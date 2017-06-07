package com.zavalin.account.config;

import com.zavalin.account.userdetails.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebMVCSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    private AppUserDetailsService appUserDetails;

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws  Exception {
//        auth.inMemoryAuthentication().withUser("user").password("123").roles("USER");
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appUserDetails).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/registration").permitAll()
                .antMatchers("/webjars/bootstrap/3.3.7/css/bootstrap.min.css").permitAll()
//                .anyRequest().permitAll();
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login").successHandler(successHandler())
                    .permitAll()
                .and()
                .logout()
                    .logoutSuccessUrl("/login")
                    .permitAll()
                .and()
                    .csrf().disable();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (HttpServletRequest request,
                HttpServletResponse response, Authentication authentication) -> {
            response.sendRedirect("home");
        };
    }
}
