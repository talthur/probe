package com.talthur.project.core.usecase;

import com.talthur.project.core.domain.Planet;
import com.talthur.project.core.domain.Probe;
import com.talthur.project.core.domain.StarShip;
import com.talthur.project.core.enums.OrientationEnum;
import com.talthur.project.core.exception.BusinessException;
import com.talthur.project.core.exception.errors.BusinessError;
import com.talthur.project.dataprovider.PlanetInMemory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class PlanetUseCaseTest {


    final int x = 10;
    final int y = 10;
    final Planet planet = new Planet(x, y);
    final String probeName = "cat";
    final StarShip probe = new Probe(OrientationEnum.NORTH, probeName, 4, 4);
    final StarShip probeWest = new Probe(OrientationEnum.WEST, probeName, 4, 4);
    final StarShip probeEast = new Probe(OrientationEnum.EAST, probeName, 4, 4);
    final StarShip probeSouth = new Probe(OrientationEnum.SOUTH, probeName, 4, 4);


    @InjectMocks PlanetUseCase planetUseCase;
    @Mock PlanetInMemory planetInMemory;

    @Test
    @DisplayName("Deve criar um planeta com sucesso")
    void shouldCreatePlanet() {
        Planet planetReturn = planetUseCase.createPlanet(x, y);
        Assertions.assertDoesNotThrow(() -> planetUseCase.createPlanet(x, y));
        Mockito.verify(planetInMemory, Mockito.atLeastOnce()).save(planetReturn);
    }

    @Test
    @DisplayName("Deve trazer uma lista de planetas")
    void shouldGetPlanets() {
        Mockito.when(planetInMemory.getAll()).thenReturn(List.of(planet));
        Assertions.assertEquals(planetUseCase.getPlanets(), List.of(planet));
        Mockito.verify(planetInMemory, Mockito.atLeastOnce()).getAll();
    }

    @Test
    @DisplayName("Deve posicionar uma StarShip")
    void shouldPlaceStarShip() {
        Mockito.when(planetInMemory.getPlanet(planet.getId())).thenReturn(planet);
        StarShip probeReturned = planetUseCase.placeProbe(planet.getId(), 4, 4, OrientationEnum.NORTH, probeName);
        Assertions.assertEquals(probeReturned.getShipName(), probe.getShipName());
        Assertions.assertEquals(probeReturned.getActualPosition(), probe.getActualPosition());
        Assertions.assertEquals(probeReturned.getOrientation(), probe.getOrientation());
        Mockito.verify(planetInMemory, Mockito.atLeastOnce()).getPlanet(planet.getId());
    }

    @Test
    @DisplayName("Deve lançar uma excessão de posição inválida")
    void shouldReturnExceptionPlaceInvalidStarShip() {
        Mockito.when(planetInMemory.getPlanet(planet.getId())).thenReturn(planet);
        String id = planet.getId();
        BusinessException placeError = Assertions.assertThrows(BusinessException.class,
            () -> planetUseCase.placeProbe(id, 0, 0, OrientationEnum.NORTH, probeName));
        Assertions.assertEquals(BusinessError.COORDINATES_INVALID.getCode(), placeError.getCode());
        Assertions.assertEquals(BusinessError.COORDINATES_INVALID.getStatus(), placeError.getStatus());
        Assertions.assertEquals(BusinessError.COORDINATES_INVALID.getDetails(), placeError.getDetail());
        Mockito.verify(planetInMemory, Mockito.atLeastOnce()).getPlanet(planet.getId());
    }

    @Test
    @DisplayName("Deve lançar uma excessão de lugar já ocupado")
    void shouldReturnExceptionOccupiedStarShip() {
        Mockito.when(planetInMemory.getPlanet(planet.getId())).thenReturn(planet);
        String id = planet.getId();
        planet.placeStarShip(4, 4, probeWest);
        BusinessException placeError = Assertions.assertThrows(BusinessException.class,
            () -> planetUseCase.placeProbe(id, 4, 4, OrientationEnum.NORTH, probeName));
        Assertions.assertEquals(BusinessError.PLACEMENT_NOT_ALLOWED_OCUPPIED.getCode(), placeError.getCode());
        Assertions.assertEquals(BusinessError.PLACEMENT_NOT_ALLOWED_OCUPPIED.getStatus(), placeError.getStatus());
        Assertions.assertEquals(BusinessError.PLACEMENT_NOT_ALLOWED_OCUPPIED.getDetails(), placeError.getDetail());
        Mockito.verify(planetInMemory, Mockito.atLeastOnce()).getPlanet(planet.getId());
    }

    @Test
    @DisplayName("Deve movimentar uma StarShip para frente")
    void shouldMoveStarShipNorth() {
        Mockito.when(planetInMemory.getPlanet(planet.getId())).thenReturn(planet);
        planet.placeStarShip(4, 4, probe);
        StarShip probeReturn = planetUseCase.moveStarShip(planet.getId(), probeName, "M");
        Assertions.assertEquals(probeReturn.getActualPosition(), List.of(4, 3));
        Mockito.verify(planetInMemory, Mockito.atLeastOnce()).getPlanet(planet.getId());
    }

    @Test
    @DisplayName("Deve movimentar uma StarShip para esquerda")
    void shouldMoveStarWest() {
        Mockito.when(planetInMemory.getPlanet(planet.getId())).thenReturn(planet);
        planet.placeStarShip(4, 4, probeWest);
        StarShip probeReturn = planetUseCase.moveStarShip(planet.getId(), probeName, "M");
        Assertions.assertEquals(probeReturn.getActualPosition(), List.of(3, 4));
        Mockito.verify(planetInMemory, Mockito.atLeastOnce()).getPlanet(planet.getId());
    }

    @Test
    @DisplayName("Deve movimentar uma StarShip para Direita")
    void shouldMoveStarEast() {
        Mockito.when(planetInMemory.getPlanet(planet.getId())).thenReturn(planet);
        planet.placeStarShip(4, 4, probeEast);
        StarShip probeReturn = planetUseCase.moveStarShip(planet.getId(), probeName, "M");
        Assertions.assertEquals(probeReturn.getActualPosition(), List.of(5, 4));
        Mockito.verify(planetInMemory, Mockito.atLeastOnce()).getPlanet(planet.getId());
    }

    @Test
    @DisplayName("Deve movimentar uma StarShip para Baixo")
    void shouldMoveStarSouth() {
        Mockito.when(planetInMemory.getPlanet(planet.getId())).thenReturn(planet);
        planet.placeStarShip(4, 4, probeSouth);
        StarShip probeReturn = planetUseCase.moveStarShip(planet.getId(), probeName, "M");
        Assertions.assertEquals(probeReturn.getActualPosition(), List.of(4, 5));
        Mockito.verify(planetInMemory, Mockito.atLeastOnce()).getPlanet(planet.getId());
    }


    @Test
    @DisplayName("Deve girar uma StarShip para esquerda")
    void shouldRotateLStarShip() {
        Mockito.when(planetInMemory.getPlanet(planet.getId())).thenReturn(planet);
        planet.placeStarShip(4, 4, probe);
        StarShip probeReturn = planetUseCase.moveStarShip(planet.getId(), probeName, "L");
        Assertions.assertEquals(OrientationEnum.WEST, probeReturn.getOrientation());
        Mockito.verify(planetInMemory, Mockito.atLeastOnce()).getPlanet(planet.getId());
    }

    @Test
    @DisplayName("Deve girar uma StarShip para direita")
    void shouldRotateRStarShip() {
        Mockito.when(planetInMemory.getPlanet(planet.getId())).thenReturn(planet);
        planet.placeStarShip(4, 4, probe);
        StarShip probeReturn = planetUseCase.moveStarShip(planet.getId(), probeName, "R");
        Assertions.assertEquals(OrientationEnum.EAST, probeReturn.getOrientation());
        Mockito.verify(planetInMemory, Mockito.atLeastOnce()).getPlanet(planet.getId());
    }

    @Test
    @DisplayName("Deve lançar uma exceção de command inválido")
    void shouldThrowExceptionInvalidCommand() {
        Mockito.when(planetInMemory.getPlanet(planet.getId())).thenReturn(planet);
        planet.placeStarShip(4, 4, probe);
        String id = planet.getId();
        BusinessException commandError = Assertions.assertThrows(BusinessException.class,
            () -> planetUseCase.moveStarShip(id, probeName, "B"));
        Assertions.assertEquals(BusinessError.INVALID_COMMAND.getStatus(), commandError.getStatus());
        Assertions.assertEquals(BusinessError.INVALID_COMMAND.getCode(), commandError.getCode());
        Assertions.assertEquals(BusinessError.INVALID_COMMAND.getDetails(), commandError.getDetail());
        Mockito.verify(planetInMemory, Mockito.atLeastOnce()).getPlanet(planet.getId());
    }

    @Test
    @DisplayName("Deve retornar uma probe ao passar seu nome")
    void shouldGetStarShip() {
        Mockito.when(planetInMemory.getPlanet(planet.getId())).thenReturn(planet);
        planet.placeStarShip(4, 4, probe);
        StarShip probeReturn = planetUseCase.getStarShip(planet.getId(), probeName);
        Assertions.assertEquals(probeReturn, probe);
        Mockito.verify(planetInMemory, Mockito.atLeastOnce()).getPlanet(planet.getId());
    }

    @Test
    @DisplayName("Deve retornar uma probe notFound")
    void shouldNotGetStarShipReturn404() {
        Mockito.when(planetInMemory.getPlanet(planet.getId())).thenThrow(new BusinessException(BusinessError.PROBE_NOT_FOUND));
        planet.placeStarShip(4, 4, probe);
        String id = planet.getId();
        BusinessException probeError = Assertions.assertThrows(BusinessException.class, () -> planetUseCase.getStarShip(id, probeName));
        Assertions.assertEquals(probeError.getCode(), BusinessError.PROBE_NOT_FOUND.getCode());
        Assertions.assertEquals(probeError.getStatus(), BusinessError.PROBE_NOT_FOUND.getStatus());
        Assertions.assertEquals(probeError.getDetail(), BusinessError.PROBE_NOT_FOUND.getDetails());
        Mockito.verify(planetInMemory, Mockito.atLeastOnce()).getPlanet(planet.getId());
    }

}

