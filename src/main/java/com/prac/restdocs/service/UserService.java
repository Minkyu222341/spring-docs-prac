package com.prac.restdocs.service;

import com.prac.restdocs.domain.User;
import com.prac.restdocs.domain.UserRequestDto;
import com.prac.restdocs.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User createUser(UserRequestDto requestDto) {
    User user = User.builder()
            .email(requestDto.getEmail())
            .name(requestDto.getName())
            .build();
    return userRepository.save(user);
  }
}