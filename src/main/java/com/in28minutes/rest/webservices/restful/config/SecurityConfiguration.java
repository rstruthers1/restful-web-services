package com.in28minutes.rest.webservices.restful.config;

import com.in28minutes.rest.webservices.restful.service.DatabaseUserDetailPasswordService;
import com.in28minutes.rest.webservices.restful.service.DatabaseUserDetailsService;
import com.in28minutes.rest.webservices.restful.service.jwt.JwtTokenAuthorizationOncePerRequestFilter;
import com.in28minutes.rest.webservices.restful.service.jwt.JwtUnAuthorizedResponseAuthenticationEntryPoint;
import com.in28minutes.rest.webservices.restful.workfactor.BcCryptWorkFactorService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final BcCryptWorkFactorService bcCryptWorkFactorService;
  private final DatabaseUserDetailsService databaseUserDetailsService;
  private final DatabaseUserDetailPasswordService databaseUserDetailPasswordService;

  private final JwtUnAuthorizedResponseAuthenticationEntryPoint jwtUnAuthorizedResponseAuthenticationEntryPoint;

  private final JwtTokenAuthorizationOncePerRequestFilter jwtAuthenticationTokenFilter;

  @Value("${jwt.get.token.uri}")
  private String authenticationPath;


  public SecurityConfiguration(
      BcCryptWorkFactorService bcCryptWorkFactorService,
      DatabaseUserDetailsService databaseUserDetailsService,
      DatabaseUserDetailPasswordService databaseUserDetailPasswordService,
      JwtUnAuthorizedResponseAuthenticationEntryPoint jwtUnAuthorizedResponseAuthenticationEntryPoint,
      JwtTokenAuthorizationOncePerRequestFilter jwtAuthenticationTokenFilter) {
    this.bcCryptWorkFactorService = bcCryptWorkFactorService;
    this.databaseUserDetailsService = databaseUserDetailsService;
    this.databaseUserDetailPasswordService = databaseUserDetailPasswordService;
    this.jwtUnAuthorizedResponseAuthenticationEntryPoint = jwtUnAuthorizedResponseAuthenticationEntryPoint;
    this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(databaseUserDetailsService)
        .passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf().disable()
        .exceptionHandling().authenticationEntryPoint(jwtUnAuthorizedResponseAuthenticationEntryPoint).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests()
        .anyRequest().authenticated();

    httpSecurity
        .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

    httpSecurity
        .headers()
        .frameOptions().sameOrigin()  //H2 Console Needs this setting
        .cacheControl(); //disable caching
  }

  @Override
  public void configure(WebSecurity webSecurity) throws Exception {
    webSecurity
        .ignoring()
        .antMatchers(
            HttpMethod.POST,
            authenticationPath
        )
        .antMatchers(HttpMethod.OPTIONS, "/**")
        .and()
        .ignoring()
        .antMatchers(
            HttpMethod.GET,
            "/" //Other Stuff You want to Ignore
        )
        .and()
        .ignoring()
        .antMatchers("/h2-console/**/**");//Should not be in Production!
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    // we must use deprecated encoder to support their encoding
    String encodingId = "bcrypt";
    Map<String, PasswordEncoder> encoders = new HashMap<>();
    encoders.put(encodingId, new BCryptPasswordEncoder(bcCryptWorkFactorService.calculateStrength()));
    encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
    encoders.put("scrypt", new SCryptPasswordEncoder());
    encoders.put("argon2", new Argon2PasswordEncoder());

    return new DelegatingPasswordEncoder(encodingId, encoders);
  }

  @Bean
  public AuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder());
    provider.setUserDetailsPasswordService(this.databaseUserDetailPasswordService);
    provider.setUserDetailsService(this.databaseUserDetailsService);
    return provider;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
