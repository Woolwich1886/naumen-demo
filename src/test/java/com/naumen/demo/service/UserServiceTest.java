package com.naumen.demo.service;

import com.naumen.demo.data.DepartmentRepository;
import com.naumen.demo.data.UserRepository;
import com.naumen.demo.entity.Department;
import com.naumen.demo.entity.User;
import com.naumen.demo.entity.UserRole;
import com.naumen.demo.rest.dto.ExternalUserInfoDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ExternalService externalService;

    @Test
    void updateDepartmentUsers() {
        String login = "loginAdmin";
        Long departmentId = 1L;

        Department department1 = new Department();
        department1.setId(1L);
        department1.setName("Dunder Mifflin");
        department1.setIsActive(true);

        UserRole userRole1 = new UserRole();
        userRole1.setId(1L);
        userRole1.setName("admin");

        UserRole userRole2 = new UserRole();
        userRole2.setId(2L);
        userRole2.setName("user");

        User user1 = new User();
        user1.setId(1L);
        user1.setUserRole(userRole1);
        user1.setDepartment(department1);
        user1.setEmail("123");
        user1.setPassword("123");
        user1.setLogin(login);
        user1.setFullName("Michael Scott");
        user1.setBirthDay(LocalDateTime.now());
        user1.setIsActive(true);
        user1.setUpdateDate(LocalDateTime.of(2000, 1, 1, 0, 0));

        User user2 = new User();
        user2.setId(2L);
        user2.setUserRole(userRole2);
        user2.setDepartment(department1);
        user2.setEmail("2");
        user2.setPassword("2");
        user2.setLogin("login");
        user2.setFullName("Dwight Schrute");
        user2.setBirthDay(LocalDateTime.now());
        user2.setIsActive(true);
        user2.setUpdateDate(LocalDateTime.of(2000, 1, 1, 0, 0));

        ExternalUserInfoDTO externalUserInfo1 = new ExternalUserInfoDTO();
        externalUserInfo1.setEmail("Best boss email");
        externalUserInfo1.setFullName("Best boss");
        externalUserInfo1.setPhoneNumber("8-800-555-35-35");

        ExternalUserInfoDTO externalUserInfo2 = new ExternalUserInfoDTO();
        externalUserInfo1.setEmail("Region manager assistant email");
        externalUserInfo1.setFullName("Region manager assistant");
        externalUserInfo1.setPhoneNumber("8 800 555 35 35");

        Mockito.when(userRepository.findFirstByLogin(login)).thenReturn(Optional.ofNullable(user1));
        Mockito.when(departmentRepository.getReferenceById(departmentId)).thenReturn(department1);
        Mockito.when(userRepository.findByDepartment(department1)).thenReturn(List.of(user1, user2));
        Mockito.when(externalService.getUpdatedInfo(user1.getLogin())).thenReturn(externalUserInfo1);
        Mockito.when(externalService.getUpdatedInfo(user2.getLogin())).thenReturn(externalUserInfo2);
        Mockito.when(userRepository.save(user1)).thenReturn(user1);
        Mockito.when(userRepository.save(user2)).thenReturn(user2);

        userService.updateDepartmentUsers(login, departmentId.toString());

        assertEquals(user1.getEmail(), externalUserInfo1.getEmail());
        assertEquals(user1.getFullName(), externalUserInfo1.getFullName());
        assertEquals(user2.getEmail(), externalUserInfo2.getEmail());
        assertEquals(user2.getFullName(), externalUserInfo2.getFullName());
        assertEquals(0, DAYS.between(user1.getUpdateDate(), LocalDateTime.now()));
        assertEquals(0, DAYS.between(user2.getUpdateDate(), LocalDateTime.now()));
    }

}