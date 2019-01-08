package com.e451.faas.demo.faasDemo.services;

import com.e451.faas.demo.faasDemo.repositories.models.User;
import java.util.Optional;

public interface UserService {
  Optional<User> findUserByEmail(String email);
  Optional<User> findUserByResetToken(String resetToken);
  void save(User user);
}
