package com.talthur.project.core.usecase;

import com.talthur.project.core.domain.Coordinates;
import com.talthur.project.core.domain.Planet;
import com.talthur.project.core.domain.Probe;
import com.talthur.project.core.enums.Direction;
import com.talthur.project.dataprovider.PlanetInMemory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IntegrationUseCaseTest {


    final String planetName = "planetName";
    final Coordinates probeCoordinates = new Coordinates(1, 1);
    final Coordinates planetCoordinates = new Coordinates(3, 3);
    final Coordinates expectedCoordinates = new Coordinates(2, 3);
    final String probeName = "cat";

    @Test
    @DisplayName("Deve criar um planeta com sucesso")
    void useCaseIntegrationTest() {
        PlanetInMemory planetInMemory = new PlanetInMemory();
        PlanetUseCase planetUseCase = new PlanetUseCase(planetInMemory);
        ProbeUseCase probeUseCase = new ProbeUseCase(planetInMemory);

        Planet planet = planetUseCase.createPlanet(planetName, planetCoordinates);
        Probe probe = probeUseCase.createProbeOnPlanet(planetName, probeCoordinates, Direction.NORTH, probeName);
        Probe probeOut = probeUseCase.moveProbe(planetName, probeName, "MRMLM");

        Assertions.assertEquals(expectedCoordinates, probeOut.getCoordinates());
    }

    

}

