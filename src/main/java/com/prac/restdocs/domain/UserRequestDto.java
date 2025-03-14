package com.prac.restdocs.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.prac.restdocs.domain
 * fileName       : UserRequestDto
 * author         : MinKyu Park
 * date           : 2023-06-09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-06-09        MinKyu Park       최초 생성
 */
@Getter
@NoArgsConstructor
public class UserRequestDto {
  private String name;

  private String email;

  @Builder
  public UserRequestDto(String name, String email) {
    this.name = name;
    this.email = email;
  }
}
