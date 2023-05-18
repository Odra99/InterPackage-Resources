package com.interpackage.resources.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.interpackage.resources.AbstractIntegrationTest;
import com.interpackage.resources.PostgreSQLExtension;
import com.interpackage.resources.model.Response;
import com.interpackage.resources.model.Route;
import com.interpackage.resources.repository.RouteRepository;
import com.interpackage.resources.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@Testcontainers
@SpringBootTest
@ExtendWith(PostgreSQLExtension.class)
@DirtiesContext
class RouteServiceTests {

    @Autowired
    private RouteService routeService;

    @Autowired
    private RouteRepository routeRepository;

    private Route route;

    @BeforeEach
    void setUp() {
        this.routeRepository.deleteAll();
        this.route = new Route(1L, "Ruta-Test",  BigDecimal.TEN, 1L, 2L, false);
    }

    @Test
    void testCreateRouteSuccess() {
        ResponseEntity<Response> responseEntity = routeService.create(route);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertTrue(((Route) Objects.requireNonNull(responseEntity.getBody())
                .getResponseObject())
                .getName().contains("Ruta-Test"));
    }

    @Test
    void testCreateRouteAlreadyExists() {
        routeRepository.save(route);
        ResponseEntity<Response> responseEntity = routeService.create(route);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody())
                .getMessage().contains("Ya existe una ruta con el nombre"));
    }

    @Test
    void testCreateRouteMissingFields() {
        ResponseEntity<Response> responseEntity = routeService.create(new Route());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody())
                .getMessage().contains(Constants.REQUIRED_FIELDS));
    }

    @Test
    void testEditRouteSuccess() throws URISyntaxException {
        Route routeUpdate = new Route(1L, "Ruta2", BigDecimal.ONE, 1L, 2L, false);
        routeUpdate = routeRepository.save(routeUpdate);
        routeUpdate.setName("Ruta2");
        routeUpdate.setPriceWeight(BigDecimal.TEN);
        routeUpdate.setOrigin(2L);
        routeUpdate.setDestination(3L);
        ResponseEntity<Response> response = routeService.edit(routeUpdate);

        // Comprueba que la respuesta sea un 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Comprueba que se hayan actualizado los datos correctamente
        Route updatedRoute = (Route) Objects.requireNonNull(
                routeService.getById(
                        routeUpdate.getRouteId()).getBody()).getResponseObject();
        assertThat(updatedRoute.getName()).isEqualTo("Ruta2");
        assertThat(updatedRoute.getPriceWeight()).isEqualTo(new BigDecimal("10.000000"));
        assertThat(updatedRoute.getOrigin()).isEqualTo(2L);
        assertThat(updatedRoute.getDestination()).isEqualTo(3L);
    }

    @Test
    void testEditRouteAlreadyExists() {
        Route existingRoute = new Route(1L, "Existing Route", BigDecimal.ONE, 1L, 2L, false);
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
    void testEditRouteDoesNotExist() {
        Route routeDoesNotExist = new Route(100L, "Existing Route", BigDecimal.ONE, 1L, 2L, false);
        // Act
        ResponseEntity<Response> response = routeService.edit(routeDoesNotExist);
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testEditRouteMissingFields() {
        Route existingRoute = new Route(1L, "Existing", BigDecimal.ONE, 1L, 2L, false);
        existingRoute = routeRepository.save(existingRoute);
        Route routeMissingFields = new Route();
        routeMissingFields.setRouteId(existingRoute.getRouteId());
        routeMissingFields.setName("Test");
        ResponseEntity<Response> responseEntity = routeService.edit(routeMissingFields);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody())
                .getMessage().contains(Constants.REQUIRED_FIELDS));
    }

    @Test
    void testGetAllRoutes() {
        routeRepository.save(new Route(1L, "Existing1", BigDecimal.ONE, 1L, 2L, false));
        routeRepository.save(new Route(2L, "Existing2", BigDecimal.ONE, 1L, 2L, false));
        routeRepository.save(new Route(3L, "Existing3", BigDecimal.ONE, 1L, 2L, false));

        // Act
        ResponseEntity<Response> response = routeService.getAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        List<Route> responseRoutes = (List<Route>) response.getBody().getResponseObject();
        assertEquals(3, responseRoutes.size());
    }

    @Test
    void testGetRouteMissingFields() {
        routeRepository.save(new Route(1L, "Existing", BigDecimal.ONE, 1L, 2L, false));
        ResponseEntity<Response> responseEntity = routeService.getById(null);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody())
                .getMessage().contains(Constants.REQUIRED_FIELDS));
    }

    @Test
    void testGetById() {
        Route testRoute = routeRepository.save(
                new Route(1L, "Existing-Test", BigDecimal.ONE, 1L, 2L, false));

        ResponseEntity<Response> response = routeService.getById(testRoute.getRouteId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testRoute.getRouteId(), ((Route) response.getBody().getResponseObject()).getRouteId());
    }

    @Test
    void testDeleteSuccess() {
        Route testRoute = routeRepository.save(
                new Route(1L, "Existing-Test", BigDecimal.ONE, 1L, 2L, false));

        ResponseEntity<Response> response = routeService.delete(testRoute.getRouteId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(((Route) Objects.requireNonNull(response.getBody()).getResponseObject()).isDeleted());
        assertTrue(routeRepository.findById(testRoute.getRouteId()).get().isDeleted());
    }

    @Test
    void testDeleteMissingField() {
        routeRepository.save(new Route(1L, "Existing-Test1", BigDecimal.ONE, 1L, 2L, false));
        ResponseEntity<Response> responseEntity = routeService.delete(null);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody())
                .getMessage().contains(Constants.REQUIRED_FIELDS));
    }

    @Test
    void testDeleteNotFound() {
        ResponseEntity<Response> response = routeService.delete(Long.MAX_VALUE);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
