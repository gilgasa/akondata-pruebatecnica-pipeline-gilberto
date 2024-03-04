package com.arkondata.pruebatecnica.pipeline.repository;

import com.arkondata.pruebatecnica.pipeline.model.entity.WifiAccessPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad {@link WifiAccessPoint}.
 * <p>
 * Este repositorio extiende {@link JpaRepository}, proporcionando métodos CRUD
 * para la entidad {@link WifiAccessPoint}. Además, define métodos
 * personalizados para consultas específicas, como la búsqueda por nombre de
 * colonia y la búsqueda por proximidad geográfica.
 * </p>
 */
@Repository
public interface WifiAccessPointRepository extends JpaRepository<WifiAccessPoint, Long> {

    /**
     * Busca puntos de acceso WiFi por el nombre de la colonia.
     * <p>
     * Este método retorna una lista paginada de puntos de acceso WiFi que se
     * encuentran en la colonia especificada.
     * </p>
     *
     * @param colonia El nombre de la colonia donde buscar los puntos de acceso
     * WiFi.
     * @param pageable La configuración de paginación y ordenación.
     * @return Una página de puntos de acceso WiFi encontrados en la colonia
     * especificada.
     */
    Page<WifiAccessPoint> findByColonia(String colonia, Pageable pageable);

    /**
     * Busca puntos de acceso WiFi por proximidad a una ubicación geográfica.
     * <p>
     * Este método utiliza una consulta SQL nativa para encontrar puntos de
     * acceso WiFi dentro de una distancia específica desde un punto geográfico
     * dado, ordenando los resultados por proximidad. La distancia se especifica
     * en kilómetros.
     * </p>
     *
     * @param latitude La latitud del punto geográfico desde el cual buscar.
     * @param longitude La longitud del punto geográfico desde el cual buscar.
     * @param distance La distancia máxima (en kilómetros) dentro de la cual
     * buscar puntos de acceso.
     * @param pageable La configuración de paginación y ordenación.
     * @return Una página de puntos de acceso WiFi que se encuentran dentro de
     * la distancia especificada desde el punto geográfico dado.
     */
    @Query(value = "SELECT * FROM wifi_access_points WHERE "
            + "(6371 * acos(cos(radians(:latitude)) * cos(radians(latitud)) *"
            + "cos(radians(longitud) - radians(:longitude)) + sin(radians(:latitude)) *"
            + "sin(radians(latitud)))) < :distance ORDER BY "
            + "(6371 * acos(cos(radians(:latitude)) * cos(radians(latitud)) *"
            + "cos(radians(longitud) - radians(:longitude)) + sin(radians(:latitude)) *"
            + "sin(radians(latitud)))) ASC",
            nativeQuery = true)
    Page<WifiAccessPoint> findByProximity(@Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("distance") double distance,
            Pageable pageable);
}
