package com.delivery.delivery_app.config.security;

import com.delivery.delivery_app.api.service.account.AccountService;
import com.delivery.delivery_app.config.filter.JwtTokenFilter;
import com.delivery.delivery_app.config.security.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
// Security 사용자 정의 하겠다.
//@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AccountService accountService;

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;


//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().regexMatchers(HttpMethod.POST, "/api/accounts/join", "/api/accounts/login");
//
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
////                .antMatchers("/api/account/join", "/api/users/login").permitAll()
//                .antMatchers("/api/post").authenticated()
////                .anyRequest().permitAll()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//                .and()
//                .addFilterBefore(new JwtTokenFilter(secretKey, accountService), UsernamePasswordAuthenticationFilter.class);
//    }

    /**
     * Redis 추가 전
     */

    private List<String> SWAGGER = List.of(
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/",
            "/v3/api-docs/**"
    );

//     상속 받아서 Security 설정이 아닌 방법으로 설정 가능 하다..
//     WebSecurityConfigurerAdaptor 은 enable 되었다..
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                .permitAll()
                .mvcMatchers(
                        HttpMethod.POST,

                            "/api/accounts/join",
                            "/api/accounts/login"
                        ).permitAll()
                .mvcMatchers(SWAGGER.toArray(new String[0])
                        ).permitAll()
//                        .mvcMatchers("/api/**").authenticated()
//
                        .anyRequest().authenticated()
                ).addFilterBefore(new JwtTokenFilter(secretKey, accountService), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())

//
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .exceptionHandling()
//                .authenticationEntryPoint()
//                .and()
                ;


        // 설정
//        http.apply(new CustomSecurityConfigurer().setFlag(false));

        return http.build();
    }



}
