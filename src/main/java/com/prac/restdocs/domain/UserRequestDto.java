package com.prac.restdocs.domain;

import lombok.Getter;
import lombok.Setter;

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
@Setter
public class UserRequestDto {
  private String name;

  private String email;
}
