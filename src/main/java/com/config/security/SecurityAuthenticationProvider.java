package com.config.security;


import java.util.Collection;
import java.util.Date;

import com.ll.admin.dao.LoginRepository;
import com.ll.admin.domain.Login;
import com.utils.EncryptionUtil;
import com.utils.MD5;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;


/**
 * 登录逻辑
 */
@Component
@Slf4j
public class SecurityAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailService;
    @Autowired
    private LoginRepository loginRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        WebAuthenticationDetails details =(WebAuthenticationDetails) authentication.getDetails();
        String remoteAddress = details.getRemoteAddress();
        remoteAddress = "0:0:0:0:0:0:0:1".equals( remoteAddress ) ? "127.0.0.1" : remoteAddress; //访问IP
        String userName = authentication.getName();	// 这个获取表单输入中返回的用户名;
        String password = authentication.getCredentials().toString();	// 这个是表单中输入的密码；
        //密码处理
        password = EncryptionUtil.encrypt( password );
        password = MD5.getMD5( password );

        Login loginData = (Login) userDetailService.loadUserByUsername(userName);

        Date loginTime = loginData.getLoginTime();
        Date logoutTime = loginData.getLogoutTime();
        Date newDate = new Date();
        final long effectiveTime = 1000 * 60 * 60 * 1; //1小时
        final long lockTime = 1000 * 60 * 3; //3分钟
        final long lockRevokeTime = 1000 * 60 * 20; //20分钟

        if (!password.equals( loginData.getPassword() )){
            int cnt = null != loginData.getCnt() ? loginData.getCnt() : 0;
            if (null == loginTime || newDate.getTime() - loginTime.getTime() > lockTime)
                cnt = 1;
            else{
                if (cnt < 5)
                    cnt = cnt + 1;
            }

            loginData.setLoginTime( newDate );
            loginData.setCnt( cnt );

            if (null != loginData.getCnt() && loginData.getCnt() > 4){
                loginData.setStarts( "400" );
                if (null != loginRepository.save( loginData ))
                    log.info( "帐户：{}已被锁定！",userName);
                throw new BadCredentialsException( "帐户被锁定！" );//密码错误，锁定
            }else if ("400".equals( loginData.getStarts() )){
                throw new BadCredentialsException( "帐户被锁定！" );
            }

            if (null != loginRepository.save( loginData ))
                log.info( "密码错误 {} 次",cnt );
            throw new BadCredentialsException( "密码错误" + cnt + "次，5次后锁定账号！");
        }

        if (!remoteAddress.equals( loginData.getLoginIp())){
            if (null != loginTime && null != logoutTime){
                if (loginTime.getTime() > logoutTime.getTime() && newDate.getTime() - loginTime.getTime() < effectiveTime){
                    log.info( "{} 请求试图登录系统，被系统拒绝！",remoteAddress );
                    throw new BadCredentialsException( "用户正在登录！" );//当前用户正在登录
                }
            }
        }

        if ("400".equals( loginData.getStarts() )){
            if (null != loginTime && newDate.getTime() - loginTime.getTime() > lockRevokeTime ){
                loginData.setStarts( "200" );
                loginData.setCnt( 0 );
            }else
                throw new BadCredentialsException( "帐户被锁定！" );
        }

        if ("500".equals( loginData.getStarts() ))
            throw new BadCredentialsException( "帐户被禁用！" );

        if (!"200".equals( loginData.getStarts() ))
            throw new BadCredentialsException( "帐户状态未知异常！" );


        Collection<? extends GrantedAuthority> authorities = loginData.getAuthorities();
        // 构建返回的用户登录成功的token

        log.info(
                "**************************** 帐户：{}     在 #-{}-#   使用   *** {} ***   登录系统 ****************************",
                userName,newDate,remoteAddress
        );

        loginData.setLoginIp( remoteAddress );
        loginData.setLoginTime( newDate );
        loginRepository.save(loginData);

        return new UsernamePasswordAuthenticationToken(loginData, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // TODO Auto-generated method stub
        // 这里直接改成retrun true;表示是支持这个执行
        return true;
    }
}
