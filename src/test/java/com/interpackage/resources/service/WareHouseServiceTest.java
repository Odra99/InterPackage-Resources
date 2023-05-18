package com.interpackage.resources.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.interpackage.resources.AbstractIntegrationTest;
import com.interpackage.resources.PostgreSQLExtension;
import com.interpackage.resources.model.*;
import com.interpackage.resources.repository.CityRepository;
import com.interpackage.resources.repository.CountryRepository;
import com.interpackage.resources.repository.WareHouseRepository;
import com.interpackage.resources.util.Constants;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Testcontainers
@SpringBootTest
@ExtendWith(PostgreSQLExtension.class)
@DirtiesContext
class WareHouseServiceTest {

    @Autowired
    private WareHouseService wareHouseService;

    @Autowired
    private WareHouseRepository wareHouseRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;

    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        var country = new Country(1L, "Country-test", "1", "2", "3");
        var city = new City(1L, "City-test", country);
        this.warehouse = new Warehouse(
                1L, "Warehouse-test", city,
                1L, "address", false);

        this.wareHouseRepository.deleteAll();
        this.countryRepository.deleteAll();
        this.cityRepository.deleteAll();
        this.countryRepository.save(country);
        this.cityRepository.save(city);

    }

    // Tests for create
    @Test
    void testCreateWarehouseSuccess() {
        ResponseEntity<Response> response = wareHouseService.create(warehouse);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(((Warehouse) Objects.requireNonNull(response.getBody())
                .getResponseObject())
                .getName().contains("Warehouse-test"));
    }

    @Test
    void testCreateWarehouseMissingFields() {
        ResponseEntity<Response> responseEntity = wareHouseService.create(new Warehouse());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody())
                .getMessage().contains(Constants.REQUIRED_FIELDS));
    }

    @Test
    void testCreateWarehouseErrorWritingToDb() {
        this.warehouse.getCity().setCityId(4L);
        ResponseEntity<Response> responseEntity = wareHouseService.create(this.warehouse);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody())
                .getMessage().contains(Constants.INTERNAL_SERVER_ERROR_DB));
    }

    // Tests for update
    @Test
    void testupdateWarehouseNullId() {
        this.warehouse.setWarehouseId(null);
        ResponseEntity<Response> responseEntity = wareHouseService.update(this.warehouse);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody())
                .getMessage().contains(Constants.REQUIRED_ID));
    }

    @Test
    void testupdateWarehouseSuccess() {
        this.warehouse.setName("Warehouse2");
        ResponseEntity<Response> responseEntity = wareHouseService.update(this.warehouse);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Warehouse updatedWarehouse = (Warehouse) Objects.requireNonNull(
                wareHouseService.findById(
                        warehouse.getWarehouseId()).getBody())
                .getResponseObject();
        assertThat(updatedWarehouse.getName()).isEqualTo("Warehouse2");
    }

    @Test
    void testupdateWarehouseError() {
        this.warehouse.setName("Warehouse2");
        this.warehouse.getCity().setCityId(2L);
        ResponseEntity<Response> responseEntity = wareHouseService.update(this.warehouse);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody())
                .getMessage().contains(Constants.INTERNAL_SERVER_ERROR_DB));
    }

    //Delete warehouse
    @Test
    void testdeleteWarehouseNullId() {
        ResponseEntity<Response> responseEntity = wareHouseService.delete(null);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody())
                .getMessage().contains(Constants.REQUIRED_ID));
    }
 
    @Test
    void testdeleteWarehouseSuccess() {
        this.wareHouseRepository.save(this.warehouse);
        ResponseEntity<Response> responseEntity = wareHouseService.delete(this.warehouse.getWarehouseId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Warehouse updatedWarehouse = (Warehouse) Objects.requireNonNull(
                wareHouseService.findById(
                        warehouse.getWarehouseId()).getBody())
                .getResponseObject();
        assertThat(updatedWarehouse.isDeleted()).isEqualTo(true);
    }

    @Test
    void testdeleteWarehouseNotFound() {
        ResponseEntity<Response> responseEntity = wareHouseService.delete(this.warehouse.getWarehouseId());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testdeleteWarehouseErrorDb() {
        this.wareHouseService.create(this.warehouse);
        ResponseEntity<Response> responseEntity = wareHouseService.delete(this.warehouse.getWarehouseId());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody())
                .getMessage().contains(Constants.INTERNAL_SERVER_ERROR_DB));
    }

    @Test
    void testfindWarehouseById() {
        this.wareHouseService.create(this.warehouse);
        ResponseEntity<Response> responseEntity = wareHouseService.findById(this.warehouse.getWarehouseId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody())
                .getResponseObject().equals(this.warehouse));
    }

    @Test
    void testfindWarehouseByError() {
        this.wareHouseService.create(this.warehouse);
        ResponseEntity<Response> responseEntity = wareHouseService.findById(this.warehouse.getWarehouseId());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
       
    }

    @Test
    void testfindWarehouseAll() {
        List<Warehouse> results = new ArrayList<Warehouse>();
        results.add(this.warehouse);
        this.wareHouseService.create(this.warehouse);
        ResponseEntity<Response> responseEntity = wareHouseService.findAll();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody())
                .getResponseObject().equals(results));
    }

    @Test
    void testfindWarehouseAllError() {
        List<Warehouse> results = new ArrayList<Warehouse>();
        results.add(this.warehouse);
        this.wareHouseService.create(this.warehouse);
        ResponseEntity<Response> responseEntity = wareHouseService.findAll();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}