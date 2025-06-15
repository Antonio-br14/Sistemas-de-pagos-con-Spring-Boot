package com.sistemaspagosibero.sistemas_pagos_backend_ibero.dtos;

import java.time.LocalDate;

import com.sistemaspagosibero.sistemas_pagos_backend_ibero.enums.TypePago;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class NewPago {

    private double cantidad;
    private TypePago typago;
    private LocalDate date;
    private String codigoEstudiante;
}
