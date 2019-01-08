package com.e451.faas.demo.faasDemo.repositories;

import com.e451.faas.demo.faasDemo.repositories.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
  Optional<User> findByResetToken(String email);
}
