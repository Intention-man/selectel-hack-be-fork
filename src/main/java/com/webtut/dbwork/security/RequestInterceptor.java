package com.webtut.dbwork.security;

import com.webtut.dbwork.services.impl.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class RequestInterceptor implements HandlerInterceptor {

    private final AuthService authService;
    private static final String AUTHORIZATION = "Authorization";
    private static final String EMPTY_TOKEN = "";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            System.out.println("1 - preHandle() : Before sending request to the Controller");
            System.out.println("Method Type: " + request.getMethod());
            System.out.println("Request URL: " + request.getServletPath());
            if (shouldNotFilter(request))
                return true;

            final String token = getTokenFromRequest(request);
            if (token.equals(EMPTY_TOKEN) || authService.userIdFromToken(token) == -1L) {
                response.setStatus(403);
                return false;
            }

            request.setAttribute("userId", authService.userIdFromToken(token));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            return false;
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        //* Business logic just before the response reaches the client and the request is served
        try {
            System.out.println("2 - postHandle() : After the Controller serves the request (before returning back response to the client)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //* Business logic after request and response is Completed
        try {
            System.out.println("3 - afterCompletion() : After the request and Response is completed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.matches("/login") || path.matches("/registration");
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer "))
            return bearer.substring(7);

        return EMPTY_TOKEN;
    }
}