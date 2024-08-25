package com.example.envios.repository;

import com.example.envios.model.Envio;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EnvioRepository {

    private final List<Envio> envios = new ArrayList<>();

    public EnvioRepository() {
        // Inicializar con algunos datos de ejemplo
        envios.add(new Envio(1L, "Comida para perros", "Bodega Central"));
        envios.add(new Envio(2L, "Juguete para gatos", "En camino"));
        envios.add(new Envio(3L, "Arena para gatos", "Sucursal de destino"));
    }

    public List<Envio> findAll() {
        return new ArrayList<>(envios);
    }

    public Optional<Envio> findById(Long id) {
        return envios.stream().filter(envio -> envio.getId().equals(id)).findFirst();
    }

    public void save(Envio envio) {
        envios.add(envio);
    }

    public void delete(Envio envio) {
        envios.remove(envio);
    }

    public boolean actualizarUbicacion(Long id, String nuevaUbicacion) {
        Optional<Envio> envioOptional = findById(id);
        if (envioOptional.isPresent()) {
            envioOptional.get().setUbicacionActual(nuevaUbicacion);
            return true;
        }
        return false;
    }
    
}
