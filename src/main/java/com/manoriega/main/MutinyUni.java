package com.manoriega.main;


import com.manoriega.main.model.Auto;
import com.manoriega.main.model.Persona;
import io.smallrye.mutiny.Uni;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

@Log
public class MutinyUni {

    static Uni<String> invokeRemoteGreetingService(String name){
        return Uni
                .createFrom()
                .item(name)
                .onItem()
                .transform(s -> s.toUpperCase());
    }


    public static void main(String[] args) {
        log.info("Inicio");


        Uni<Void> voidUni = Uni.createFrom().nullItem();

        Uni.createFrom().item("Manuel Alejandro Noriega Zabala").onItem().transformToUni(s -> invokeRemoteGreetingService(s)).subscribe().with(s -> System.out.println(s));

        Uni<String> uniString1 = Uni.createFrom()
                .item(1012)
                .onItem().transform(i -> "hello-" + i)
                .onItem().delayIt().by(Duration.ofMillis(5000))
                .onItem().invoke(() -> System.out.println("Invoke"))
                .onFailure().invoke(fail -> System.out.println("fail " + fail))
                .onCancellation().invoke(() -> System.out.println("Cancelled"));

        Uni<String> uniString2 = Uni.createFrom()
                .item("ESTO SE VA EJECUTAR DE PRIMERO");


        Uni<Persona> uniPersona = Uni.createFrom()
                .item(Persona.builder()
                        .nombre("manuel")
                        .apellido("noriega")
                        .edad(33)
                        .build())
                .onItem().transform(
                        persona -> {
                            persona.setNombre("Alejandro");
                            return persona;
                        }
                );

        Uni<Auto> uniAuto = Uni.createFrom()
                .item(Auto.builder()
                        .marca("chevy")
                        .modelo("aveo")
                        .color("azul")
                        .ano(2010)
                        .build())
                .map(auto -> {
                    log.info("cambiando el color: " + auto.getColor());
                    auto.setColor("rojo".toUpperCase());
                    log.info("color cambiando a: " + auto.getColor());
                    return auto;
                });

        uniString1.subscribe()
                .with(
                        response -> {
                            log.info(response);
                        },
                        failure -> {
                            log.info("error " + failure);
                        }
                );

        uniString2.subscribe()
                .with(
                        response -> {
                            log.info(response);
                        },
                        failure -> {
                            log.info("error " + failure);
                        }
                );

        uniPersona.onItem().transform(persona -> {
            persona.setEdad(30);
            return persona;
        }).onItem().delayIt().by(Duration.ofMillis(10000))
                .subscribe()
                .with(persona -> {
                    log.info(persona.toString());
                });

        uniAuto.onItem().transform(autoResponse -> {
            log.info("llevo todo a mayusculas");
            return Auto.builder()
                    .marca(autoResponse.getMarca().toUpperCase())
                    .modelo(autoResponse.getModelo().toUpperCase())
                    .color(autoResponse.getColor().toUpperCase())
                    .ano(autoResponse.getAno())
                    .build();
        }).onItem().transform(autoResponse -> {
            if ("ROJO".equals(autoResponse.getColor())) {
                log.info("color no valido");
                autoResponse.setColor("Blanco".toUpperCase());
            }
            return autoResponse;
        }).subscribe()
                .with(autoResponse -> {
                    log.info(autoResponse.toString());
                });

    }
}