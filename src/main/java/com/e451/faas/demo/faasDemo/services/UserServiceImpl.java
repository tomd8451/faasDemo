package com.e451.faas.demo.faasDemo.services;

import com.e451.faas.demo.faasDemo.repositories.UserRepository;
import com.e451.faas.demo.faasDemo.repositories.models.User;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService)")
public class UserServiceImpl implements UserService{

  @Autowired
  private UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Optional findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public Optional findUserByResetToken(String resetToken) {
    return userRepository.findByResetToken(resetToken);
  }

  @Override
  public void save(User user) {
    userRepository.save(user);
  }
}
