package com.techzen.techlearn.repository;

import com.techzen.techlearn.entity.Role;
import com.techzen.techlearn.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(RoleType name);
}
