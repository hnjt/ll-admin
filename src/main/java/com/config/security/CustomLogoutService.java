package com.config.security;

import com.ll.admin.dao.UserRepository;
import com.ll.admin.domain.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 登出
 * 默认被 /signout 请求
 */
@Component
public class CustomLogoutService implements LogoutSuccessHandler {

    @Value( "${server.servlet.context-path}" )
    private String contextPath;
    @Value( "${security-fig.login-url}" )
    private String loginUrl;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication)
            throws IOException, ServletException {

        Login login = userRepository.findByUsername( authentication.getName() );
        login.setLogoutTime( new Date(  ) );//退出时间

        response.sendRedirect( contextPath + loginUrl );
    }
}