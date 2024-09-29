package com.naumen.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Пользователь
 */
@Data
@Entity
@Table(schema = "public", name = "user")
public class User {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Логин
     */
    @Column(name = "login", nullable = false, length = 64)
    private String login;

    /**
     * Пароль
     */
    @Column(name = "password", nullable = false, length = 256)
    private String password;

    /**
     * Полное имя
     */
    @Column(name = "full_name", nullable = false, length = 256)
    private String fullName;

    /**
     * Роль пользователя
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private UserRole userRole;

    /**
     * Электронная почта
     */
    @Column(name = "email", nullable = false, length = 128)
    private String email;

    /**
     * Департамент
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    /**
     * Дата рождения
     */
    @Column(name = "birth_day", nullable = false)
    private LocalDateTime birthDay;

    /**
     * Активен
     */
    @Column(name = "active", nullable = false)
    private Boolean isActive;

    /**
     * Последняя дата обновления
     */
    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;

}
