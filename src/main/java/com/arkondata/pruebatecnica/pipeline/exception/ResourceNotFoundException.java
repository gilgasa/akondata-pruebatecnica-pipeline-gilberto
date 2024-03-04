package com.arkondata.pruebatecnica.pipeline.exception;

/**
 * Excepción personalizada lanzada cuando un recurso específico no se encuentra.
 * <p>
 * Esta excepción se utiliza principalmente en los servicios o controladores
 * para indicar que una operación sobre un recurso específico no puede
 * completarse porque el recurso no existe. Por ejemplo, se puede lanzar al
 * intentar buscar un recurso por su ID y este no existe en la base de datos.
 * </p>
 *
 * @author Gilberto García
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor que crea una instancia de {@code ResourceNotFoundException}
     * con un mensaje formatado que incluye el nombre del recurso, el campo por
     * el cual se realizó la búsqueda y el valor del campo que no produjo
     * resultados.
     *
     * @param resourceName El nombre del recurso que no se encontró. Ejemplo:
     * "Usuario".
     * @param fieldName El nombre del campo por el cual se buscaba el recurso.
     * Ejemplo: "ID".
     * @param fieldValue El valor del campo por el cual se buscaba el recurso.
     * Ejemplo: 10.
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s no encontrado con %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
