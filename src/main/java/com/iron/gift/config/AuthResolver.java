package com.iron.gift.config;

import com.iron.gift.config.data.UserSession;
import com.iron.gift.domain.Session;
import com.iron.gift.exception.Unauthorized;
import com.iron.gift.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String accessToken = webRequest.getHeader("authorization");

        if (accessToken == null || accessToken.equals("")) {
            throw new Unauthorized();
        }

        Session session = sessionRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new Unauthorized());

        UserSession userSession = new UserSession(session.getUser().getId());
        return userSession;
    }
}
