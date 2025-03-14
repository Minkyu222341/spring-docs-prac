package com.prac.restdocs.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String email;

  // 기본 생성자, Getter/Setter 생략

  @Builder
  public User(Long id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }
}