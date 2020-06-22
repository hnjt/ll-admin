package com.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Security fig
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)//开启方法控制注解 @Secured、@PreAuthorize、@PostAuthorize
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value( "${security-fig.login-url}" )
    private String loginUrl;
    @Value( "${security-fig.login-processing-url}" )
    private String loginProcessingUrl;
    @Value( "${security-fig.error-url}" )
    private String errorUrl;
    @Value( "${security-fig.logout-url}" )
    private String logoutUrl;

    @Autowired
    private CustomLogoutService customLogoutService;

    @Bean
    UserDetailsService customUserService(){
        return new CustomLoginService();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .portMapper(  )
                .http( 80 )
                .mapsTo( 443 )
                .and()
                .authorizeRequests()
                .antMatchers( "/sys/**" ).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage( loginUrl )//登录页面
                .loginProcessingUrl(loginProcessingUrl)//登录action 提交地址
//                .defaultSuccessUrl( "/index.html" )//这里指定的是静态页面，必须加后缀，如果不指定，就走路径为“/”的方法
//                .failureUrl( "/login?error" )
                .failureUrl(errorUrl)//登录失败处理方法
                .permitAll()
                .and()
                .logout()
                .logoutUrl(logoutUrl)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler( customLogoutService )
                .permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //设置静态资源不要拦截
        web.ignoring().antMatchers("/js/**","/css/**", "/static/image/**","/static/**","/templates/**");
    }

    @Autowired
    private SecurityAuthenticationProvider provider;//注入我们自己的AuthenticationProvider

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //走默认认证
//        auth.userDetailsService( customUserService() ).passwordEncoder( new PasswordConfig() );
        //走自定义认证
        auth.authenticationProvider(provider);
    }


}
