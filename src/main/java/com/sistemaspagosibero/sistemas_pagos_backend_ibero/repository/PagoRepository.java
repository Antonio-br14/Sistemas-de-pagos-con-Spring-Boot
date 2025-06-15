package com.sistemaspagosibero.sistemas_pagos_backend_ibero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemaspagosibero.sistemas_pagos_backend_ibero.entities.Pago;
import com.sistemaspagosibero.sistemas_pagos_backend_ibero.enums.PagoStatus;
import com.sistemaspagosibero.sistemas_pagos_backend_ibero.enums.TypePago;



@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    //lista de pagos asociados a un estudiante
    List<Pago> findByEstudiante_Codigo(String codigo);

    //lista para buscar los pagos por su estado
    List<Pago> findByStatus(PagoStatus status);

    //lisra para buscar los pagos por su tipo seleccionado
    List<Pago> findByType(TypePago type);
}