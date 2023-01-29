package ch.supsi.backend_api_rest.controller.interceptors;

import ch.supsi.backend_api_rest.controller.AuthenticationController;
import ch.supsi.backend_api_rest.security.jwt.TokenService;
import ch.supsi.backend_api_rest.service.IEmployeeService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class CheckLoginInterceptor implements HandlerInterceptor {
    private final TokenService tokenService;
    private final IEmployeeService employeeService;
enum Status{
    AUTHORIZED, UNAUTHORIZED, REFRESH
}
    @Autowired
    public CheckLoginInterceptor(TokenService tokenService, IEmployeeService employeeService) {
        this.tokenService = tokenService;
        this.employeeService = employeeService;
    }

    private Status setEmployee(String token) {
        if (token == null || token.isEmpty())
            return Status.UNAUTHORIZED;
        token = token.replace("Bearer ", "");
        //check if token is valid
        String username = tokenService.getUsernameFromToken(token);

        if (tokenService.isLogged(username)) {
            tokenService.updateSession(username);
            if (tokenService.validateToken(token)) {
                employeeService.setCurrentEmployee(employeeService.findEmployeeByUsername(username));
                return Status.AUTHORIZED;
            }
            if (tokenService.isMenager(token)) {

                return Status.REFRESH;
            }
        }


                tokenService.revokeUser(username);
                return Status.UNAUTHORIZED;

    }

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        //get token from cookie

        var cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization")) {
                    request.setAttribute("Authorization", cookie.getValue());
                }
            }
        }
if(request.getHeader("Authorization")!=null)
{
    var result = setEmployee(request.getHeader("Authorization"));


        if (result == Status.UNAUTHORIZED) {
            response.setStatus(401);
            return false;
        }
        else if (result == Status.REFRESH) {
            response.setStatus(403);
            return false;
        }
        else {
            return true;
        }
    }else{
        response.setStatus(401);
        return false;
    }
    }


}
