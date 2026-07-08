package com.vssolutions.careerportal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // NEW — must come BEFORE the /api/auth/** permitAll below (first-match-wins)
                .requestMatchers("/api/auth/login-history/all").hasRole("ADMIN")
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/jobs").permitAll()
                .requestMatchers("/api/jobs/search").permitAll()
                .requestMatchers("/api/jobs/filter").permitAll()   // FIXED — was missing, defaulted to authenticated
                .requestMatchers("/api/jobs/{id}").permitAll()

                // NEW — public company browsing + read-only reviews (candidates & guests)
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/companies/**").permitAll()
                // NEW — public course catalog browsing
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/courses", "/api/courses/{id}").permitAll()
                // NEW — public internship browsing
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/internships/**").permitAll()

                .requestMatchers("/api/candidates/**").hasRole("CANDIDATE")
                .requestMatchers("/api/resume/**").hasRole("CANDIDATE")
                .requestMatchers("/api/saved-jobs/**").hasRole("CANDIDATE") // NEW — explicit rule for saved jobs
                // NEW — posting/writing a company review requires a candidate login (GET above stays public)
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/companies/*/reviews").hasRole("CANDIDATE")
                .requestMatchers("/api/internship-applications/**").hasRole("CANDIDATE")
                .requestMatchers("/api/courses/*/enroll", "/api/courses/*/complete", "/api/courses/my/**").hasRole("CANDIDATE")

                .requestMatchers("/api/recruiters/**").hasRole("RECRUITER")

                .requestMatchers("/api/admin/**").hasRole("ADMIN") // covers audit-logs + reports automatically

                .requestMatchers("/api/notifications/**").authenticated()
                .requestMatchers("/api/applications/**").authenticated()
                .requestMatchers("/api/messages/**").authenticated()      // NEW — Messages module
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
