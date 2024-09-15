package com.example.envios.controller;

import com.example.envios.model.Envio;
import com.example.envios.service.EnvioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    private static final Logger logger = LoggerFactory.getLogger(EnvioController.class);

    private final EnvioService envioService;

    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    @GetMapping
    public List<Envio> obtenerTodosLosEnvios() {
        logger.info("Obteniendo todos los envíos");
        return envioService.obtenerTodosLosEnvios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Envio> obtenerEnvioPorId(@PathVariable Long id) {
        logger.info("Buscando envío con ID: {}", id);
        Optional<Envio> envio = envioService.obtenerEnvioPorId(id);
        if (envio.isPresent()) {
            logger.info("Envío encontrado: {}", envio.get());
            return ResponseEntity.ok(envio.get());
        } else {
            logger.warn("Envío con ID {} no encontrado", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Envio> crearEnvio(@RequestBody Envio envio) {
        logger.info("Creando nuevo envío: {}", envio);
        Envio nuevoEnvio = envioService.guardarEnvio(envio);
        logger.info("Envío creado con éxito: {}", nuevoEnvio);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEnvio);
    }

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
}
