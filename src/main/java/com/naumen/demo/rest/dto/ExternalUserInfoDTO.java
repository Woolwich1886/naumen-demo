package com.naumen.demo.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExternalUserInfoDTO {
    /**
     * Логин пользователя
     */
    @JsonProperty("login")
    private String login;

    /**
     * ФИО пользователя
     */
    @JsonProperty("fullName")
    private String fullName;

//    TODO: в сущности пользователя нет этого поля?
    /**
     * Номер телефона
     */
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    /**
     * Email пользователя
     */
    @JsonProperty("email")
    private String email;

    /**
     * Дата рождения
     */
    @JsonProperty("birthDay")
    private LocalDateTime birthDay;
}
