package com.arkondata.pruebatecnica.pipeline.service.impl;

import com.arkondata.pruebatecnica.pipeline.exception.ResourceNotFoundException;
import com.arkondata.pruebatecnica.pipeline.model.entity.WifiAccessPoint;
import com.arkondata.pruebatecnica.pipeline.repository.WifiAccessPointRepository;
import com.arkondata.pruebatecnica.pipeline.service.interfaces.IWifiAccessPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio para gestionar los puntos de acceso WiFi.
 * <p>
 * Esta clase provee la lógica de negocio para operaciones CRUD sobre los puntos
 * de acceso WiFi, así como búsquedas específicas por colonia y proximidad
 * geográfica.
 * </p>
 */
@Service
public class WifiAccessPointServiceImpl implements IWifiAccessPointService {

    private final WifiAccessPointRepository wifiAccessPointRepository;

    /**
     * Constructor que inyecta el repositorio de puntos de acceso WiFi.
     *
     * @param wifiAccessPointRepository El repositorio asociado a los puntos de
     * acceso WiFi.
     */
    @Autowired
    public WifiAccessPointServiceImpl(WifiAccessPointRepository wifiAccessPointRepository) {
        this.wifiAccessPointRepository = wifiAccessPointRepository;
    }

    /**
     * Encuentra todos los puntos de acceso WiFi disponibles, paginados.
     *
     * @param pageable Configuración de paginación y ordenación.
     * @return Una página de puntos de acceso WiFi.
     */
    @Override
    public Page<WifiAccessPoint> findAll(Pageable pageable) {
        return wifiAccessPointRepository.findAll(pageable);
    }

    /**
     * Busca un punto de acceso WiFi por su ID.
     * <p>
     * Si el punto de acceso no se encuentra, se lanza una
     * {@link ResourceNotFoundException}.
     * </p>
     *
     * @param id El ID del punto de acceso WiFi a buscar.
     * @return El punto de acceso WiFi encontrado.
     */
    @Override
    public WifiAccessPoint findById(Long id) {
        return wifiAccessPointRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WifiAccessPoint", "id", id));
    }

    /**
     * Encuentra puntos de acceso WiFi por el nombre de la colonia, paginados.
     *
     * @param colonia El nombre de la colonia donde buscar los puntos de acceso
     * WiFi.
     * @param pageable Configuración de paginación y ordenación.
     * @return Una página de puntos de acceso WiFi encontrados en la colonia
     * especificada.
     */
    @Override
    public Page<WifiAccessPoint> findByColonia(String colonia, Pageable pageable) {
        return wifiAccessPointRepository.findByColonia(colonia, pageable);
    }

    /**
     * Encuentra puntos de acceso WiFi por proximidad a una ubicación
     * geográfica, paginados.
     * <p>
     * Utiliza la latitud, la longitud y la distancia para realizar la búsqueda.
     * </p>
     *
     * @param latitude La latitud desde donde buscar.
     * @param longitude La longitud desde donde buscar.
     * @param distance La distancia máxima en kilómetros para incluir puntos de
     * acceso en el resultado.
     * @param pageable Configuración de paginación y ordenación.
     * @return Una página de puntos de acceso WiFi que se encuentran dentro de
     * la distancia especificada desde el punto geográfico dado.
     */
    @Override
    public Page<WifiAccessPoint> findByProximity(double latitude, double longitude, double distance, Pageable pageable) {
        return wifiAccessPointRepository.findByProximity(latitude, longitude, distance, pageable);
    }

}
