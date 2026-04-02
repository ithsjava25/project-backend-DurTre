package org.example.crimearchive.config;

import org.example.crimearchive.polis.Account;
import org.example.crimearchive.polis.AccountUserDetailsService;
import org.example.crimearchive.polis.UserRepository;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/").permitAll();
                    auth.requestMatchers("/static/**").permitAll();
                    auth.requestMatchers("/favicon.ico").permitAll();
                    auth.requestMatchers("/index").permitAll();
                    auth.requestMatchers("/error").permitAll();

                    auth.requestMatchers("/private").hasRole("ADMIN");
                    auth.requestMatchers("/userpage").hasRole("USER");


                    auth.anyRequest().authenticated();
                })
                //Bygger inloggingsformuläret automatiskt
                .oauth2Login(Customizer.withDefaults())
                .formLogin(formLogin -> formLogin.defaultSuccessUrl("/userpage"))
        .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AccountUserDetailsService userDetailsService(UserRepository repo){
        return new AccountUserDetailsService(repo);
    }

}
