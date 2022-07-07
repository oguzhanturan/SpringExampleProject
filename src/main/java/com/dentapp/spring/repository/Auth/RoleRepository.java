package com.dentapp.spring.repository.Auth;

import java.util.Optional;

import com.dentapp.spring.models.Auth.ERole;
import com.dentapp.spring.models.Auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
