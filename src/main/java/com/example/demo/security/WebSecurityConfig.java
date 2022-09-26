package com.example.demo.security;

import com.example.demo.entryPoint.AuthEntryPointJwt;
import com.example.demo.filter.AuthTokenFilter;
import com.example.demo.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers(HttpMethod.OPTIONS, "/**")
                //.mvcMatchers("/auth/**")
                .mvcMatchers("/actuator/health")
                .mvcMatchers("/app/**/*.{js,html,css}")
                .mvcMatchers("/i18n/**")
                .mvcMatchers("/content/**")
                .mvcMatchers("/swagger-ui/index.html")
                .mvcMatchers("/management/health")
                .mvcMatchers("/management/prometheus")
                .mvcMatchers("/management/info")
                .mvcMatchers("/actuator/health")
                .mvcMatchers("/swagger-resources/**", "/swagger-ui.html")
                .mvcMatchers("/v2/api-docs", "/v3/api-docs/**", "/webjars/**")
                .mvcMatchers("/api/docs")
                .mvcMatchers("/v3/api-docs")
                .mvcMatchers("/api-docs")
                .mvcMatchers("/v3/api-docs.yaml")
                .mvcMatchers("/webjars/springfox-swagger-ui/springfox.css")
                .mvcMatchers("/webjars/springfox-swagger-ui/springfox.js")
                .mvcMatchers("/webjars/springfox-swagger-ui/swagger-ui-standalone-preset.js")
                .mvcMatchers("/webjars/springfox-swagger-ui/swagger-ui-bundle.js")
                .mvcMatchers("/webjars/springfox-swagger-ui/swagger-ui.css")
                .mvcMatchers("/configuration/ui", "/configuration/security")
                .mvcMatchers("/swagger-ui/**")
                .mvcMatchers("/swagger-ui")
                .mvcMatchers("/api-docs")
                .mvcMatchers("/swagger-ui/index.html")
                .mvcMatchers("/swagger-resources/**", "/swagger-ui.html")
                .mvcMatchers("/register")
                .mvcMatchers("/login");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                .contentSecurityPolicy("default-src 'self';");
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .anyRequest().authenticated().and()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}