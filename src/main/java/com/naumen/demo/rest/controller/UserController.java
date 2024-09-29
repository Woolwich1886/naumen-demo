package com.naumen.demo.rest.controller;

import com.naumen.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "users")
public class UserController {

    private final UserService userService;

    //TODO: не совсем понял по постановке, каким образом проверять пользователя,
    // который будет запускать обновление, поэтому добавил логин в query параметры, по которому можно валидировать

    /**
     * Обновляет пользователей на основе информации из внешнего сервиса
     * @param departmentId id департамента
     * @param login логин пользователя
     * @return nothing
     */
    @PostMapping("/update")
    public ResponseEntity<Void> UpdateDepartmentUsers(
            @RequestParam(name = "department") String departmentId,
            @RequestParam(name = "user") String login
    ) {
        userService.updateDepartmentUsers(login, departmentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
