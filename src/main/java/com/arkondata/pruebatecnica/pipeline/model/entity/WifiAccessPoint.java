package com.arkondata.pruebatecnica.pipeline.model.entity;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;

/**
 * Entidad que representa un Punto de Acceso WiFi.
 * <p>
 * Esta entidad modela los datos de un punto de acceso WiFi, incluyendo su
 * identificación gubernamental, programa bajo el cual opera, fecha de
 * instalación, ubicación geográfica, así como la colonia y alcaldía donde se
 * encuentra.
 * </p>
 */
@Entity
@Table(name = "wifi_access_points")
public class WifiAccessPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Identificador único del punto de acceso WiFi", example = "1", required = true)
    private Long id;

    @Column(name = "idgob")
    @ApiModelProperty(notes = "Identificador gubernamental del punto de acceso", example = "MX_DF_CDMX_1")
    private String idgob;

    @Column(name = "programa")
    @ApiModelProperty(notes = "Nombre del programa bajo el cual opera el punto de acceso", example = "Internet para todos")
    private String programa;

    @Column(name = "fecha_instalacion")
    @ApiModelProperty(notes = "Fecha de instalación del punto de acceso", example = "2023-01-15")
    private String fecha_instalacion;

    @Column(name = "latitud")
    @ApiModelProperty(notes = "Latitud geográfica del punto de acceso", example = "19.4326077")
    private Double latitud;

    @Column(name = "longitud")
    @ApiModelProperty(notes = "Longitud geográfica del punto de acceso", example = "-99.133208")
    private Double longitud;

    @Column(name = "colonia")
    @ApiModelProperty(notes = "Nombre de la colonia donde se ubica el punto de acceso", example = "Centro")
    private String colonia;

    @Column(name = "alcaldia")
    @ApiModelProperty(notes = "Nombre de la alcaldía o municipio donde se ubica el punto de acceso", example = "Cuauhtémoc")
    private String alcaldia;

    /**
     * Constructor por defecto.
     */
    public WifiAccessPoint() {
    }

    /**
     * Constructor con todos los campos.
     *
     * @param idgob Identificador gubernamental del punto de acceso.
     * @param programa Nombre del programa bajo el cual opera el punto de
     * acceso.
     * @param fecha_instalacion Fecha de instalación del punto de acceso.
     * @param latitud Latitud geográfica del punto de acceso.
     * @param longitud Longitud geográfica del punto de acceso.
     * @param colonia Nombre de la colonia donde se ubica el punto de acceso.
     * @param alcaldia Nombre de la alcaldía o municipio donde se ubica el punto
     * de acceso.
     */
    public WifiAccessPoint(String idgob, String programa, String fecha_instalacion, Double latitud, Double longitud, String colonia, String alcaldia) {
        this.idgob = idgob;
        this.programa = programa;
        this.fecha_instalacion = fecha_instalacion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.colonia = colonia;
        this.alcaldia = alcaldia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdgob() {
        return idgob;
    }

    public void setIdgob(String idgob) {
        this.idgob = idgob;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getFecha_instalacion() {
        return fecha_instalacion;
    }

    public void setFecha_instalacion(String fecha_instalacion) {
        this.fecha_instalacion = fecha_instalacion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getAlcaldia() {
        return alcaldia;
    }

    public void setAlcaldia(String alcaldia) {
        this.alcaldia = alcaldia;
    }
}
