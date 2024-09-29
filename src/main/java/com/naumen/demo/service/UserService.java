package com.naumen.demo.service;

import com.naumen.demo.data.DepartmentRepository;
import com.naumen.demo.data.UserRepository;
import com.naumen.demo.entity.Department;
import com.naumen.demo.entity.User;
import com.naumen.demo.entity.UserRole;
import com.naumen.demo.rest.dto.ExternalUserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class UserService {

    private final static int DAYS_TO_UPDATE = 30;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final ExternalService externalService;

    public boolean checkIsUserAdmin(String login) {
        Optional<User> user = userRepository.findFirstByLogin(login);
        if (user.isEmpty()) {
            throw new RuntimeException("Не найден пользователь с логином %s".formatted(login));
        }

        return user.flatMap(u -> Optional.ofNullable(u.getUserRole()))
                .map(UserRole::getName)
                .map(String::toLowerCase)
                .map(role -> role.equals("admin"))
                .orElse(false);
    }

    public Department getDepartmentById(String id) {
        return getDepartmentById(NumberUtils.parseNumber(id, Long.class));
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.getReferenceById(id);
    }

    public Set<User> getOutdatedUsers(Department department) {
//        TODO: берем всех пользователей или только активных?
        return userRepository.findByDepartment(department)
                .stream()
                .filter(user -> DAYS.between(user.getUpdateDate(), LocalDateTime.now()) >= DAYS_TO_UPDATE)
                .collect(Collectors.toSet());
    }

    public void updateDepartmentUsers(String initiator, String departmentId) {
//        TODO: проверка на активный департамент?
        if (checkIsUserAdmin(initiator)) {
            Department department = getDepartmentById(departmentId);
            Set<User> outdatedUsers = getOutdatedUsers(department);
            outdatedUsers.forEach(this::updateUserByExternalSource);
        } else {
            throw new RuntimeException("У пользователя с логином %s недостаточно прав.".formatted(initiator));
        }
    }

    private void updateUserByExternalSource(User user) {
        ExternalUserInfoDTO externalInfo = externalService.getUpdatedInfo(user.getLogin());
        user.setFullName(externalInfo.getFullName());
        user.setEmail(externalInfo.getEmail());
//        TODO: в сущности нет поля с телефоном?
//        user.setPhoneNumber(externalInfo.getPhoneNumber())
        user.setUpdateDate(LocalDateTime.now());
        userRepository.save(user);
    }
}
