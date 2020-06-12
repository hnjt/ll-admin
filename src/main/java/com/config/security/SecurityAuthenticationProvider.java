package com.config.security;


import java.util.Collection;

import com.ll.admin.domain.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//认证处理。通过UserDetailsService获取到用户名和密码，这里可以获取到登陆输入的用户名和密码，两者进行匹配认证，包括加密处理，从而判断登陆成功与否
@Component
public class SecurityAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // TODO 只做token生成过程，登录校验交给‘loadUserByUsername’去做
        String userName = authentication.getName();	// 这个获取表单输入中返回的用户名;
        String password = authentication.getCredentials().toString();	// 这个是表单中输入的密码；

        Login loginData = (Login) userDetailService.loadUserByUsername(userName);

        if ( loginData == null )
            throw new BadCredentialsException( "用户不存在" );

        if (!password.equals( loginData.getPassword() ))
            throw new BadCredentialsException( "密码错误" );

        Collection<? extends GrantedAuthority> authorities = loginData.getAuthorities();
        // 构建返回的用户登录成功的token
        return new UsernamePasswordAuthenticationToken(loginData, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // TODO Auto-generated method stub
        // 这里直接改成retrun true;表示是支持这个执行
        return true;
    }
}
