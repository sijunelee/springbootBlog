package com.sijune.blog.springboot.web;

import com.sijune.blog.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)  // SpringRunner: 실행
@WebMvcTest(controllers = HelloController.class, excludeFilters = {@ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, classes= SecurityConfig.class)}) //웹에 집중할 수 있는 어노테이
public class HelloControllerTest {
    @Autowired //빈 주
    private MockMvc mvc; //웹 API 테스트 시 사

    @WithMockUser(roles="USER")
    @Test
    public void hello_return() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) //http get 요청
                .andExpect(status().isOk()) //응답 상태 확
                .andExpect(content().string(hello)); //응답 결과 확인
    }

    @WithMockUser(roles="USER")
    @Test
    public void helloDto_return() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                .param("name", name)
                .param("amount", String.valueOf(amount))) //json은 문자열만 가능
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) //$.을 통해 필드명 가져올 수 있다.
                .andExpect(jsonPath("$.amount", is(amount)));

    }
}
