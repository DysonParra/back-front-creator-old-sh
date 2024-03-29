/*
 * @fileoverview    {RecursoEntidadGenerica}
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
package com.rtc.project.definicion.web.rest;

import com.rtc.project.definicion.servicio.ServicioEntidadGenerica;
import com.rtc.project.definicion.servicio.dto.EntidadGenericaDTO;
import com.rtc.project.definicion.web.rest.ensamblador.EnsambladorRecursoEntidadGenerica;
import com.rtc.project.definicion.web.rest.util.PaginationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * TODO: Description of {@code RecursoEntidadGenerica}.
 *
 * @author Dyson Parra
 * @since 11
 */
@RestController
@RequestMapping(value="/api/v1", produces=MediaType.APPLICATION_JSON_VALUE)
@Api(value="EntidadGenerica API")
public class RecursoEntidadGenerica {

    private final Logger log = LoggerFactory.getLogger(RecursoEntidadGenerica.class);
    private final ServicioEntidadGenerica servicioEntidad;
    private final EnsambladorRecursoEntidadGenerica ensambladorRecursoEntidad;

    /**
     * TODO: Description of {@code RecursoEntidadGenerica}.
     *
     * @param servicioEntidad
     * @param ensambladorRecursoEntidad
     */
    public RecursoEntidadGenerica(ServicioEntidadGenerica servicioEntidad,
                              EnsambladorRecursoEntidadGenerica ensambladorRecursoEntidad) {
        this.servicioEntidad = servicioEntidad;
        this.ensambladorRecursoEntidad = ensambladorRecursoEntidad;
    }

    /**
     * TODO: Description of {@code obtenerEntidades}.
     *
     * @param pageable
     * @return
     */
    @ApiOperation(value = "Lista las [Entidades] existentes paginadas", response = Iterable.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista recuperada exitosamente"),
        @ApiResponse(code = 401, message = "No autorizado para ver el recurso."),
        @ApiResponse(code = 403, message = "Prohibido acceder al recurso que intenta alcanzar"),
        @ApiResponse(code = 404, message = "No se encuentra el recurso que intentabas alcanzar")
    })
    @GetMapping("/EntidadGenerica/paginas")
    public ResponseEntity<List<EntidadGenericaDTO>> obtenerEntidades(Pageable pageable) {
        log.debug("REST request to get a page of EntidadGenerica");
        Page<EntidadGenericaDTO> page = null;
        try {
            page = servicioEntidad.obtenerEntidades(pageable);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/EntidadGenerica/paginas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * TODO: Description of {@code obtenerEntidades}.
     *
     * @param expresion
     * @param pageable
     * @return
     */
    @ApiOperation(value = "Lista las [Entidades] existentes paginadas", response = Iterable.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista recuperada exitosamente"),
        @ApiResponse(code = 401, message = "No autorizado para ver el recurso."),
        @ApiResponse(code = 403, message = "Prohibido acceder al recurso que intenta alcanzar"),
        @ApiResponse(code = 404, message = "No se encuentra el recurso que intentabas alcanzar")
    })
    @GetMapping("/EntidadGenerica/{expresion}/paginas")
    public ResponseEntity<List<EntidadGenericaDTO>> obtenerEntidades(@PathVariable String expresion, Pageable pageable) {
        log.debug("REST request to get a page of EntidadGenerica");
        Page<EntidadGenericaDTO> page = null;
        try {
            page = servicioEntidad.query(expresion, pageable);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/EntidadGenerica/{expresion}/paginas/" + expresion);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * TODO: Description of {@code obtenerEntidades}.
     *
     * @return
     */
    @ApiOperation(value = "Lista las [Entidades] existentes", response = Iterable.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista recuperada exitosamente"),
        @ApiResponse(code = 401, message = "No autorizado para ver el recurso."),
        @ApiResponse(code = 403, message = "Prohibido acceder al recurso que intenta alcanzar"),
        @ApiResponse(code = 404, message = "No se encuentra el recurso que intentabas alcanzar")
    })
    @GetMapping("/EntidadGenerica")
    public CollectionModel<EntityModel<EntidadGenericaDTO>> obtenerEntidades() {

        List<EntityModel<EntidadGenericaDTO>> entidades = null;

        try {
            entidades = this.servicioEntidad.obtenerEntidades().parallelStream()
                .map(ensambladorRecursoEntidad::toModel)
                .collect(Collectors.toList());
            CollectionModel<EntityModel<EntidadGenericaDTO>> recursoRetorno = new CollectionModel<EntityModel<EntidadGenericaDTO>>(entidades);
            recursoRetorno.add(linkTo(methodOn(RecursoEntidadGenerica.class).obtenerEntidades()).withSelfRel());
            return recursoRetorno;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    /**
     * TODO: Description of {@code obtenerEntidades}.
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Lista la [Entidad] solicitada", response = Iterable.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista recuperada exitosamente"),
        @ApiResponse(code = 401, message = "No autorizado para ver el recurso."),
        @ApiResponse(code = 403, message = "Prohibido acceder al recurso que intenta alcanzar"),
        @ApiResponse(code = 404, message = "No se encuentra el recurso que intentabas alcanzar")
    })
    @GetMapping("/EntidadGenerica/requisito/{id}")
    public CollectionModel<EntityModel<EntidadGenericaDTO>> obtenerEntidades(@PathVariable String id) {

        List<EntityModel<EntidadGenericaDTO>> entidades = null;

        try {
            entidades = this.servicioEntidad.obtenerEntidades(id).parallelStream()
                .map(ensambladorRecursoEntidad::toModel)
                .collect(Collectors.toList());
            CollectionModel<EntityModel<EntidadGenericaDTO>> recursoRetorno = new CollectionModel<EntityModel<EntidadGenericaDTO>>(entidades);
            recursoRetorno.add(linkTo(methodOn(RecursoEntidadGenerica.class).obtenerEntidades()).withSelfRel());
            return recursoRetorno;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    /**
     * TODO: Description of {@code buscarEntidad}.
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Lista la [Entidad] solicitada", response = Iterable.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista recuperada exitosamente"),
        @ApiResponse(code = 401, message = "No autorizado para ver el recurso."),
        @ApiResponse(code = 403, message = "Prohibido acceder al recurso que intenta alcanzar"),
        @ApiResponse(code = 404, message = "No se encuentra el recurso que intentabas alcanzar")
    })
    @GetMapping("/EntidadGenerica/{id}")
    public ResponseEntity<EntityModel<EntidadGenericaDTO>> buscarEntidad(@PathVariable String id) {

        log.debug(String.format("servicio-entidadGenerica buscarEntidad() invocado:{} por {} ",
            servicioEntidad.getClass().getName(), id));

        try {
            return Optional.of(this.servicioEntidad.buscarEntidad(id))
                .map(u -> new ResponseEntity<>(ensambladorRecursoEntidad.toModel(u), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            log.error("Ocurrio un error en la llamada REST buscarEntidad", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * TODO: Description of {@code guardarEntidad}.
     *
     * @param entidadDTO
     * @return
     */
    @ApiOperation(value = "Guarda la [Entidad] solicitada", response = Iterable.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista recuperada exitosamente"),
        @ApiResponse(code = 401, message = "No autorizado para ver el recurso."),
        @ApiResponse(code = 403, message = "Prohibido acceder al recurso que intenta alcanzar"),
        @ApiResponse(code = 404, message = "No se encuentra el recurso que intentabas alcanzar")
    })
    @PostMapping("/EntidadGenerica")
    public ResponseEntity<?> guardarEntidad(@RequestBody EntidadGenericaDTO entidadDTO) {

        EntityModel<EntidadGenericaDTO> resource = null;

        try {
            resource = ensambladorRecursoEntidad.toModel(servicioEntidad.guardarActualizar(entidadDTO));

            return ResponseEntity
                .created(new URI(resource.getLink("self").orElse(new Link("self")).getHref()))
                .body(resource);
        } catch (Exception e) {
            log.warn("Ocurrio un error en la llamada REST guardarEntidad", e);
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * TODO: Description of {@code actualizarEntidad}.
     *
     * @param entidadDTO
     * @param id
     * @return
     */
    @ApiOperation(value = "Actualiza la [Entidad] solicitada", response = Iterable.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista recuperada exitosamente"),
        @ApiResponse(code = 401, message = "No autorizado para ver el recurso."),
        @ApiResponse(code = 403, message = "Prohibido acceder al recurso que intenta alcanzar"),
        @ApiResponse(code = 404, message = "No se encuentra el recurso que intentabas alcanzar")
    })
    @PutMapping("/EntidadGenerica/{id}")
    public ResponseEntity<?> actualizarEntidad(@RequestBody EntidadGenericaDTO entidadDTO, @PathVariable String id) {
        return guardarEntidad(entidadDTO);
    }

    /**
     * TODO: Description of {@code eliminarEntidad}.
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Elimina la [Entidad] solicitada", response = Iterable.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista recuperada exitosamente"),
        @ApiResponse(code = 401, message = "No autorizado para ver el recurso."),
        @ApiResponse(code = 403, message = "Prohibido acceder al recurso que intenta alcanzar"),
        @ApiResponse(code = 404, message = "No se encuentra el recurso que intentabas alcanzar")
    })
    @DeleteMapping("/EntidadGenerica/{id}")
    public ResponseEntity<?> eliminarEntidad(@PathVariable String id) {
        log.debug("Solicitud REST para Eliminar la entidad : {}", id);
        try {
            servicioEntidad.eliminarEntidad(id);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return ResponseEntity.noContent().build();
    }
}
