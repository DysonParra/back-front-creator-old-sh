/*
 * @fileoverview    {Pesaje}
 *
 * @version         2.0
 *
 * @author          Dyson Arley Parra Tilano <dysontilano@gmail.com>
 *
 * @copyright       Dyson Parra
 * @see             github.com/DysonParra
 *
 * History
 * @version 1.0     Implementation done.
 * @version 2.0     Documentation added.
 */
package com.rtc.project.definicion.dominio;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO: Description of {@code Pesaje}.
 *
 * @author Dyson Parra
 * @since 11
 */
@Entity
@Table(catalog = "cardinal", schema = "")
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Pesaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long intId;
    private BigInteger intTiqueteNumero;
    @Column(length = 10)
    private String strPlaca;
    @Column(length = 50)
    private String strCodigo;
    private BigInteger intNumeroInterno;
    @Column(length = 20)
    private String strTipoVehiculo;
    @Column(length = 100)
    private String strConductor;
    @Column(length = 20)
    private String strCedula;
    @Column(length = 100)
    private String strProducto;
    @Column(length = 100)
    private String strPlanta;
    @Column(length = 100)
    private String strCliente;
    @Column(length = 100)
    private String strTransportadora;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtFechaHoraPesoVacio;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtFechaHoraPesoLleno;
    @Column(length = 50)
    private String strCiv;
    @Column(length = 80)
    private String strDireccion;
    @Column(length = 100)
    private String strEntregadoPor;
    @Column(length = 100)
    private String strRecibidoPor;
    @Column(length = 50)
    private String strShipment;
    @Column(length = 50)
    private String strSello;
    @Column(length = 50)
    private String strR;
    @Column(length = 50)
    private String strContenedor;
    @Column(length = 250)
    private String strObservacion;
    @Column(length = 21)
    private String enmTipoIngreso;

    /**
     * Obtiene el valor en {String} del objeto actual.
     *
     * @return un {String} con la representación del objeto.
     */
    @Override
    public String toString() {
        return "Pesaje[ intId=" + intId + " ]";
    }

}
