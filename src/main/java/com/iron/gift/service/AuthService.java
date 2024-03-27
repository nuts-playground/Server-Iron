package com.iron.gift.service;

import com.iron.gift.domain.Session;
import com.iron.gift.domain.User;
import com.iron.gift.exception.InvalidSigninInformation;
import com.iron.gift.repository.user.UserRepository;
import com.iron.gift.request.user.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public String signin(Login request) {
        User user = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new InvalidSigninInformation());

        Session session = user.addSession();

        return session.getAccessToken();

    }

}
