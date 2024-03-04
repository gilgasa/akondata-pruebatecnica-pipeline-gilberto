package com.arkondata.pruebatecnica.pipeline.config;

import org.springframework.core.io.ClassPathResource;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generador de scripts SQL a partir de archivos CSV.
 * <p>
 * Esta clase proporciona la funcionalidad para leer datos de un archivo CSV y
 * generar un script SQL que inserta estos datos en una base de datos. Está
 * diseñada para trabajar con archivos CSV que representan puntos de acceso
 * WiFi, transformando estos datos en instrucciones INSERT SQL.
 * </p>
 *
 * @author Gilberto García Sánchez
 * @email gilgasan1@gmail.com
 */
public class SqlScriptGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlScriptGenerator.class);

    /**
     * Genera un script SQL basado en los datos proporcionados por un archivo
     * CSV.
     * <p>
     * Este método lee el contenido de un InputStream que representa un archivo
     * CSV, y escribe el script SQL resultante en un OutputStream proporcionado.
     * Asume que el archivo CSV tiene un formato específico con un número
     * esperado de columnas.
     * </p>
     *
     * @param csvInputStream El stream de entrada que contiene los datos del
     * archivo CSV.
     * @param sqlOutputStream El stream de salida donde se escribe el script SQL
     * generado.
     */
    public static void generateSqlScript(InputStream csvInputStream, OutputStream sqlOutputStream) {
        final int expectedColumns = 7; // Número esperado de columnas en el archivo CSV.
        String insertTemplate = "INSERT INTO wifi_access_points (idgob, programa, fecha_instalacion, latitud, longitud, colonia, alcaldia) VALUES (%s, %s, %s, %s, %s, %s, %s);\n";

        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(csvInputStream, StandardCharsets.UTF_8));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(sqlOutputStream, StandardCharsets.UTF_8))) {
            reader.readLine(); // Omitir la primera línea que usualmente contiene la cabecera del archivo CSV.

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                if (data.length < expectedColumns) {
                    LOGGER.error("Línea mal formada, se esperaban " + expectedColumns + " columnas: " + line);
                    continue;
                }
                for (int i = 0; i < data.length; i++) {
                    data[i] = data[i].trim(); // Elimina espacios en blanco al inicio y al final.
                    if (data[i].isEmpty()) {
                        data[i] = "NULL";
                    } else if (i == 3 || i == 4) { // Índices para latitud y longitud.
                        data[i] = cleanNumericValue(data[i]); // Limpieza adicional para valores numéricos.
                    } else {
                        data[i] = "'" + escapeSql(data[i]) + "'";
                    }
                }
                String sql = String.format(insertTemplate, (Object[]) data);
                writer.write(sql);
            }

            LOGGER.info("Archivo SQL generado exitosamente.");
        } catch (Exception e) {
            LOGGER.error("Error al generar el script SQL", e);
        }
    }

    /**
     * Escapa caracteres especiales en valores de SQL para prevenir inyecciones
     * SQL.
     *
     * @param value El valor a escapar.
     * @return El valor con caracteres especiales escapados.
     */
    private static String escapeSql(String value) {
        return value.replace("'", "''");
    }

    /**
     * Limpia el valor numérico eliminando caracteres no deseados.
     *
     * @param value El valor numérico a limpiar.
     * @return Un valor numérico limpio, como una cadena.
     */
    private static String cleanNumericValue(String value) {
        return value.replaceAll("[^0-9.-]", "");
    }

    /**
     * Punto de entrada principal para la generación del script SQL desde la
     * línea de comandos.
     * <p>
     * Este método permite la ejecución directa de la generación del script SQL
     * proporcionando la ruta del archivo CSV como primer argumento y la ruta de
     * salida del script SQL como segundo argumento.
     * </p>
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Uso: java SqlScriptGenerator <rutaCSV> <rutaSQL>");
            return;
        }
        try {
            InputStream csvInputStream = new ClassPathResource(args[0]).getInputStream();

            // Ajuste para apuntar a 'src/main/resources'
            String outputPath = "src/main/resources/" + args[1];
            File outputFile = new File(outputPath);
            outputFile.getParentFile().mkdirs(); // Asegura que el directorio exista
            OutputStream sqlOutputStream = new FileOutputStream(outputFile);

            generateSqlScript(csvInputStream, sqlOutputStream);
        } catch (Exception e) {
            LOGGER.error("Error al generar el script SQL", e);
        }
    }
}
