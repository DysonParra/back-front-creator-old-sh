/*
 * @fileoverview    {ServicioGenerico}
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
package com.rtc.project.definicion.servicio;

import java.util.List;

/**
 * TODO: Definición de {@code ServicioGenerico}.
 *
 * @param <T>
 *
 * @author Dyson Parra
 * @since 11
 */
public interface ServicioGenerico<T> {

    /**
     * Guarda o actualiza los datos de un actividad.
     *
     * @param entidadDTO entidad que sera almacenada
     * @return entidad almacenada en la base de datos.
     * @throws java.lang.Exception
     */
    public T guardarActualizar(T entidadDTO) throws Exception;

    /**
     * Lista todos las entidades (actividad) existentes.
     *
     * @return lista de entidades almacenadas en la base de datos.
     * @throws java.lang.Exception
     */
    public List<T> obtenerEntidades() throws Exception;

    /**
     * Obtiene el actividad segun el id suministrado.
     *
     * @param id entidad
     * @return entidad almacenada en la base de datos.
     * @throws java.lang.Exception
     */
    public T buscarEntidad(String id) throws Exception;

    /**
     * Guarda o actualiza los datos de un actividad.
     *
     * @param id entidad que sera eliminada.
     * @throws java.lang.Exception
     */
    public void eliminarEntidad(String id) throws Exception;
}
