package com.naumen.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Департамент
 */
@Data
@Entity
@Table(schema = "public", name = "department")
public class Department {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Наименование
     */
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    /**
     * Активен
     */
    @Column(name = "active", nullable = false)
    private Boolean isActive;

}
