package com.interpackage.resources.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.interpackage.resources.AbstractIntegrationTest;
import com.interpackage.resources.model.Response;
import com.interpackage.resources.model.Route;
import com.interpackage.resources.repository.RouteRepository;
import com.interpackage.resources.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@Testcontainers
@SpringBootTest
class RouteServiceTests extends AbstractIntegrationTest {

    @Autowired
    private RouteService routeService;

    @Autowired
    private RouteRepository routeRepository;

    private Route route;

    @BeforeEach
    void setUp() {
        this.routeRepository.deleteAll();
        this.route = new Route(1L, "Ruta-Test",  BigDecimal.TEN, 1L, 2L);
    }

    @Test
    void createRouteSuccess() {
        ResponseEntity<Response> responseEntity = routeService.create(route);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertTrue(((Route) Objects.requireNonNull(responseEntity.getBody())
                .getResponseObject())
                .getName().contains("Ruta-Test"));
    }

    @Test
    void createRouteAlreadyExists() {
        routeRepository.save(route);
        ResponseEntity<Response> responseEntity = routeService.create(route);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody())
                .getMessage().contains("Ya existe una ruta con el nombre"));
    }

    @Test
    void createRouteMissingFields() {
        ResponseEntity<Response> responseEntity = routeService.create(new Route());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody())
                .getMessage().contains(Constants.REQUIRED_FIELDS));
    }

    @Test
    void editRouteSuccess() throws URISyntaxException {
        Route routeUpdate = new Route(1L, "Ruta1", BigDecimal.ONE, 1L, 2L);
        routeRepository.save(route);

        ResponseEntity<Response> response = routeService.edit(routeUpdate);

        // Comprueba que la respuesta sea un 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Comprueba que se hayan actualizado los datos correctamente
        Route updatedRoute = (Route) Objects.requireNonNull(routeService.getById(1L).getBody()).getResponseObject();
        assertThat(updatedRoute.getName()).isEqualTo("Ruta1");
        assertThat(updatedRoute.getPriceWeight()).isEqualTo(new BigDecimal("1.000000"));
        assertThat(updatedRoute.getOrigin()).isEqualTo(1L);
        assertThat(updatedRoute.getDestination()).isEqualTo(2L);
    }

    @Test
    void editRouteAlreadyExists() {
        Route existingRoute = new Route(1L, "Existing Route", BigDecimal.ONE, 1L, 2L);
        routeRepository.save(existingRoute);
        Route routeToUpdate = new Route();
        routeToUpdate.setRouteId(20L);
        routeToUpdate.setName("Existing Route");
        routeToUpdate.setPriceWeight(BigDecimal.TEN);
        routeToUpdate.setOrigin(1L);
        routeToUpdate.setDestination(2L);

        // Act
        ResponseEntity<Response> response = routeService.edit(routeToUpdate);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(Objects.requireNonNull(response.getBody()).getMessage()).contains("Ya existe una ruta con el nombre");
    }

    @Test
    void editRouteDoesNotExist() {
        Route routeDoesNotExist = new Route(100L, "Existing Route", BigDecimal.ONE, 1L, 2L);
        // Act
        ResponseEntity<Response> response = routeService.edit(routeDoesNotExist);
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void editRouteMissingFields() {
        Route existingRoute = new Route(1L, "Existing", BigDecimal.ONE, 1L, 2L);
        routeRepository.save(existingRoute);
        Route routeMissingFields = new Route();
        routeMissingFields.setRouteId(3L);
        routeMissingFields.setName("Test");
        ResponseEntity<Response> responseEntity = routeService.edit(routeMissingFields);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody())
                .getMessage().contains(Constants.REQUIRED_FIELDS));
    }

    @Test
    void getAllRoutes() {
        routeRepository.save(new Route(1L, "Existing1", BigDecimal.ONE, 1L, 2L));
        routeRepository.save(new Route(2L, "Existing2", BigDecimal.ONE, 1L, 2L));
        routeRepository.save(new Route(3L, "Existing3", BigDecimal.ONE, 1L, 2L));

        // Act
        ResponseEntity<Response> response = routeService.getAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        List<Route> responseRoutes = (List<Route>) response.getBody().getResponseObject();
        assertEquals(3, responseRoutes.size());
    }

    @Test
    void getRouteMissingFields(){
        routeRepository.save(new Route(1L, "Existing", BigDecimal.ONE, 1L, 2L));
        ResponseEntity<Response> responseEntity = routeService.getById(null);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody())
                .getMessage().contains(Constants.REQUIRED_FIELDS));
    }
}
