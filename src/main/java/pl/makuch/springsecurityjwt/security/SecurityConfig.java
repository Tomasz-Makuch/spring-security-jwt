package pl.makuch.springsecurityjwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.makuch.springsecurityjwt.jwt.JwtConfig;
import pl.makuch.springsecurityjwt.jwt.JwtTokenVerifier;
import pl.makuch.springsecurityjwt.jwt.JwtUsernameAndPasswordAuthenticationFilter;

import javax.crypto.SecretKey;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImp userDetailsServiceImp;
    private final JwtConfig jwtConfig;

    @Autowired
    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp, JwtConfig jwtConfig) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImp);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig))
                .addFilterAfter(new JwtTokenVerifier(jwtConfig), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers("/console/**").permitAll()
                    .antMatchers("/permitAll").permitAll()
                    .antMatchers("/permitUser/**").hasRole("USER")
                    .antMatchers("/permitAdmin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
