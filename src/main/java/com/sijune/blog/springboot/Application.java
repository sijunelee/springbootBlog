package com.sijune.blog.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing
@SpringBootApplication //스프링부트의 자동설정, 스프링 빈읽기, 생성을 모두 자동으로 설
public class Application { //메인 클래스
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        //run은 내장 was를 실행시킨다. jar파일로 실행하면 된다.
    }
}
