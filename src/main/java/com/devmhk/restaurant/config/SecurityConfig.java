package com.devmhk.restaurant.config;

import com.devmhk.restaurant.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomerService customerService;

    @Bean
    UserAuthenticationFalureHandler getFailureHandler() {
        return new UserAuthenticationFalureHandler();
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();

        http.authorizeRequests()
                .antMatchers("/",
                        "/customer/sign-up",
                        "/customer/email-auth")
                .permitAll();

        http.authorizeRequests()
                .antMatchers("/admin/**")
                .hasAuthority("ADMIN");

        http.formLogin()
                .loginPage("/customer/sign-in")
                .failureHandler(getFailureHandler())
                .permitAll();

        super.configure(http);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerService).passwordEncoder(getPasswordEncoder());
        super.configure(auth);
    }
}
