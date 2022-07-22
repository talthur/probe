package com.talthur.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

        //        Planet planet = new Planet(5, 7);
        //        planet.placeProbe(3, 3, new Probe(OrientationEnum.NORTH, "Xuxa", 3, 3));
        //        Probe probe = planet.getProbes().get("Xuxa");
        //        probe.rotate('L');
        //        probe.rotate('L');
        //        System.out.println(planet.moveProbe(probe));
        //        System.out.println(planet.moveProbe(probe));
        //        System.out.println(planet.moveProbe(probe));
        //        planet.placeProbe(3,2, new Probe(OrientationEnum.SOUTH, "Angelica", 3, 2));
        //        System.out.println("teste");
    }

}
