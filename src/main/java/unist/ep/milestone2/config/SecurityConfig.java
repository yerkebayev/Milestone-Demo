package unist.ep.milestone2.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import unist.ep.milestone2.repository.UserRepository;
import unist.ep.milestone2.service.impl.UserServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private final UserRepository userRepository;
    @Bean
    public UserServiceImpl userService() { return new UserServiceImpl(userRepository); }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.exceptionHandling().accessDeniedPage("/403-page");

        AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userService()).passwordEncoder(passwordEncoder());

        http.formLogin()
                .loginPage("/")  // "/sign-in-page" Controller page
                .loginProcessingUrl("/to-enter") // <form action = "/to-enter" method = "post">
                .usernameParameter("user_email") // <input type = "email" name = "user_email">
                .passwordParameter("user_password") // <input type = "password" name = "user_password">
                .defaultSuccessUrl("/clubs") // reponse.sendRedirect("/profile")
                .failureUrl("/?autherror");

        http.logout()
                .logoutUrl("/logout") // post request to /sign-out
                .logoutSuccessUrl("/login");

        http.csrf().disable();

        return http.build();
    }

}
