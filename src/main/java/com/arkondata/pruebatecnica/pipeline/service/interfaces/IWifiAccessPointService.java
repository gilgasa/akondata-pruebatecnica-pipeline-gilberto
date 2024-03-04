package com.arkondata.pruebatecnica.pipeline.service.interfaces;

import com.arkondata.pruebatecnica.pipeline.model.entity.WifiAccessPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interfaz para el servicio de gestión de puntos de acceso WiFi.
 * <p>
 * Define los contratos de servicio para realizar operaciones CRUD sobre los
 * puntos de acceso WiFi, así como para realizar búsquedas especializadas por
 * colonia y proximidad geográfica.
 * </p>
 */
public interface IWifiAccessPointService {

    /**
     * Obtiene todos los puntos de acceso WiFi disponibles, paginados.
     *
     * @param pageable Configuración de paginación y ordenación.
     * @return Una página conteniendo puntos de acceso WiFi.
     */
    Page<WifiAccessPoint> findAll(Pageable pageable);

    /**
     * Busca un punto de acceso WiFi por su ID.
     *
     * @param id El ID del punto de acceso WiFi a buscar.
     * @return El punto de acceso WiFi encontrado, o lanza una excepción si no
     * se encuentra.
     */
    WifiAccessPoint findById(Long id);

    /**
     * Encuentra puntos de acceso WiFi por el nombre de la colonia, paginados.
     *
     * @param colonia El nombre de la colonia donde buscar los puntos de acceso
     * WiFi.
     * @param pageable Configuración de paginación y ordenación.
     * @return Una página de puntos de acceso WiFi encontrados en la colonia
     * especificada.
     */
    Page<WifiAccessPoint> findByColonia(String colonia, Pageable pageable);

    /**
     * Encuentra puntos de acceso WiFi por proximidad a una ubicación
     * geográfica, paginados.
     * <p>
     * Realiza la búsqueda basada en la latitud, longitud y distancia
     * especificadas, retornando puntos de acceso WiFi dentro del radio de
     * distancia alrededor de la ubicación dada.
     * </p>
     *
     * @param latitude La latitud desde donde realizar la búsqueda.
     * @param longitude La longitud desde donde realizar la búsqueda.
     * @param distance La distancia en kilómetros para limitar la búsqueda.
     * @param pageable Configuración de paginación y ordenación.
     * @return Una página de puntos de acceso WiFi que se encuentran dentro del
     * área especificada.
     */
    Page<WifiAccessPoint> findByProximity(double latitude, double longitude, double distance, Pageable pageable);
}
