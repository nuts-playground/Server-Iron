package com.iron.gift.service;

import com.iron.gift.crypto.PasswordEncoder;
import com.iron.gift.domain.User;
import com.iron.gift.exception.AlreadyExistsEmailException;
import com.iron.gift.exception.InvalidSigninInformation;
import com.iron.gift.repository.user.UserRepository;
import com.iron.gift.request.user.Login;
import com.iron.gift.request.user.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public Long signin(Login request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidSigninInformation::new);

        PasswordEncoder encoder = new PasswordEncoder();
        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidSigninInformation();
        }

        return user.getId();
    }

    public void signup(Signup signup) {

        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());

        if (userOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        PasswordEncoder passwordEncoder = new PasswordEncoder();
        String encryptedPassword = passwordEncoder.encrypt(signup.getPassword());

        User newUser = User.builder()
                .email(signup.getEmail())
                .password(encryptedPassword)
                .name(signup.getName())
                .build();

        userRepository.save(newUser);
    }
}
