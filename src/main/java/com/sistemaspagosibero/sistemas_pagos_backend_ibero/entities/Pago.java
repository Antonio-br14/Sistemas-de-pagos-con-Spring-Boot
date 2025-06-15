package com.sistemaspagosibero.sistemas_pagos_backend_ibero.entities;

import java.time.LocalDate;

import com.sistemaspagosibero.sistemas_pagos_backend_ibero.enums.PagoStatus;
import com.sistemaspagosibero.sistemas_pagos_backend_ibero.enums.TypePago;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor // <- importante para JPA
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private double cantidad;
    private TypePago type;
    private PagoStatus status;
    private String file; // podrías cambiar a filePath o fileUrl

    @ManyToOne
    @JoinColumn(name = "estudiante_id") // <- recomendable
    private Estudiante estudiante;
}
