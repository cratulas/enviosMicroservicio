package com.example.envios.controller;

import com.example.envios.model.Envio;
import com.example.envios.service.EnvioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    private final EnvioService envioService;

    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    @GetMapping
    public List<Envio> obtenerTodosLosEnvios() {
        return envioService.obtenerTodosLosEnvios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Envio> obtenerEnvioPorId(@PathVariable Long id) {
        Optional<Envio> envio = envioService.obtenerEnvioPorId(id);
        return envio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/ubicacion")
    public ResponseEntity<String> actualizarUbicacion(
            @PathVariable Long id,
            @RequestBody Map<String, String> requestBody) {
        String nuevaUbicacion = requestBody.get("nuevaUbicacion");
        if (nuevaUbicacion == null || nuevaUbicacion.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ubicación no proporcionada.");
        }
        
        boolean actualizado = envioService.actualizarUbicacionEnvio(id, nuevaUbicacion);
        if (actualizado) {
            return ResponseEntity.ok("Ubicación actualizada con éxito.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
