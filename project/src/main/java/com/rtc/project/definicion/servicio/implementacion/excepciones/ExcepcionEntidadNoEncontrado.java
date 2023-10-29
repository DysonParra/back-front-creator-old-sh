/*
 * @fileoverview    {ExcepcionEntidadNoEncontrado}
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
package com.rtc.project.definicion.servicio.implementacion.excepciones;

/**
 * TODO: Description of {@code ExcepcionEntidadNoEncontrado}.
 *
 * @author Dyson Parra
 * @since 11
 */
public class ExcepcionEntidadNoEncontrado extends RuntimeException {

    /**
     * TODO: Description of {@code ExcepcionEntidadNoEncontrado}.
     *
     * @param id
     */
    public ExcepcionEntidadNoEncontrado(Long id) {
        super("No se puede encontrar la entidad " + id);
    }

    /**
     * TODO: Description of {@code ExcepcionEntidadNoEncontrado}.
     *
     * @param usuario
     */
    public ExcepcionEntidadNoEncontrado(String usuario) {
        super("No se puede encontrar la entidad " + usuario);
    }

}
