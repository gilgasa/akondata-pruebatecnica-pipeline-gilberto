package com.arkondata.pruebatecnica.pipeline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot.
 * <p>
 * Esta clase sirve como el punto de entrada para iniciar la aplicación Spring
 * Boot. Utiliza la anotación {@code @SpringBootApplication} para habilitar la
 * configuración automática de Spring Boot, el escaneo de componentes y otras
 * configuraciones por defecto necesarias para arrancar la aplicación.
 * </p>
 */
@SpringBootApplication
public class PipelineApplication {

    /**
     * Método principal que inicia la aplicación.
     * <p>
     * Utiliza {@link SpringApplication#run} para arrancar la aplicación,
     * pasando la clase actual como argumento junto con los argumentos de línea
     * de comandos.
     * </p>
     *
     * @param args Argumentos de línea de comandos pasados al iniciar la
     * aplicación.
     */
    public static void main(String[] args) {
        SpringApplication.run(PipelineApplication.class, args);
    }
}
