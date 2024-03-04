package com.arkondata.pruebatecnica.pipeline.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configura las medidas de seguridad para la aplicación.
 * <p>
 * Esta configuración incluye la desactivación de CSRF (Cross-Site Request
 * Forgery) y la configuración de las reglas de autorización de solicitudes.
 * Además, permite el acceso a todos los endpoints y deshabilita las opciones de
 * frame para la consola H2, mejorando así la compatibilidad con herramientas de
 * desarrollo sin comprometer la seguridad.
 * </p>
 *
 * @author Gilberto García Sánchez
 * @email gilgasan1@gmail.com
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    /**
     * Define la cadena de filtros de seguridad personalizada para la
     * aplicación.
     * <p>
     * A través de este método se configura el comportamiento de seguridad de la
     * aplicación, especificando qué recursos son públicos y cuáles requieren
     * autenticación. En este caso, se permite el acceso a todos los endpoints
     * sin autenticación. También se desactiva la protección CSRF, la cual es
     * importante para las aplicaciones que actúan como clientes de API REST
     * donde el cliente gestiona la sesión.
     * </p>
     *
     * @param http Objeto HttpSecurity para configurar aspectos de la seguridad
     * web.
     * @return La cadena de filtros de seguridad construida.
     * @throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        LOGGER.info("Configurando la seguridad...");

        // Configura HttpSecurity para desactivar CSRF y permitir acceso a todos los endpoints
        http
                .csrf().disable() // Desactiva la protección CSRF, adecuado para APIs que son consumidas por clientes que no mantienen una sesión.
                .authorizeRequests()
                .antMatchers("/**").permitAll() // Permite el acceso a todos los endpoints sin autenticación.
                //  Más configuraciones de seguridad específicas.
                .and()
                .headers().frameOptions().disable(); // Desactiva las opciones de frame para permitir el uso de la consola H2 en desarrollo.

        return http.build(); // Construye y retorna la configuración de seguridad.
    }
}
