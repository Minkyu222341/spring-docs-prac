package com.prac.restdocs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class PostRequestDto {
    private String name;
    private String content;
}
