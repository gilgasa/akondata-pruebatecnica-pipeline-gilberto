package com.arkondata.pruebatecnica.pipeline.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import org.springframework.core.io.Resource;

/**
 * Ejecutor de script SQL al inicio de la aplicación.
 * <p>
 * Esta clase implementa CommandLineRunner para ejecutar un script SQL
 * específico al inicio de la aplicación. Se utiliza para inicializar la base de
 * datos con datos necesarios o para realizar migraciones simples.
 * </p>
 *
 * @author Gilberto García Sánchez
 * @email gilgasan1@gmail.com
 */
@Component
public class SqlScriptRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlScriptRunner.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Ejecuta el script SQL al inicio de la aplicación.
     * <p>
     * Este método carga y ejecuta un script SQL ubicado en el classpath,
     * especificado por la ruta relativa 'data/wifi_access_points.sql'. Este
     * enfoque permite una fácil inicialización de la base de datos con datos
     * predefinidos o estructuras de base de datos al desplegar la aplicación.
     * </p>
     *
     * @param args Argumentos de línea de comandos pasados al iniciar la
     * aplicación.
     * @throws Exception Si ocurre un error durante la ejecución del script SQL.
     */
    @Override
    public void run(String... args) throws Exception {
        String resourcePath = "classpath:data/wifi_access_points.sql"; // Ruta del script SQL en el classpath.

        Resource resource = resourceLoader.getResource(resourcePath); // Carga el recurso del script SQL.

        if (resource.exists() && resource.isReadable()) {
            ScriptUtils.executeSqlScript(dataSource.getConnection(), resource); // Ejecuta el script SQL.
            LOGGER.info("Script SQL ejecutado exitosamente al iniciar la aplicación.");
            verificarDatos(); // Verifica los datos después de la ejecución del script.
        } else {
            LOGGER.warn("Script SQL no encontrado, omitiendo ejecución.");
        }
    }

    /**
     * Verifica los datos en la base de datos después de la ejecución del script
     * SQL.
     * <p>
     * Este método realiza una consulta simple para contar los puntos de acceso
     * WiFi registrados en la base de datos, sirviendo como una verificación
     * básica de que el script SQL se ejecutó correctamente y los datos
     * esperados están presentes.
     * </p>
     */
    private void verificarDatos() {
        String sql = "SELECT COUNT(*) FROM wifi_access_points"; // Consulta para contar los puntos de acceso WiFi.
        Integer cantidad = jdbcTemplate.queryForObject(sql, Integer.class); // Ejecuta la consulta.

        if (cantidad != null && cantidad > 0) {
            LOGGER.info("Hay {} puntos de acceso WiFi registrados en la base de datos.", cantidad);
        } else {
            LOGGER.warn("No se encontraron puntos de acceso WiFi en la base de datos.");
        }
    }
}
