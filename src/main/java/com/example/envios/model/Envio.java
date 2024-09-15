package com.example.envios.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La descripción del producto no puede estar vacía.")
    @Size(max = 100, message = "La descripción del producto no debe exceder los 100 caracteres.")
    private String descripcionProducto;

    @NotBlank(message = "La ubicación actual no puede estar vacía.")
    private String ubicacionActual;
}
