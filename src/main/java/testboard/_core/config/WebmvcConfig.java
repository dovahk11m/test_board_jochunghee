package testboard._core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import testboard._core.interceptor.LoginInterceptor;

@Configuration
@RequiredArgsConstructor
public class WebmvcConfig {

    private final LoginInterceptor loginInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/",
                        "/posts",
                        "/posts/{id:[0-9]+}",
                        "/login-form",
                        "/login",
                        "/join-form",
                        "/join",
                        "/h2-console/**",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/favicon.ico",
                        "/error"
                );
    }
}
