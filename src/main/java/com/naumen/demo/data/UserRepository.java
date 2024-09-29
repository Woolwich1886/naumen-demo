package com.naumen.demo.data;

import com.naumen.demo.entity.Department;
import com.naumen.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByLogin(String login);

    List<User> findByDepartment(Department department);

}
