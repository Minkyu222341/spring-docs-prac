package com.prac.restdocs.controller;

import com.prac.restdocs.domain.User;
import com.prac.restdocs.domain.UserRequestDto;
import com.prac.restdocs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @PostMapping
  public User createUser(@RequestBody UserRequestDto requestDto) {
    return userService.createUser(requestDto);
  }
}