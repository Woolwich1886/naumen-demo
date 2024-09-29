package com.naumen.demo.service;

import com.naumen.demo.rest.dto.ExternalUserInfoDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@ExtendWith(MockitoExtension.class)
class ExternalServiceTest {

    @InjectMocks
    private ExternalService externalService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void getUpdatedInfoStatusCode() {
        String login = "SomeLogin";
        String url = "http://external.service/api/v1/user/%s".formatted(login);

        Mockito.when(restTemplate.exchange(url, HttpMethod.GET, null, ExternalUserInfoDTO.class))
                .thenReturn(ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).build());

        assertThrowsExactly(
                RuntimeException.class,
                () -> externalService.getUpdatedInfo(login),
                "Косяк"
        );
    }

    @Test
    void getUpdatedInfoRest() {
        String login = "SomeLogin";
        String url = "http://external.service/api/v1/user/%s".formatted(login);

        Mockito.when(restTemplate.exchange(url, HttpMethod.GET, null, ExternalUserInfoDTO.class))
                .thenThrow(new RestClientException("Подозрительно кривой адрес"));

        assertThrowsExactly(
                RuntimeException.class,
                () -> externalService.getUpdatedInfo(login),
                "Косяк"
        );
    }
}