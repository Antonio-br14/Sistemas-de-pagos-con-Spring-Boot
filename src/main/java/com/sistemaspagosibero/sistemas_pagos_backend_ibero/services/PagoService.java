package com.sistemaspagosibero.sistemas_pagos_backend_ibero.services;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.security.Key;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sistemaspagosibero.sistemas_pagos_backend_ibero.entities.Estudiante;
import com.sistemaspagosibero.sistemas_pagos_backend_ibero.entities.Pago;
import com.sistemaspagosibero.sistemas_pagos_backend_ibero.enums.PagoStatus;
import com.sistemaspagosibero.sistemas_pagos_backend_ibero.enums.TypePago;
import com.sistemaspagosibero.sistemas_pagos_backend_ibero.repository.EstudianteRepository;
import com.sistemaspagosibero.sistemas_pagos_backend_ibero.repository.PagoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional //para asegurar que los metoos esta clase se ejecuten dentro de una transaccion
public class PagoService {

    // inyeccion de dependecias de pagorepository para interactuar con la base de datos de pagos
    @Autowired
    private PagoRepository pagoRepository; 

    //inyeccion de dependencias de estudiante repositorio para obtener informacion de los estudiantes desde la base de datos
    @Autowired 
    private EstudianteRepository estudianteRepository;

    /*
     * metodo para guardar el pago en la base de datos y almancer un archivo pdf en el servidor asociado
     * 
     * @param file archivo pdf que se sube al servidor
     * @param cantidad monto del pago realizado
     * @param type tipos de pagos
     * @param date fecha en que se realiza el pago
     * @param codigoEstudiante que realiza el pago
     * @return objeto de pago guardado en la base de datos
     * @thows IDExepcion lanzar una exepcion si ocurre un error
     */


     public Pago savePago(MultipartFile file, double canitdad, TypePago type, LocalDate date, String codigoEstudiante) throws IOException{

        /*
         * contruir la ruta donde se guardara el archivo dentro del sistema 
         * System.getProperty("user.home"): obtiene la ruta del directorio personal del usuario del actual sistema del sistema
         * path.get: construir una ruta dentro del directorio personal en la carpeta "enset-data/pagos"
         */

         Path folderPath = Paths.get(System.getProperty("user.home"),"enset-data", "pagos");

         //verificar si la carpeta ya existe, si no la debe verificar
         if(!Files.exists(folderPath)){
            Files.createDirectories(folderPath);
         }

         //generamos un nombre unico para el archivo usando UUID(indentificacor unico universal)

         String filename = UUID.randomUUID().toString();

         //construimos la ruta completa del archivo agregando la extension .pdf

         Path filePath = Paths.get(System.getProperty("user.home"),"enset-data", "pagos", filename + ".pdf");

         //guardamos el archivo de recibido en la ubicacion especificada dentro del ssitema de archivos 

         Files.copy(file.getInputStream(), filePath);

         Estudiante estudiante = estudianteRepository.findByCodigo(codigoEstudiante);

         //creamos un nuevo objeto pago utilizando el patron de diseño builder

         Pago pago = Pago.builder()
         .type(type)
         .status(PagoStatus.CREADO)
         .fecha(date)
         .estudiante(estudiante)
         .cantidad(canitdad)
         .file(filePath.toUri().toString()) //ruta del archivo pdf almacenado
         .build(); // construccion final del objeto Pago

         return pagoRepository.save(pago);
     }

     public byte[]getArchivoPorId(Long pagoId) throws IOException{

      //busca un objeto pago en la bd por su id
      Pago pago = pagoRepository.findById(pagoId).get();
      
      /*
       * pago.getfile: obtiene la Url del archivo guardado como una cadena de texto
       * URI.create convierte la cadena de texto en un objeto URI
       * pathof convierte la URI en un path para poder acceder al archivo
       * file.readAllBytes va a leer el contenido del archivo y lo va a devolver en un array que es un vector de bytes
       * esto permite obtener el contenido del archivo para su posterior uso por ejemplo descargar 
       */
      return Files.readAllBytes(Path.of(URI.create(pago.getFile()))); 
     }

     public Pago actualizarpagoPorStatus(PagoStatus status, Long id){

      //busca un objeto pago en la bd por su id
      Pago pago = pagoRepository.findById(id).get();
      //actualiza el estado del pago que puede ser validado o rechazado
      pago.setStatus(status);
      //guarda el objeto pago acualizado en la base de datos y lo devuelve

      //busca un objeto pago en la bd por id
      return pagoRepository.save(pago);
     }

}
