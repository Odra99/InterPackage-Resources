package com.interpackage.resources.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.interpackage.resources.AbstractIntegrationTest;
import com.interpackage.resources.model.Path;
import com.interpackage.resources.model.Response;
import com.interpackage.resources.model.Route;

@Testcontainers
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class PathServiceTest extends AbstractIntegrationTest {

    @Autowired
    private PathService pathService;

    @Autowired
    private RouteService routeService;

    private Path path = new Path();

    private Route route;

    private static Long pathId = Long.valueOf(0);

    @BeforeEach
    void setUp() {
        route = new Route();
        this.route.setName("TEST-ROUTE");
        this.route.setDestination(Long.valueOf(0));
        this.route.setOrigin(Long.valueOf(0));
        this.route.setPriceWeight(new BigDecimal(0.0));
        this.route.setRouteId(Long.valueOf(1));
        this.routeService.create(route);
        this.path.setPathId(null);
        this.path.setName("TEST-PATH");
        this.path.setRoute(route);
        this.path.setDestination(Long.valueOf(0));
        this.path.setOrigin(Long.valueOf(0));
        this.path.setPriority(1);
    }

    @Test
    @Order(1)
    void testCreate() {
        ResponseEntity<Response> responseEntity = pathService.create(path);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertFalse(path.isDeleted());
        assertNotNull(path.getPathId());
        pathId = path.getPathId();
    }

    @Test
    @Order(2)
    void testCreateWithNameAlreadyRegistered() {
        ResponseEntity<Response> responseEntity = pathService.create(path);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @Order(3)
    void testGetPath() {
        ResponseEntity<Response> responseEntity = pathService.getById(pathId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    @Order(4)
    void testGetPathNotExists() {
        ResponseEntity<Response> responseEntity = pathService.getById(Long.valueOf(1000));
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

    }

    @Test
    @Order(5)
    void testUpdate() {
        path.setName("UPDATE-NAME");
        path.setPathId(pathId);
        ResponseEntity<Response> responseEntity = pathService.update(path);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Path pathDb = (Path) pathService.getById(pathId).getBody().getResponseObject();
        assertEquals("UPDATE-NAME", pathDb.getName());
    }

    @Test
    @Order(6)
    void testUpdateNotFound() {
        path.setPathId(Long.valueOf(-1));
        ResponseEntity<Response> responseEntity = pathService.update(path);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @Order(7)
    void testUpdateRouteNotFound() {
        route.setRouteId(Long.valueOf(-1));
        ResponseEntity<Response> responseEntity = pathService.update(path);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Ruta no encontrada", responseEntity.getBody().getMessage());
    }

    @Test
    @Order(8)
    void testUpdateNameAlreadyExisted() {
        path.setName("UPDATE-NAME");
        path.setPathId(Long.valueOf(-1));
        ResponseEntity<Response> responseEntity = pathService.update(path);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Ya existe path con el nombre " + path.getName(), responseEntity.getBody().getMessage());
    }

    @Test
    @Order(9)
    void testChangeStatus() {
        ResponseEntity<Response> responseEntity = pathService.changeStatus(pathId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        var pathDB = (Path) responseEntity.getBody().getResponseObject();
        assertEquals(false, pathDB.isActive());
        responseEntity = pathService.changeStatus(pathId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        pathDB = (Path) responseEntity.getBody().getResponseObject();
        assertEquals(true, pathDB.isActive());
    }

    @Test
    @Order(10)
    void testDeletePath() {
        ResponseEntity<Response> responseEntity = pathService.delete(pathId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @Order(11)
    void testNotExistedDeletePath() {
        ResponseEntity<Response> responseEntity = pathService.delete(pathId);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Trayecto no encontrado", responseEntity.getBody().getMessage());
    }

}
