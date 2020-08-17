package com.sijune.blog.springboot.domain.posts;

import com.sijune.blog.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity //테이블과 링크될 클래스, 절대 Setter메소드를 만들지 않는다.
public class Posts extends BaseTimeEntity {

    @Id //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동으로 1씩 증가
    private Long id;

    @Column(length = 500, nullable = false) //기본적으로 부여되나, 값을 변경하고 싶을 때 사용
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }



}
