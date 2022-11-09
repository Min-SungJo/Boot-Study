package com.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthenticatedReactiveAuthorizationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //@Bean을 다룰 수 있게 해준다.
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * application.properties에 작성한 datasource(db정보)사용할 수 있게 의존성 주입
     * **/
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                    .antMatchers("/","/starter-template.css","/css/**").permitAll()//css in css folder->jpa
                    .anyRequest().authenticated()
                   .and()
                .formLogin()
                    .loginPage("/account/login")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();
    }

    /**
     * AuthenticationManagerBuilder 스프링이 알아서 인증처리를 해준다
     * Authentication   로그인 인증
     * Authorization    사용자 권한
     * **/
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(
                    "select username, password, enabled "+
                    "from user "+
                    "where username = ?"
                )
                .authoritiesByUsernameQuery(
                    "select u.username, ur.role "+
                    "from user_role ur join user u on ur.role=u.role "+
                    "where username = ?"
                );
    }
    /*password 안전하게 암호화 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
