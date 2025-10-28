package com.archivos.ecommerce.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas (sin autenticación)
                .requestMatchers("/api/auth/**", "/api/test/**").permitAll()
                .requestMatchers("/api/categorias/**").permitAll()
                .requestMatchers("/api/productos", "/api/productos/{id}").permitAll()
                
                // Rutas exclusivas para ADMINISTRADOR
                .requestMatchers("/api/admin/**").hasAuthority("ADMINISTRADOR")
                
                // Rutas para MODERADOR y ADMINISTRADOR
                .requestMatchers("/api/moderador/**").hasAnyAuthority("MODERADOR", "ADMINISTRADOR")
                .requestMatchers("/api/solicitudes/**").hasAnyAuthority("MODERADOR", "ADMINISTRADOR")
                
                // Rutas para LOGISTICA y ADMINISTRADOR
                .requestMatchers("/api/logistica/**").hasAnyAuthority("LOGISTICA", "ADMINISTRADOR")
                .requestMatchers("/api/pedidos/gestionar/**").hasAnyAuthority("LOGISTICA", "ADMINISTRADOR")
                
                // Rutas para MODERADOR, LOGISTICA y ADMINISTRADOR
                .requestMatchers("/api/sanciones/**").hasAnyAuthority("MODERADOR", "LOGISTICA", "ADMINISTRADOR")
                
                // Rutas para usuarios autenticados (todos los roles)
                .requestMatchers("/api/usuarios/perfil").authenticated()
                .requestMatchers("/api/carrito/**").authenticated()
                .requestMatchers("/api/pedidos/**").authenticated()
                .requestMatchers("/api/calificaciones/**").authenticated()
                .requestMatchers("/api/notificaciones/**").authenticated()
                .requestMatchers("/api/productos/crear").authenticated()
                .requestMatchers("/api/productos/actualizar/**").authenticated()
                .requestMatchers("/api/productos/eliminar/**").authenticated()
                
                // Todas las demás rutas requieren autenticación
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8080"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}