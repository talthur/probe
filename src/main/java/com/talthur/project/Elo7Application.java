package com.talthur.project;

import com.talthur.project.core.enums.OrientationEnum;
import com.talthur.project.core.domain.Planet;
import com.talthur.project.core.domain.Probe;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Elo7Application {

	public static void main(String[] args) {

		SpringApplication.run(Elo7Application.class, args);

		Planet planet = new Planet(5,7);
		planet.placeProbe(3,3, new Probe(OrientationEnum.NORTH, "Xuxa", 3, 3));
		Probe probe = planet.getProbes().get("Xuxa");
		probe.rotate('L');
		planet.moveProbe(probe);

	}

}
