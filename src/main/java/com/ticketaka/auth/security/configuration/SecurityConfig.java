package com.ticketaka.auth.security.configuration;


import com.ticketaka.auth.security.jwt.JwtAuthenticationFilter;
import com.ticketaka.auth.security.jwt.JwtUtils;
import com.ticketaka.auth.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtils jwtUtils;
    private final RedisService redisService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// jwt 를 사용하기 때문에 세션사용 X
                .and()
                .authorizeRequests()
                .antMatchers("/member/login").permitAll()
                .antMatchers("/member/signup").permitAll()
                .antMatchers("/member/checkDuplicateEmail").permitAll()
                .antMatchers("/member/info").authenticated()
                .antMatchers("/member/logout").authenticated()
//                .antMatchers("/member/signup").permitAll()
                //.antMatchers("/main/**").permitAll()
                .antMatchers("/performance/cat").permitAll()
                .antMatchers("/performance/search").permitAll()
                .antMatchers("/performance").permitAll()
                .antMatchers("/performance/session/**").permitAll()
                .antMatchers("/performance/rsv/**").authenticated()
                .antMatchers("/reservation/**").authenticated()
                .anyRequest().authenticated() // 그 밖에 요청은 모두 인증정보가 있어야 함
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtils, redisService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
