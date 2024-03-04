package com.arkondata.pruebatecnica.pipeline.config;

import org.springframework.beans.factory.annotation.Value;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configura Swagger para generar documentación interactiva de la API.
 * <p>
 * Esta configuración establece los parámetros generales para la documentación
 * de la API, incluyendo información sobre el proyecto, la versión, los términos
 * de servicio, el contacto, y la licencia. Se utiliza Swagger UI para
 * visualizar la documentación generada y probar los endpoints de la API
 * directamente desde el navegador.
 * </p>
 *
 * @author Gilberto García
 * @email gilgasan1@gmail.com
 */
@Configuration
public class SwaggerConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerConfig.class);

    @Value("${version}")
    private String appVersion; // Versión de la aplicación, inyectada desde el archivo de propiedades.

    @Value("${user.role}")
    private String userRole; // Rol de usuario, inyectado desde el archivo de propiedades para demostración.

    /**
     * Crea un bean de configuración de Swagger (Docket) para la API.
     * <p>
     * Este método configura un bean de Docket que Swagger utiliza para generar
     * la documentación de la API. Especifica el tipo de documentación (OAS_30),
     * el paquete base de los controladores, los selectores de camino, y la
     * información de la API a través de un método auxiliar.</p>
     *
     * @return Un objeto Docket configurado para la documentación de Swagger.
     */
    @Bean
    public Docket api() {
        LOGGER.info("Configurando Swagger con la versión: {}", appVersion);
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.arkondata.pruebatecnica.pipeline.controller")) // Especifica el paquete base para los controladores de la API.
                .paths(PathSelectors.any()) // Selecciona todos los caminos para la documentación.
                .build()
                .apiInfo(apiDetails()); // Incluye la información de la API definida en el método apiDetails.
    }

    /**
     * Proporciona detalles de la API para la documentación de Swagger.
     * <p>
     * Este método auxiliar construye un objeto ApiInfo con detalles como el
     * título de la API, descripción, términos de servicio, contacto, licencia,
     * y la versión. Estos detalles se visualizan en la UI de Swagger y son
     * importantes para informar a los consumidores de la API sobre su propósito
     * y cómo interactuar con ella.</p>
     *
     * @return Un objeto ApiInfo con los detalles de la API.
     */
    private ApiInfo apiDetails() {
        return new ApiInfoBuilder()
                .title("Prueba Técnica Pipeline API")
                .description("Documentación de la API para la Prueba Técnica Pipeline, proporcionando endpoints para operaciones relacionadas con puntos de acceso WiFi.")
                .termsOfServiceUrl("URL-terminos-de-servicio")
                .contact(new Contact("Gilberto García", "https://github.com/gilgasa", "gilgasan1@gmail.com"))
                .license("Licencia Prueba Tecnica 1.0")
                .version(appVersion) // Incluye la versión de la aplicación.
                .build();
    }
}
