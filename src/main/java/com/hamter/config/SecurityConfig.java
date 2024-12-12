package com.hamter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;


import com.hamter.filter.JwtAuthenticationFilter;
import com.hamter.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/auth/register", "/api/auth/login", "/api/auth/changePassword", "/api/resetpass/password-reset").permitAll()
            .antMatchers("/reset-password", "/p-reset-password").permitAll()
            .antMatchers("/api/bookings/**").hasAnyAuthority("CUST", "MANAGE", "STAFF")
            .antMatchers("/api/specialties/**").hasAnyAuthority("CUST", "MANAGE", "STAFF")
            .antMatchers("/api/doctors/**").hasAnyAuthority("CUST", "MANAGE", "STAFF")
            .antMatchers("/api/histories/**").hasAnyAuthority("CUST", "MANAGE", "STAFF")
            .antMatchers("/api/schedules/**").hasAnyAuthority("CUST", "MANAGE", "STAFF")
            .antMatchers("/api/time-slots/**").hasAnyAuthority("CUST", "MANAGE", "STAFF")
            .antMatchers("/api/users/**").hasAnyAuthority("CUST", "MANAGE", "STAFF")
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.cors().configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowCredentials(true);
            config.addAllowedOriginPattern("http://localhost:4200");
            config.addAllowedHeader("Authorization"); 
            config.addAllowedHeader("*");
            config.addAllowedMethod("*");
            return config;
        });

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

