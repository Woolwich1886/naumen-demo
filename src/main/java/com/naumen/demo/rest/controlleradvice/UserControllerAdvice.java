package com.naumen.demo.rest.controlleradvice;

import com.naumen.demo.rest.dto.ExceptionDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDTO> readableRuntimeException(HttpServletRequest req, RuntimeException exception) {
        var dto = ExceptionDTO.builder()
                .error(exception.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .path(String.format("%s?%s", req.getRequestURI(), req.getQueryString()))
                .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .build();
        exception.printStackTrace();
        return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
