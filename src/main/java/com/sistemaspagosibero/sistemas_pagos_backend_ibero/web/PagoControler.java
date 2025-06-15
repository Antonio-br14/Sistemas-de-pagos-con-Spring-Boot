package com.sistemaspagosibero.sistemas_pagos_backend_ibero.web;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sistemaspagosibero.sistemas_pagos_backend_ibero.entities.Estudiante;
import com.sistemaspagosibero.sistemas_pagos_backend_ibero.entities.Pago;
import com.sistemaspagosibero.sistemas_pagos_backend_ibero.enums.PagoStatus;
import com.sistemaspagosibero.sistemas_pagos_backend_ibero.enums.TypePago;
import com.sistemaspagosibero.sistemas_pagos_backend_ibero.repository.EstudianteRepository;
import com.sistemaspagosibero.sistemas_pagos_backend_ibero.repository.PagoRepository;
import com.sistemaspagosibero.sistemas_pagos_backend_ibero.services.PagoService;

@RestController
@CrossOrigin("*")
public class PagoControler {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PagoService pagoService;

    @GetMapping("/estudiantes")
    public List<Estudiante> listarEstudiantes(){
        return estudianteRepository.findAll();
    }

    // ESTE ES EL MÉTODO QUE DEVUELVE UN SOLO ESTUDIANTE POR CÓDIGO
    @GetMapping("/estudiantes/{codigo}")
    public Estudiante listartEstudiantePorCodigo(@PathVariable String codigo){
        return estudianteRepository.findByCodigo(codigo);
    } 

    @GetMapping("/estudiantesPorprogramas")
    public List<Estudiante> listarEstudiantesPorProgramas(@RequestParam String ProgramaId){
        return estudianteRepository.findByProgramaId(ProgramaId);
    }

    @GetMapping("/pagos")
    public List<Pago> listarPagos(){
        return pagoRepository.findAll();
    }

    @GetMapping("/pago/{id}")
    public Pago listarPorPagoId (@PathVariable Long id){
        return pagoRepository.findById(id).get();
    }

    // ESTE MÉTODO DEVUELVE LOS PAGOS DE UN ESTUDIANTE (NO EL ESTUDIANTE)
    @GetMapping("/estudiantes/{codigo}/pagos")
    public List<Pago> listarPagosPorCodigoEstudiante(@PathVariable String codigo) {
        return pagoRepository.findByEstudiante_Codigo(codigo);
    }

    // SI QUIERES UN MÉTODO QUE DEVUELVA EL ESTUDIANTE CON SUS PAGOS:
    @GetMapping("/estudiante-con-pagos/{codigo}")
    public EstudianteConPagos obtenerEstudianteConPagos(@PathVariable String codigo) {
        Estudiante estudiante = estudianteRepository.findByCodigo(codigo);
        List<Pago> pagos = pagoRepository.findByEstudiante_Codigo(codigo);
        
        return new EstudianteConPagos(estudiante, pagos);
    }

    @GetMapping("/pagosPorStatus")
    public List<Pago> listarPagosPorcodigosPorStatus(@RequestParam PagoStatus Status){
        return pagoRepository.findByStatus(Status);
    }
    
    @GetMapping("/pagos/por/tipos")
    public List<Pago> listarPagosPortipos(@RequestParam TypePago type){
        return pagoRepository.findByType(type);
    }

    @PutMapping("pagos/{pagoId}/actualizarPago")
    public Pago actualizaStatusPago(@RequestParam PagoStatus status, @PathVariable("pagoId") Long pagoId){
    return pagoService.actualizarpagoPorStatus(status, pagoId);
    }


    @PostMapping(path = "/pagos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Pago guardarPago(@RequestParam("file") MultipartFile file, double cantidad,TypePago type, LocalDate date, String codigoEstudiante) throws IOException{
        return pagoService.savePago(file, cantidad, type, date, codigoEstudiante);
    }

    @GetMapping(value = "/pagoFile/{pagoid}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte [] ListarArchivosPorId(@PathVariable Long pagoid) throws IOException{
        return pagoService.getArchivoPorId(pagoid);
    }

    // CLASE INTERNA PARA DEVOLVER ESTUDIANTE CON SUS PAGOS
    public static class EstudianteConPagos {
        private Estudiante estudiante;
        private List<Pago> pagos;

        public EstudianteConPagos(Estudiante estudiante, List<Pago> pagos) {
            this.estudiante = estudiante;
            this.pagos = pagos;
        }

        // Getters
        public Estudiante getEstudiante() {
            return estudiante;
        }

        public List<Pago> getPagos() {
            return pagos;
        }

        // Setters
        public void setEstudiante(Estudiante estudiante) {
            this.estudiante = estudiante;
        }

        public void setPagos(List<Pago> pagos) {
            this.pagos = pagos;
        }
    }
}