package com.naumen.demo.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ExceptionDTO {

    /**
     * Статус ошибки
     */
    @JsonProperty("status")
    private String status;

    /**
     * Текст ошибки
     */
    @JsonProperty("error")
    private String error;

    /**
     * Путь запроса
     */
    @JsonProperty("path")
    private String path;

    /**
     * Время
     */
    @JsonProperty("timestamp")
    private String timestamp;

}
