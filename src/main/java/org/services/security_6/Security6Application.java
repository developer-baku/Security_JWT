package org.services.security_6;

import org.services.security_6.jwt.AuthEntryPointJwt;
import org.services.security_6.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
public class Security6Application {
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
         httpSecurity
                .authorizeHttpRequests(a->a
                        .requestMatchers("/signin").permitAll()
                        .requestMatchers("/admin").hasAuthority("ADMIN")
                        .requestMatchers("/today").hasAuthority("USER_C")
                        .requestMatchers("/yesterday").hasAuthority("USER_B")
                        .requestMatchers("/tomorrow").hasAuthority("USER_A")

                        .anyRequest().permitAll()
                )
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(f->f.authenticationEntryPoint(
                        unauthorizedHandler
                ))
                 .headers(h->h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return    httpSecurity    .build();

    }
   @Bean
   public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
   }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration b) throws Exception {
        return b.getAuthenticationManager();
    }
    @Bean
   public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
   }

    public static void main(String[] args) {
//       String s ="600000000";
//        System.out.println(Integer.parseInt(s));
        SpringApplication.run(Security6Application.class, args);
    }

}
