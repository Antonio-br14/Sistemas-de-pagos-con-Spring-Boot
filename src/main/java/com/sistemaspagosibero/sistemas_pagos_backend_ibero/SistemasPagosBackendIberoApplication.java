package com.sistemaspagosibero.sistemas_pagos_backend_ibero;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sistemaspagosibero.sistemas_pagos_backend_ibero.entities.Estudiante;
import com.sistemaspagosibero.sistemas_pagos_backend_ibero.entities.Pago;
import com.sistemaspagosibero.sistemas_pagos_backend_ibero.enums.PagoStatus;
import com.sistemaspagosibero.sistemas_pagos_backend_ibero.enums.TypePago;
import com.sistemaspagosibero.sistemas_pagos_backend_ibero.repository.EstudianteRepository;
import com.sistemaspagosibero.sistemas_pagos_backend_ibero.repository.PagoRepository;

@SpringBootApplication
public class SistemasPagosBackendIberoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemasPagosBackendIberoApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner (EstudianteRepository estudianteRepository, PagoRepository pagoRepository){
		return args ->{
			//guarda un estudiante en la bas de datos al iniciar la aplicacion
			estudianteRepository.save(Estudiante.builder().id(UUID.randomUUID().toString()).nombre("Melissa").apellido("Gordillo").codigo("1234").programaId("IS123").build());

			estudianteRepository.save(Estudiante.builder().id(UUID.randomUUID().toString()).nombre("Antonio").apellido("Barrios").codigo("54321").programaId("ID123").build());

			estudianteRepository.save(Estudiante.builder().id(UUID.randomUUID().toString()).nombre("Javier").apellido("Diaz").codigo("2678").programaId("LC123").build());

			estudianteRepository.save(Estudiante.builder().id(UUID.randomUUID().toString()).nombre("Adriana").apellido("Uribie").codigo("4532").programaId("LC123").build());

			estudianteRepository.save(Estudiante.builder().id(UUID.randomUUID().toString()).nombre("Maria").apellido("Avila").codigo("0987").programaId("LC123").build());

			//obtiene todos los valores posibles enumerados type pago

			TypePago tiPosPagos[] = TypePago.values();
			//crea un objeto random para seleccionar valores aleatorios
			Random random = new Random();

			//itero sobre todos los estudiantes 
			estudianteRepository.findAll().forEach(estudiante -> {
				//crear 10 pagos para cada estudiantes
				for(int i =0;i<10;i++){
					int index = random.nextInt(tiPosPagos.length);

					Pago pago = Pago.builder().cantidad(1000 + (int) (Math.random()*20000)).type(tiPosPagos[index]).status(PagoStatus.CREADO).fecha(LocalDate.now()).estudiante(estudiante).build();

					//guardar el pago en la base de datos

					pagoRepository.save(pago);
				}
			});
			
		};
	}

}
