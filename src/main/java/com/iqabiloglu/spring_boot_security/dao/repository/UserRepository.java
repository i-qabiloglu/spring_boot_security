package com.iqabiloglu.spring_boot_security.dao.repository;

import com.iqabiloglu.spring_boot_security.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
