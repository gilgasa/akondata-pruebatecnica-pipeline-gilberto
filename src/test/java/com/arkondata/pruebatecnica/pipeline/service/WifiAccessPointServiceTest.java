package com.arkondata.pruebatecnica.pipeline.service;

import com.arkondata.pruebatecnica.pipeline.exception.ResourceNotFoundException;
import com.arkondata.pruebatecnica.pipeline.model.entity.WifiAccessPoint;
import com.arkondata.pruebatecnica.pipeline.repository.WifiAccessPointRepository;
import com.arkondata.pruebatecnica.pipeline.service.impl.WifiAccessPointServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Clase de pruebas para {@link WifiAccessPointServiceImpl}.
 * <p>
 * Verifica el correcto funcionamiento de los métodos implementados en el
 * servicio de gestión de puntos de acceso WiFi, asegurando que se realicen las
 * operaciones esperadas y se manejen adecuadamente los casos de uso y
 * excepciones.
 * </p>
 */
public class WifiAccessPointServiceTest {

    private WifiAccessPointServiceImpl service;

    @Mock
    private WifiAccessPointRepository repository;

    @BeforeEach
    void setUp() {
        // Inicializa los mocks y el servicio a probar antes de cada test
        MockitoAnnotations.openMocks(this);
        service = new WifiAccessPointServiceImpl(repository);
    }

    @Test
    void findAll_ShouldReturnPageOfWifiAccessPoints() {
        // Prepara el entorno de prueba configurando el mock del repositorio
        Pageable pageable = PageRequest.of(0, 10);
        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(mockListOfWifiAccessPoints()));

        // Ejecuta el método a probar
        Page<WifiAccessPoint> result = service.findAll(pageable);

        // Verifica los resultados y las interacciones esperadas
        assertNotNull(result, "El resultado no debe ser null");
        assertEquals(10, result.getContent().size(), "El tamaño de la página esperado es 10");
    }

    @Test
    void findById_ShouldReturnWifiAccessPointWhenExists() {
        // Configura el mock del repositorio para devolver un WifiAccessPoint existente
        Long id = 1L;
        WifiAccessPoint wifiAccessPoint = new WifiAccessPoint();
        wifiAccessPoint.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(wifiAccessPoint));

        // Ejecuta el método a probar
        WifiAccessPoint result = service.findById(id);

        // Verifica que el resultado sea el esperado
        assertNotNull(result, "El punto de acceso WiFi encontrado no debe ser null");
        assertEquals(id, result.getId(), "El ID del punto de acceso WiFi encontrado debe coincidir con el solicitado");
    }

    @Test
    void findById_ShouldThrowResourceNotFoundExceptionWhenDoesNotExist() {
        // Configura el mock del repositorio para simular que el WifiAccessPoint no existe
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Verifica que se lance la excepción esperada cuando el recurso no existe
        assertThrows(ResourceNotFoundException.class, () -> service.findById(id), "Se debe lanzar ResourceNotFoundException cuando el punto de acceso WiFi no existe");
    }

    /**
     * Crea una lista ficticia de objetos WifiAccessPoint para las pruebas.
     *
     * @return Lista de objetos WifiAccessPoint simulados.
     */
    private List<WifiAccessPoint> mockListOfWifiAccessPoints() {
        List<WifiAccessPoint> accessPoints = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            WifiAccessPoint point = new WifiAccessPoint();
            // Configura los atributos del punto de acceso con valores de prueba
            point.setId((long) i);
            point.setIdgob("IDGOB" + i);
            point.setPrograma("Programa" + i);
            point.setFecha_instalacion("2024-01-01");
            point.setLatitud(19.432608 + i);
            point.setLongitud(-99.133209 + i);
            point.setColonia("Colonia" + i);
            point.setAlcaldia("Alcaldía" + i);
            accessPoints.add(point);
        }
        return accessPoints;
    }

}
