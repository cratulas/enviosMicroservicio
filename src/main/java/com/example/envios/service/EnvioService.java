package com.example.envios.service;

import com.example.envios.model.Envio;
import com.example.envios.repository.EnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnvioService {

    private final EnvioRepository envioRepository;

    @Autowired
    public EnvioService(EnvioRepository envioRepository) {
        this.envioRepository = envioRepository;
    }

    public List<Envio> obtenerTodosLosEnvios() {
        return envioRepository.findAll();
    }

    public Optional<Envio> obtenerEnvioPorId(Long id) {
        return envioRepository.findById(id);
    }

    public boolean actualizarUbicacionEnvio(Long id, String nuevaUbicacion) {
        return envioRepository.actualizarUbicacion(id, nuevaUbicacion);
    }
    
}
