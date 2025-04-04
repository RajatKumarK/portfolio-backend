//package com.rajatkumar.portfolio.portfolio_backend.Configuration;
//
//import com.rajatkumar.portfolio.portfolio_backend.Configuration.utility.JwtAuthenticationFilter;
//import java.util.List;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//  private final JwtAuthenticationFilter jwtAuthFilter;
//
//  private final AuthenticationProvider authenticationProvider;
//
//  private static final String[] AUTH_WHITELIST = {
//      "/auth/**",
//      "/api/**",
//      "/swagger-ui/**",
//      "/swagger-ui.html",
//      "/**"
//  };
//
//  public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter,
//      AuthenticationProvider authenticationProvider) {
//    this.jwtAuthFilter = jwtAuthFilter;
//    this.authenticationProvider = authenticationProvider;
//  }
//
//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http
//        .csrf(AbstractHttpConfigurer::disable)
//        .authorizeHttpRequests(auth -> auth
//            .requestMatchers(AUTH_WHITELIST).permitAll()
//            .anyRequest().authenticated()
//        )
//        .sessionManagement(session -> session
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        )
//        .authenticationProvider(authenticationProvider)
//        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//    return http.build();
//  }
//
//  @Bean
//  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//    return config.getAuthenticationManager();
//  }
//
//  @Bean
//  CorsConfigurationSource corsConfigurationSource() {
//    CorsConfiguration configuration = new CorsConfiguration();
//    configuration.setAllowedOrigins(List.of("http://localhost:3000")); // Update with your frontend URL
//    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
//
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/**", configuration);
//    return source;
//  }
//}
//
////  @Bean
////  public AuthenticationProvider authenticationProvider() {
////    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
////    authProvider.setUserDetailsService(userDetailsService);
////    authProvider.setPasswordEncoder(passwordEncoder());
////    return authProvider;
////  }
//
////  @Bean
////  public PasswordEncoder passwordEncoder() {
////    return new BCryptPasswordEncoder();
////  }
//
//
//
//
//
//
//
//
///*
////  @Bean
////  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////    return http
////        .csrf().disable()
////        .authorizeHttpRequests(auth -> auth
////            .requestMatchers("/api/auth/**").permitAll()
////            .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
////            .requestMatchers("/api/contact").permitAll()
////            .anyRequest().authenticated()
////        )
////        .sessionManagement(session -> session
////            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////        )
////        .authenticationProvider(authenticationProvider())
////        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
////        .build();
////  }
// */
