package ch.supsi.backend_api_rest.controller.interceptors;

import ch.supsi.backend_api_rest.security.jwt.TokenService;
import ch.supsi.backend_api_rest.service.IEmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
@Component
public class CheckLoginInterceptor implements HandlerInterceptor {
    private final TokenService tokenService;
    private final IEmployeeService employeeService;

    @Autowired
    public CheckLoginInterceptor(TokenService tokenService, IEmployeeService employeeService) {
        this.tokenService = tokenService;
        this.employeeService = employeeService;
    }

    private boolean setEmployee(String token) {
        if (token == null || token.isEmpty())
            return false;
        token = token.replace("Bearer ", "");
        if (tokenService.isLogged(tokenService.getUsernameFromToken(token))) {
            employeeService.setCurrentEmployee(employeeService.findEmployeeByUsername(tokenService.getUsernameFromToken(token)));
            return true;
        } else {
            return false;
        }


    }

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        return setEmployee(request.getHeader("Authorization"));
    }
}
