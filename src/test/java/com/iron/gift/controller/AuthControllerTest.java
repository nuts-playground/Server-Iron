package com.iron.gift.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iron.gift.domain.User;
import com.iron.gift.repository.SessionRepository;
import com.iron.gift.repository.user.UserRepository;
import com.iron.gift.request.user.Login;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 성공")
    void test() throws Exception {

        User user = User.builder()
                .name("테스트")
                .email("test@gmail")
                .password("1234")
                .build();

        userRepository.save(user);

        Login loginBuilde = Login.builder()
                .email("test@gmail")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(loginBuilde);

        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", Matchers.notNullValue()))
                .andDo(print());

    }

    @Test
    @DisplayName("로그인 후 세션 생성")
    @Transactional
    void test2() throws Exception {

        User user = User.builder()
                .name("테스트")
                .email("test@gmail")
                .password("1234")
                .build();

        userRepository.save(user);

        Login loginBuilde = Login.builder()
                .email("test@gmail")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(loginBuilde);

        mockMvc.perform(post("/auth/login")
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        User loggedUser = userRepository.findById(user.getId())
                .orElseThrow(RuntimeException::new);

        Assertions.assertEquals(1L, loggedUser.getSessions().size());

    }
}