package com.example.envios.controller;

import com.example.envios.model.Envio;
import com.example.envios.service.EnvioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    private static final Logger logger = LoggerFactory.getLogger(EnvioController.class);

    private final EnvioService envioService;

    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    // GET: Obtener todos los envíos con HATEOAS
    @GetMapping
    public List<EntityModel<Envio>> obtenerTodosLosEnvios() {
        logger.info("Obteniendo todos los envíos");

        return envioService.obtenerTodosLosEnvios().stream()
                .map(this::convertirAEntityModel)
                .collect(Collectors.toList());
    }

    // GET: Obtener envío por ID con HATEOAS
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Envio>> obtenerEnvioPorId(@PathVariable Long id) {
        logger.info("Buscando envío con ID: {}", id);

        Optional<Envio> envio = envioService.obtenerEnvioPorId(id);
        if (envio.isPresent()) {
            logger.info("Envío encontrado: {}", envio.get());
            return ResponseEntity.ok(convertirAEntityModel(envio.get()));
        } else {
            logger.warn("Envío con ID {} no encontrado", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // POST: Crear nuevo envío
    @PostMapping
    public ResponseEntity<EntityModel<Envio>> crearEnvio(@RequestBody Envio envio) {
        logger.info("Creando nuevo envío: {}", envio);
        Envio nuevoEnvio = envioService.guardarEnvio(envio);
        logger.info("Envío creado con éxito: {}", nuevoEnvio);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertirAEntityModel(nuevoEnvio));
    }

    // PUT: Actualizar ubicación
    @PutMapping("/{id}/ubicacion")
    public ResponseEntity<String> actualizarUbicacion(
            @PathVariable Long id,
            @RequestBody Map<String, String> requestBody) {
        logger.info("Actualizando ubicación del envío con ID: {}", id);
        String nuevaUbicacion = requestBody.get("nuevaUbicacion");
        if (nuevaUbicacion == null || nuevaUbicacion.isEmpty()) {
            logger.warn("Ubicación no proporcionada para el envío con ID: {}", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ubicación no proporcionada.");
        }

        boolean actualizado = envioService.actualizarUbicacionEnvio(id, nuevaUbicacion);
        if (actualizado) {
            logger.info("Ubicación del envío con ID: {} actualizada a {}", id, nuevaUbicacion);
            return ResponseEntity.ok("Ubicación actualizada con éxito.");
        } else {
            logger.warn("Envío con ID {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Eliminar envío
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEnvio(@PathVariable Long id) {
        logger.info("Eliminando envío con ID: {}", id);
        Optional<Envio> envio = envioService.obtenerEnvioPorId(id);
        if (envio.isPresent()) {
            envioService.eliminarEnvio(id);
            logger.info("Envío con ID {} eliminado con éxito", id);
            return ResponseEntity.ok("Envío eliminado con éxito.");
        } else {
            logger.warn("Envío con ID {} no encontrado", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Envío no encontrado.");
        }
    }

    // Método para convertir un Envio a EntityModel (HATEOAS)
    private EntityModel<Envio> convertirAEntityModel(Envio envio) {
        EntityModel<Envio> envioModel = EntityModel.of(envio);

        // Enlace a sí mismo (el recurso actual)
        Link selfLink = linkTo(methodOn(EnvioController.class).obtenerEnvioPorId(envio.getId())).withSelfRel();
        envioModel.add(selfLink);

        // Enlace a todos los envíos
        Link allEnviosLink = linkTo(methodOn(EnvioController.class).obtenerTodosLosEnvios()).withRel("envios");
        envioModel.add(allEnviosLink);

        return envioModel;
    }
}
