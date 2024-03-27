package com.iron.gift.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Login {

    @NotBlank(message = "이메일을 입력해 주세요.")
    private String email;

    @NotBlank(message = "비밀번호을 입력해 주세요.")
    private String password;

    public Login() {
    }

    @Builder
    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
