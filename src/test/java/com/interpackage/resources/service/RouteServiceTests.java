package com.interpackage.resources.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.interpackage.resources.AbstractIntegrationTest;
import com.interpackage.resources.model.Response;
import com.interpackage.resources.model.Route;
import com.interpackage.resources.repository.RouteRepository;
import com.interpackage.resources.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Testcontainers
@SpringBootTest
public class RouteServiceTests extends AbstractIntegrationTest {

    @Autowired
    private RouteService routeService;

    @Autowired
    private RouteRepository routeRepository;

    @Value("${server.port}")
    private String port;

    private Route route;

    @BeforeEach
    void setUp() {
        routeRepository.deleteAll();
        this.route = new Route(1L, "Ruta-Test", new BigDecimal("12.50"), 1L, 2L);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void createRouteSuccess() throws URISyntaxException {
        RequestEntity<Route> request = RequestEntity
                .post(new URI(Constants.HOST + port + "/route/"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(route);

        ResponseEntity<Response> responseEntity = routeService.create(Objects.requireNonNull(request.getBody()));
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertTrue(((Route) Objects.requireNonNull(responseEntity.getBody())
                .getResponseObject())
                .getName().contains("Ruta-Test"));
    }

    @Test
    void createRouteAlreadyExists() throws URISyntaxException {
        routeRepository.save(route);

        RequestEntity<Route> request = RequestEntity
                .post(new URI(Constants.HOST + port + "/route/"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(route);

        ResponseEntity<Response> responseEntity = routeService.create(Objects.requireNonNull(request.getBody()));
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody())
                .getMessage().contains("Ya existe una ruta con el nombre"));
    }

    @Test
    void createRouteMissingFields() throws URISyntaxException {
        Route route = new Route();

        RequestEntity<Route> request = RequestEntity
                .post(new URI(Constants.HOST + port + "/routes"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(route);

        ResponseEntity<Response> responseEntity = routeService.create(Objects.requireNonNull(request.getBody()));
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody())
                .getMessage().contains("Todos los campos son requeridos"));
    }
}
