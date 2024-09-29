package com.naumen.demo.service;

import com.naumen.demo.rest.dto.ExternalUserInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExternalService {

    private final RestTemplate restTemplate;
    @Value("${app.external.reconnectattempts}")
    private int reconnectAttempts;
    @Value("${app.external.serviceapi}")
    private String externalApi;

    public ExternalUserInfoDTO getUpdatedInfo(String login) {
        var url = "%s/user/%s".formatted(externalApi, login);
        log.info(url);
        ExternalUserInfoDTO responseBody = null;
        var attemptCount = 1;
        while (attemptCount <= reconnectAttempts) {
            log.info(
                    "Попытка № {} получения данных для пользователя с логином {}. Максимальное количество попыток {}.",
                    attemptCount,
                    login,
                    reconnectAttempts
            );
            try {
                ResponseEntity<ExternalUserInfoDTO> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        ExternalUserInfoDTO.class
                );
                if (response.getStatusCode().is2xxSuccessful()) {
                    log.info("Успешно получена информация о пользователе с логином {}", login);
                    responseBody = response.getBody();
                    break;
                } else {
                    throw new RuntimeException("статус ответа: %s".formatted(response.getStatusCode().toString()));
                }
            } catch (Exception e) {
                log.error("Ошибка подключения: {}", e.getMessage());
                if (attemptCount == reconnectAttempts) {
                    throw new RuntimeException(
                            "Ошибка подключения к удаленному серверу. Причина: %s".formatted(e.getMessage())
                    );
                } else {
                    try {
                        attemptCount++;
                        Thread.sleep(1000);
                    } catch (InterruptedException interruptedException) {
                        throw new RuntimeException(
                                "Выполнение запроса прервано: %s".formatted(interruptedException.getMessage())
                        );
                    }
                }
            }
        }

        return responseBody;
    }
}
