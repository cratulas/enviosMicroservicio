package com.example.envios.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Envio {
    private Long id;
    private String descripcionProducto;
    private String ubicacionActual;
}
