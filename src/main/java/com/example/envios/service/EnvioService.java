package com.example.envios.service;

import com.example.envios.model.Envio;
import com.example.envios.repository.EnvioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnvioService {

    private final EnvioRepository envioRepository;

    public EnvioService(EnvioRepository envioRepository) {
        this.envioRepository = envioRepository;
    }

    public List<Envio> obtenerTodosLosEnvios() {
        return envioRepository.findAll();
    }

    public Optional<Envio> obtenerEnvioPorId(Long id) {
        return envioRepository.findById(id);
    }

    public Envio guardarEnvio(Envio envio) {
        return envioRepository.save(envio);
    }

    public boolean actualizarUbicacionEnvio(Long id, String nuevaUbicacion) {
        Optional<Envio> envio = envioRepository.findById(id);
        if (envio.isPresent()) {
            Envio envioActualizado = envio.get();
            envioActualizado.setUbicacionActual(nuevaUbicacion);
            envioRepository.save(envioActualizado);  // Guardar los cambios en la BD
            return true;
        }
        return false;
    }

    public void eliminarEnvio(Long id) {
        envioRepository.deleteById(id);
    }
}
