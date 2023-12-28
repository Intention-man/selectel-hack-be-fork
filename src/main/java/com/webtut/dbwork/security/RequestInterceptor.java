package com.webtut.dbwork.security;

import com.webtut.dbwork.services.impl.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class RequestInterceptor implements HandlerInterceptor {

    private final AuthService authService;
    private static final String AUTHORIZATION = "authorization";
    private static final String EMPTY_TOKEN = "";

    private final Set<String> methodsToFilter = Set.of("GET", "POST", "PUT", "DELETE");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            if (shouldFilter(request)){
                final String token = getTokenFromRequest(request);
                if (token.equals(EMPTY_TOKEN) || authService.userIdFromToken(token) == -1L) {
                    response.setStatus(403);
                    return false;
                }
                request.setAttribute("userId", authService.userIdFromToken(token));
            }
            return true;
        } catch (Exception e) {
            response.setStatus(500);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        response.addHeader("Access-Control-Max-Age", "1209600");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }

    private boolean shouldFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return methodsToFilter.contains(request.getMethod().toUpperCase()) && !(path.matches("/login") || path.matches("/registration"));
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer "))
            return bearer.substring(7);

        return EMPTY_TOKEN;
    }
}