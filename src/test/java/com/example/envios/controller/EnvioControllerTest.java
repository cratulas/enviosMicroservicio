package com.example.envios.controller;

import com.example.envios.model.Envio;
import com.example.envios.service.EnvioService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EnvioController.class)
public class EnvioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnvioService envioService;

    @Test
    public void obtenerEnvioPorId_DeberiaRetornarEnvio() throws Exception {
        // Arrange
        Envio envio = new Envio(1L, "Comida para perros", "Bodega Norte");
        when(envioService.obtenerEnvioPorId(1L)).thenReturn(Optional.of(envio));

        // Act & Assert
        mockMvc.perform(get("/api/envios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcionProducto").value("Comida para perros"))
                .andExpect(jsonPath("$.ubicacionActual").value("Bodega Norte"));
    }

    @Test
    public void obtenerEnvioPorId_DeberiaRetornar404SiNoExiste() throws Exception {
        // Arrange
        when(envioService.obtenerEnvioPorId(1L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/envios/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void crearEnvio_DeberiaCrearEnvioExitosamente() throws Exception {
        // Arrange
        Envio envio = new Envio(1L, "Comida para gatos", "Bodega Central");
        when(envioService.guardarEnvio(any(Envio.class))).thenReturn(envio);

        // Act & Assert
        mockMvc.perform(post("/api/envios")
                .contentType("application/json")
                .content("{\"descripcionProducto\":\"Comida para gatos\", \"ubicacionActual\":\"Bodega Central\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descripcionProducto").value("Comida para gatos"))
                .andExpect(jsonPath("$.ubicacionActual").value("Bodega Central"));
    }
}
