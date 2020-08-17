package com.sijune.blog.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter //get 메소드 생성
@RequiredArgsConstructor
public class HelloResponseDto {

    private final String name;
    private final int amount;

}
