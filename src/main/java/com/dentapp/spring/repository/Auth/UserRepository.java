package com.dentapp.spring.repository.Auth;

import java.util.List;
import java.util.Optional;

import com.dentapp.spring.models.Auth.Role;
import com.dentapp.spring.models.Auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  List<User> findByRoles_(Role role);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
