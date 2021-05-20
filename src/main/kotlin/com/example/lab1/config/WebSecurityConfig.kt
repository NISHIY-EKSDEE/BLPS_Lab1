package com.example.lab1.config

import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
class WebSecurityConfig(
    val jwtTokenProvider : JwtTokenProvider
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()

//        http.authorizeRequests().antMatchers("/").permitAll();

        http.authorizeRequests()
                /* PRODUCTS */
                .antMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/products").hasRole("SHOP")
                .antMatchers(HttpMethod.PUT, "/api/products/**").hasRole("SHOP")
                .antMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("SHOP")
                /* ORDERS */
                .antMatchers(HttpMethod.GET, "/api/orders").hasRole("CLIENT")
                .antMatchers(HttpMethod.GET, "/api/orders/all").hasRole("DELIVERY")
                .antMatchers(HttpMethod.GET, "/api/orders/{orderId:[\\d+]}")
                    .hasAnyAuthority("CLIENT", "DELIVERY")
                .antMatchers(HttpMethod.POST, "/api/orders").hasRole("CLIENT")
                .antMatchers(HttpMethod.PUT, "/api/orders/{orderId:[\\d+]}/status").hasRole("DELIVERY")
                /* PICKUP POINTS */
                .antMatchers(HttpMethod.GET, "/api/pickup/points/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/pickup/points").hasRole("DELIVERY")
                .antMatchers(HttpMethod.PUT, "/api/pickup/points").hasRole("DELIVERY")
                .antMatchers(HttpMethod.DELETE, "/api/pickup/points/{pointId:[\\d+]}").hasRole("DELIVERY")
                /* LOGIN AND REGISTRATION */
                .antMatchers("/register", "/login").permitAll()
                .anyRequest().authenticated()
//                /* SWAGGER */
//               .antMatchers("/swagger-ui.html").permitAll()

        http.apply(JwtTokenFilterConfigurer(jwtTokenProvider))
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/v2/api-docs/**")
        web.ignoring().antMatchers("/swagger.json")
        web.ignoring().antMatchers("/swagger-ui.html")
        web.ignoring().antMatchers("/swagger-resources/**")
        web.ignoring().antMatchers("/webjars/**")
    }

    @Bean
    fun passwordEncoder() : BCryptPasswordEncoder {
        return BCryptPasswordEncoder();
    }
}