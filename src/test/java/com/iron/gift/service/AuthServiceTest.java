package com.iron.gift.service;

import com.iron.gift.config.AppConfig;
import com.iron.gift.crypto.PasswordEncoder;
import com.iron.gift.domain.User;
import com.iron.gift.exception.AlreadyExistsEmailException;
import com.iron.gift.repository.user.UserRepository;
import com.iron.gift.request.user.Login;
import com.iron.gift.request.user.Signup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 성공")
    @Transactional
    public void signinSuccess() {

        PasswordEncoder encoder = new PasswordEncoder();
        String encryptedPassword = encoder.encrypt("1234");

        User user = User.builder()
                .email("test@gmail.com")
                .password(encryptedPassword)
                .name("테스트")
                .build();

        userRepository.save(user);

        Login login = Login.builder()
                .email("test@gmail.com")
                .password("1234")
                .build();

        Long userId = authService.signin(login);
        assertNotNull(userId);
    }

    @Test
    @DisplayName("회원가입 성공")
    @Transactional
    public void signup() {
        Signup signup = Signup.builder()
                .email("test@gmail.com")
                .password("1234")
                .name("테스트")
                .build();

        authService.signup(signup);

        assertEquals(1, userRepository.count());

        User user = userRepository.findAll().iterator().next();
        assertEquals("test@gmail.com", user.getEmail());
        assertNotNull(user.getPassword());
        assertNotEquals("1234", user.getPassword());
        assertEquals("테스트", user.getName());
    }

    @Test
    @DisplayName("회원가입 시 중복 이메일 체크")
    public void signupDuplicateEmail() {
        User user = User.builder()
                .email("test@gmail.com")
                .password("1234")
                .name("테스트")
                .build();

        userRepository.save(user);

        Signup signup = Signup.builder()
                .email("test@gmail.com")
                .password("1234")
                .name("테스트")
                .build();

        assertThrows(AlreadyExistsEmailException.class, () -> authService.signup(signup));
    }
}