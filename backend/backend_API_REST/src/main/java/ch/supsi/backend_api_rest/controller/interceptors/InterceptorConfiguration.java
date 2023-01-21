package ch.supsi.backend_api_rest.controller.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    private final CheckLoginInterceptor checkLoginInterceptor;
    @Autowired
    public InterceptorConfiguration(CheckLoginInterceptor checkLoginInterceptor) {
        this.checkLoginInterceptor = checkLoginInterceptor;
    }
    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(checkLoginInterceptor).addPathPatterns("/api/**").excludePathPatterns("/api/auth/**");
    }

}
