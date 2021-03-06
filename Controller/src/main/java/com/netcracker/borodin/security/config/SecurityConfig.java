package com.netcracker.borodin.security.config;

import com.netcracker.borodin.entity.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebSecurity
@EnableSwagger2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsServiceImpl;

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public SecurityConfig(
      UserDetailsService userDetailsServiceImpl, PasswordEncoder passwordEncoder) {
    this.userDetailsServiceImpl = userDetailsServiceImpl;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers(
            "/v2/api-docs",
            "/swagger-resources/configuration/ui",
            "/swagger-resources",
            "/swagger-resources/configuration/security",
            "/swagger-ui.html",
            "/webjars/**")
        .permitAll()
        .and()
        .authorizeRequests()
        .antMatchers("/books/top/**", "/signUp/**")
        .permitAll()
        .antMatchers("/authors/**", "/books/**", "/comments/**", "/genres/**", "/list/**")
        .authenticated()
        .antMatchers("/users/**")
        .hasAuthority(Role.ADMIN.name())
        .and()
        .httpBasic()
        .and()
        .csrf()
        .disable();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
  }
}
