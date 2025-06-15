package com.sistemaspagosibero.sistemas_pagos_backend_ibero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemaspagosibero.sistemas_pagos_backend_ibero.entities.Estudiante;
import java.util.List;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, String> {

    //metodo personalizado que busque un estudiante por su  codigo unico
    Estudiante findByCodigo(String codigo);

    //metodo personalizado que me muestre  una lista de estudiantes que pertenecen aun programa en especifico

    List<Estudiante> findByProgramaId(String programaId);


}
