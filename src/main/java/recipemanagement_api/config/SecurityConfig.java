package recipemanagement_api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import  recipemanagement_api.Util.JwtUtil;

import java.io.IOException;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll() //login and register allowing
                .requestMatchers(HttpMethod.GET, "/recipes/**").permitAll() //  recipes viewed my all
                .anyRequest().authenticated() // other requ needs jwt
            )
            .addFilterBefore(new JwtAuthenticationFilter(), BasicAuthenticationFilter.class);
        return http.build();
    }

   
    public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
        public JwtAuthenticationFilter() {
            super(authentication -> null); 
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                        FilterChain chain) throws IOException, ServletException {
            String header = request.getHeader("Authorization");

            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7);
                try {
                    String username = JwtUtil.extractUsername(token);
                    String role = JwtUtil.extractRole(token); // role extraced from jwt

                    if (username != null && role != null) {
                        // ✅ Add role as authority so @PreAuthorize works
                        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(authority));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } catch (Exception e) {
                    System.out.println("❌ Invalid token: " + e.getMessage());
                }
            }

            chain.doFilter(request, response);
        }
    }
}