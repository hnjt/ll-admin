package com.config.security;

import com.ll.admin.dao.LoginRepository;
import com.ll.admin.domain.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 登录
 */
public class CustomLoginService implements UserDetailsService {

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Login login = this.loginRepository.findByUsername( username );
        if (login == null)
            throw new BadCredentialsException("用户不存在！");

        return login;
    }

}
