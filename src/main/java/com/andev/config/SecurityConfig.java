package com.andev.config;

import jakarta.servlet.FilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // active spring web security
@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /**
         * Khi người dùng truy cập endpoint yêu cầu xác thực nhưng chưa đăng nhập,
         * họ sẽ được chuyển đến trang login gặp định do Spring Security cung cấp
         */
        http.formLogin((formLogin) -> formLogin.loginProcessingUrl("/login"));

        http.authorizeHttpRequests(req -> req
                // Các endpoint này không yêu cầu xác thực, người dùng có thể truy cập mà không cần login
                .requestMatchers("/api/v1/auth/login", "/api/v1/auth/register")
                .permitAll()
                // Tất cả các yêu cầu khác (ngoài  các endpoint trên) đều yêu cầu người dùng phải đăng nhập trước khi truy cập
                .anyRequest().authenticated()
        );
        return http.build();
    }

    /**
     * Config User Information (quản lý các user)
     *
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User
                .withUsername("admin")
                .password(passwordEncoder().encode("123"))
//                .roles("admin", "user")
                .authorities("ROLE_ADMIN", "ROLE_USER")
                .build();

        UserDetails user = User
                .withUsername("user")
                .password(passwordEncoder().encode("123"))
//                .roles("user")
                .authorities("ROLE_USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
