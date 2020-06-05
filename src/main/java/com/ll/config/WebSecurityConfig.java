package com.ll.config;

import com.ll.admin.service.CustomUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)//开启方法控制注解 @Secured、@PreAuthorize、@PostAuthorize
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    UserDetailsService customUserService(){
        return new CustomUserService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService( customUserService() ).passwordEncoder( new PasswordConfig() );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage( "/login" )
//                .defaultSuccessUrl( "/index.html" )//这里指定的是静态页面，必须加后缀，如果不指定，就走路径为“/”的方法
                .failureUrl( "/login?error" )
                .permitAll()
                .and()
                .logout().permitAll();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //设置静态资源不要拦截
        web.ignoring().antMatchers("/js/**","/cs/**","/images/**");
    }
}
