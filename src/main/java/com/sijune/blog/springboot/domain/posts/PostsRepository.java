package com.sijune.blog.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//DB Layer 접근자
public interface PostsRepository extends JpaRepository<Posts, Long> { //기본 CRUD 자동 생성

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc(); //해당 쿼리는 JPA에서 지원하지 않는다.

}
