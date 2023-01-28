package com.iqabiloglu.spring_boot_security.dao.repository;

import com.iqabiloglu.spring_boot_security.dao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
