package com.config.security;

import com.ll.admin.dao.UserRepository;
import com.ll.admin.domain.Login;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

        String userName = authentication.getName();
        Date newDate = new Date();
        WebAuthenticationDetails details =(WebAuthenticationDetails) authentication.getDetails();
        String remoteAddress = details.getRemoteAddress();

        Login login = userRepository.findByUsername( authentication.getName() );

        login.setLogoutTime( new Date(  ) );//退出时间

        log.info(
                "**************************** 帐户：{}     在 #-{}-#   使用   *** {} ***   退出系统 ****************************",
                userName,newDate,remoteAddress
        );
        response.sendRedirect( contextPath + loginUrl );
    }
}