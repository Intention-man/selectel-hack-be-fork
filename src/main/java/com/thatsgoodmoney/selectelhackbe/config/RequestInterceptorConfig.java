package com.thatsgoodmoney.selectelhackbe.config;


import com.thatsgoodmoney.selectelhackbe.security.RequestInterceptor;
import com.thatsgoodmoney.selectelhackbe.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class RequestInterceptorConfig implements WebMvcConfigurer {
    private final AuthService authService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestInterceptor(authService));
    }
}