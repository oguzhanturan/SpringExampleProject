package com.dentapp.spring.repository.Auth;

import java.util.Optional;

import com.dentapp.spring.models.Auth.RefreshToken;
import com.dentapp.spring.models.Auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByToken(String token);

  @Modifying
  int deleteByUser(User user);
}
