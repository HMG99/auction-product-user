package com.auction.security;

import com.auction.entity.UserEntity;
import com.auction.enums.Role;
import com.auction.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Base64;

@Component
public class ThreadContextInterceptor implements HandlerInterceptor {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler instanceof HandlerMethod handlerMethod) {

            RequiredAdminUser methodAnnotation = handlerMethod.getMethodAnnotation(RequiredAdminUser.class);

            if(methodAnnotation != null) {
                String bearerToken = request.getHeader("Authorization");
                if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
                    String token = bearerToken.substring("Bearer ".length());
                    String emailFromToken = getEmailFromToken(token);

                    UserEntity userEntity = userRepository.getByEmail(emailFromToken);
                    if(!userEntity.getRole().equals(Role.ADMIN)) {
                        throw new SecurityForbiddenException("InSufficient permission");
                    }
                }
            }
        }
        return true;
    }

    private String getEmailFromToken(String token) {
        String[] split = token.split("\\.");
        String payload = new String(Base64.getUrlDecoder().decode(split[1]));
        String s = payload.split(",")[0].split(":")[1];
        return s.substring(1, s.length() - 1);
    }

}


