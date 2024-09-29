package com.example.envios.repository;

import com.example.envios.model.Envio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")  // 
public class EnvioRepositoryTest {

    @Autowired
    private EnvioRepository envioRepository;

    @Test
    public void guardarEnvio_DeberiaGuardarYRetornarEnvio() {
        // Arrange
        Envio envio = new Envio(null, "Comida para perros", "Bodega Norte");

        // Act
        Envio envioGuardado = envioRepository.save(envio);

        // Assert
        assertNotNull(envioGuardado.getId());
        assertEquals("Comida para perros", envioGuardado.getDescripcionProducto());
    }

    @Test
    public void obtenerEnvioPorId_DeberiaRetornarEnvio() {
        // Arrange
        Envio envio = new Envio(null, "Comida para gatos", "Bodega Sur");
        envio = envioRepository.save(envio);

        // Act
        Optional<Envio> resultado = envioRepository.findById(envio.getId());

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Comida para gatos", resultado.get().getDescripcionProducto());
    }
}
