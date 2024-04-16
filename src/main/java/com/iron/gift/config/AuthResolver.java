package com.iron.gift.config;

import com.iron.gift.config.data.UserSession;
import com.iron.gift.exception.Unauthorized;
import com.iron.gift.repository.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;
    private final AppConfig appConfig;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String jws = webRequest.getHeader("Authorization");
        if (jws == null || jws.isEmpty() || jws.isBlank()) {
            throw new Unauthorized();
        }

        byte[] decodedKey = Base64.getDecoder().decode(appConfig.getJwtKey());
        SecretKeySpec originKey = new SecretKeySpec(decodedKey, "HMACSHA256");

        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(originKey)
                    .build()
                    .parseSignedClaims(jws);

            String userId = claims.getPayload().getSubject();

            return new UserSession(Long.parseLong(userId));

        } catch (JwtException e) {
            log.error("jwt error:{}", e);
            throw new Unauthorized();
        }
    }
}
