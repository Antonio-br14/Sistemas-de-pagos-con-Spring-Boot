package com.sistemaspagosibero.sistemas_pagos_backend_ibero.entities;

//import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

@Entity //Indica que esta clase es una entidad JPA
@Builder //Para construir objetos
@Data //Getters and setters
@NoArgsConstructor //Genera un constructor sin argumentos
@AllArgsConstructor //Genera un constructor con todos los argumentos
public class Estudiante {

        
    @Id//llave primaria en la base de datos //Identificador del estudiante
    private String id;
    private String nombre;
    private String apellido;

    @Column(unique = true) //Este campo debe ser úni en la base de datos    
    private String codigo;

    private String programaId;
    private String foto;

}