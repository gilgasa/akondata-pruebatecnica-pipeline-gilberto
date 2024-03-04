package com.arkondata.pruebatecnica.pipeline.controller;

import com.arkondata.pruebatecnica.pipeline.model.entity.WifiAccessPoint;
import com.arkondata.pruebatecnica.pipeline.service.interfaces.IWifiAccessPointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para la gestión de Puntos de Acceso WiFi.
 * <p>
 * Proporciona endpoints para operaciones CRUD y consultas especializadas
 * relacionadas con los Puntos de Acceso WiFi, incluyendo búsqueda por ID, por
 * nombre de colonia y por proximidad geográfica.
 * </p>
 *
 * @author Gilberto García
 */
@Validated
@RestController
@Api(tags = "WifiAccessPoints-Controller", value = "Controlador para operaciones relacionadas con los puntos de acceso WiFi")
@RequestMapping("/api/wifi-access-points")
public class WifiAccessPointController {

    private final IWifiAccessPointService wifiAccessPointService;

    @Autowired
    public WifiAccessPointController(IWifiAccessPointService wifiAccessPointService) {
        this.wifiAccessPointService = wifiAccessPointService;
    }

    /**
     * Obtiene todos los puntos de acceso WiFi disponibles.
     * <p>
     * Retorna una lista paginada de puntos de acceso WiFi, permitiendo la
     * especificación de número de página, tamaño de página, y criterios de
     * ordenación.
     * </p>
     *
     * @param pageable Configuración de paginación y ordenación.
     * @return Una página de puntos de acceso WiFi.
     */
    @GetMapping
    @ApiOperation(
            value = "Obtiene todos los puntos de acceso WiFi",
            notes = "Proporciona una lista paginada de todos los puntos de acceso WiFi disponibles. "
            + "Permite especificar número de página, tamaño de página y criterios de ordenación."
    )
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                value = "Número de la página que se desea obtener. La numeración comienza en 0.")
        ,
    @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                value = "Cantidad de registros por página.")
        ,
    @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                value = "Criterios de ordenación en formato: propiedad[,asc|desc]. Ejemplo: id,asc.")
        ,
     @ApiImplicitParam(name = "offset", dataType = "integer", paramType = "query",
                value = "Posición inicial desde la cual se empiezan a recuperar los registros en el conjunto de resultados de la consulta. Calculado como 'page' * 'size'.")
        ,
   @ApiImplicitParam(name = "pageNumber", dataType = "integer", paramType = "query",
                value = "Número de página que se desea obtener, siendo 0 el inicio. Funciona como un alias de 'page'.")
        ,
    @ApiImplicitParam(name = "pageSize", dataType = "integer", paramType = "query",
                value = "Número máximo de registros por página. Funciona como un alias de 'size'.")
        ,
        
    @ApiImplicitParam(name = "paged", dataType = "boolean", paramType = "query",
                value = "Indica si la paginación está habilitada.")
        ,
    @ApiImplicitParam(name = "unpaged", dataType = "boolean", paramType = "query",
                value = "Indica si se desea deshabilitar la paginación y recuperar todos los registros.")
        ,
    @ApiImplicitParam(name = "sort.sorted", dataType = "boolean", paramType = "query",
                value = "Indica si se ha solicitado alguna ordenación.")
        ,
    @ApiImplicitParam(name = "sort.unsorted", dataType = "boolean", paramType = "query",
                value = "Indica si no se desea aplicar ninguna ordenación.")
    })
    public Page<WifiAccessPoint> getAllWifiAccessPoints(@PageableDefault(size = 20) Pageable pageable) {
        return wifiAccessPointService.findAll(pageable);
    }

    /**
     * Obtiene un punto de acceso WiFi por su ID.
     * <p>
     * Busca un punto de acceso específico por su ID. Si el punto de acceso no
     * se encuentra, se devuelve una respuesta HTTP 404 Not Found.
     * </p>
     *
     * @param id ID del punto de acceso WiFi a buscar.
     * @return El punto de acceso WiFi encontrado.
     */
    @GetMapping("/{id}")
    @ApiOperation(
            value = "Obtiene un punto de acceso WiFi por su ID",
            notes = "Proporciona un ID para buscar un punto de acceso WiFi específico. "
            + "Si no se encuentra un punto de acceso con el ID proporcionado, "
            + "se devolverá una respuesta HTTP 404 Not Found."
    )
    public WifiAccessPoint getWifiAccessPointById(
            @ApiParam(
                    value = "ID del punto de acceso WiFi que se desea obtener",
                    required = true,
                    example = "123"
            )
            @PathVariable Long id) {
        return wifiAccessPointService.findById(id);
    }

    /**
     * Obtiene puntos de acceso WiFi por nombre de colonia.
     * <p>
     * Busca todos los puntos de acceso WiFi localizados en una colonia
     * específica. La respuesta es paginada y permite controlar el número de
     * página y tamaño de página.
     * </p>
     *
     * @param colonia Nombre de la colonia para la búsqueda.
     * @param pageable Configuración de paginación y ordenación.
     * @return Una página de puntos de acceso WiFi localizados en la colonia
     * especificada.
     */
    @GetMapping("/colonia")
    @ApiOperation(
            value = "Obtiene puntos de acceso WiFi por colonia",
            notes = "Proporciona el nombre de una colonia para buscar todos los puntos de acceso WiFi en esa área. "
            + "La respuesta es paginada y se puede controlar mediante parámetros de consulta adicionales, como 'page' y 'size'."
    )
    public Page<WifiAccessPoint> getWifiAccessPointsByColonia(
            @ApiParam(
                    value = "Nombre de la colonia para buscar los puntos de acceso WiFi",
                    required = true,
                    example = "Condesa"
            )
            @RequestParam @NotBlank(message = "El nombre de la colonia no puede estar vacío")
            @Size(min = 2, max = 100, message = "El nombre de la colonia debe tener entre 2 y 100 caracteres") String colonia,
            Pageable pageable) {
        return wifiAccessPointService.findByColonia(colonia, pageable);
    }

    /**
     * Obtiene puntos de acceso WiFi por proximidad geográfica.
     * <p>
     * Realiza una búsqueda de puntos de acceso WiFi basada en la ubicación
     * geográfica proporcionada y una distancia opcional en kilómetros. Devuelve
     * una lista paginada de puntos de acceso dentro del radio de la distancia
     * especificada desde las coordenadas dadas.
     * </p>
     *
     * @param latitude Latitud geográfica desde donde realizar la búsqueda.
     * @param longitude Longitud geográfica desde donde realizar la búsqueda.
     * @param distance Distancia en kilómetros para la búsqueda desde el punto
     * de coordenadas dado.
     * @param pageable Configuración de paginación y ordenación.
     * @return Una página de puntos de acceso WiFi dentro del radio de búsqueda
     * especificado.
     */
    @GetMapping("/proximity")
    @ApiOperation(
            value = "Obtiene puntos de acceso WiFi por proximidad",
            notes = "Realiza una búsqueda de puntos de acceso WiFi basada en la ubicación geográfica proporcionada y una distancia opcional. "
            + "La búsqueda devuelve una lista paginada de puntos de acceso que se encuentran dentro del radio de la distancia especificada desde las coordenadas dadas."
    )
    @ApiImplicitParams({
        @ApiImplicitParam(name = "offset", dataType = "integer", paramType = "query",
                value = "Posición inicial desde la cual se empiezan a recuperar los registros en el conjunto de resultados de la consulta. Calculado como 'page' * 'size'.")
        ,
   @ApiImplicitParam(name = "pageNumber", dataType = "integer", paramType = "query",
                value = "Número de página que se desea obtener, siendo 0 el inicio. Funciona como un alias de 'page'.")
        ,
    @ApiImplicitParam(name = "pageSize", dataType = "integer", paramType = "query",
                value = "Número máximo de registros por página. Funciona como un alias de 'size'.")
        ,
        
    @ApiImplicitParam(name = "paged", dataType = "boolean", paramType = "query",
                value = "Indica si la paginación está habilitada.")
        ,
    @ApiImplicitParam(name = "unpaged", dataType = "boolean", paramType = "query",
                value = "Indica si se desea deshabilitar la paginación y recuperar todos los registros.")
        ,
    @ApiImplicitParam(name = "sort.sorted", dataType = "boolean", paramType = "query",
                value = "Indica si se ha solicitado alguna ordenación.")
        ,
    @ApiImplicitParam(name = "sort.unsorted", dataType = "boolean", paramType = "query",
                value = "Indica si no se desea aplicar ninguna ordenación.")
    })
    public Page<WifiAccessPoint> getWifiAccessPointsByProximity(
            @ApiParam(
                    value = "Latitud geográfica desde donde realizar la búsqueda",
                    required = true,
                    example = "19.432608"
            )
            @RequestParam @DecimalMin(value = "-90.0", message = "La latitud mínima permitida es -90")
            @DecimalMax(value = "90.0", message = "La latitud máxima permitida es 90") double latitude,
            @ApiParam(
                    value = "Longitud geográfica desde donde realizar la búsqueda",
                    required = true,
                    example = "-99.133209"
            )
            @RequestParam @DecimalMin(value = "-180.0", message = "La longitud mínima permitida es -180")
            @DecimalMax(value = "180.0", message = "La longitud máxima permitida es 180") double longitude,
            @ApiParam(
                    value = "Distancia en kilómetros para la búsqueda desde el punto de coordenadas dado",
                    required = false,
                    example = "5"
            )
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "La distancia para la búsqueda debe ser de al menos 1km") double distance,
            Pageable pageable
    ) {
        return wifiAccessPointService.findByProximity(latitude, longitude, distance, pageable);
    }
}
