package com.mesto.movieplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mesto.movieplatform.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleById(Integer id);

    Role findByName(String name);

    Boolean existsByName(String name);
}
