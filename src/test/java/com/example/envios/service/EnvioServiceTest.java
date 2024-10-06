package com.example.envios.service;

import com.example.envios.model.Envio;
import com.example.envios.repository.EnvioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EnvioServiceTest {

    @Mock
    private EnvioRepository envioRepository;

    @InjectMocks
    private EnvioService envioService;

    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void obtenerEnvioPorId_DeberiaRetornarEnvio() {
        // Arrange
        Envio envio = new Envio(1L, "Comida para gatos", "Bodega Central");
        when(envioRepository.findById(1L)).thenReturn(Optional.of(envio));

        // Act
        Optional<Envio> result = envioService.obtenerEnvioPorId(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Comida para gatos", result.get().getDescripcionProducto());
    }

    @Test
    public void guardarEnvio_DeberiaGuardarYRetornarEnvio() {
        // Arrange
        Envio envio = new Envio(1L, "Comida para gatos", "Bodega Central");
        when(envioRepository.save(envio)).thenReturn(envio);

        // Act
        Envio resultado = envioService.guardarEnvio(envio);

        // Assert
        assertNotNull(resultado);
        assertEquals("Comida para gatos", resultado.getDescripcionProducto());
    }

    @Test
    public void eliminarEnvio_DeberiaEliminarEnvioCorrectamente() {
        // Arrange
        Long envioId = 1L;
        doNothing().when(envioRepository).deleteById(envioId);

        // Act
        envioService.eliminarEnvio(envioId);

        // Assert
        verify(envioRepository, times(1)).deleteById(envioId);
    }

}
