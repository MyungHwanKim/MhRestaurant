package com.devmhk.restaurant.config;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    static String msg = "아이디 혹은 비밀번호를 틀렸습니다.";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException
    {
        if (exception instanceof InternalAuthenticationServiceException) {
            msg = exception.getMessage();
        }

        setUseForward(true);
        setDefaultFailureUrl("/customer/sign-in?error=true");
        request.setAttribute("errorMessage", msg);

        super.onAuthenticationFailure(request, response, exception);
    }
}
