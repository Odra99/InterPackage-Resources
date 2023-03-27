CREATE TABLE public.country(
    country_id bigint DEFAULT 0 NOT NULL,
    name  character varying(75) DEFAULT ''::character varying NOT NULL,
    iso_code_1  character varying(25) DEFAULT ''::character varying NOT NULL,
    iso_code_2  character varying(25) DEFAULT ''::character varying NOT NULL,
    iso_code_3  character varying(25) DEFAULT ''::character varying NOT NULL,
    CONSTRAINT country PRIMARY KEY (country_id)
);


CREATE TABLE public.city(
    city_id bigint DEFAULT 0 NOT NULL,
    country_id bigint DEFAULT 0 NOT NULL,
    name  character varying(75) DEFAULT ''::character varying NOT NULL,
    CONSTRAINT city PRIMARY KEY (city_id)
);

CREATE TABLE public.warehouse(
    warehouse_id bigint DEFAULT 0 NOT NULL,
    name  character varying(75) DEFAULT ''::character varying NOT NULL,
    address_line varying(500) DEFAULT ''::character varying NOT NULL,
    warehouse_type bigint DEFAULT 0 NOT NULL,
    city_id bigint DEFAULT 0 NOT NULL,
    CONSTRAINT warehouse PRIMARY KEY (warehouse_id)
);

CREATE TABLE public.route(
    route_id bigint DEFAULT 0 NOT NULL,
    name  character varying(75) DEFAULT ''::character varying NOT NULL,
    price_weight numeric(23,20) DEFAULT 0.0 NOT NULL,
    origin bigint DEFAULT 0 NOT NULL,
    destination bigint DEFAULT 0 NOT NULL,
    CONSTRAINT route PRIMARY KEY (route_id)
);

CREATE TABLE public.path(
    path_id bigint DEFAULT 0 NOT NULL,
    name  character varying(75) DEFAULT ''::character varying NOT NULL,
    route_id bigint DEFAULT 0 NOT NULL,
    origin bigint DEFAULT 0 NOT NULL,
    destination bigint DEFAULT 0 NOT NULL,
    priority int DEFAULT 0 NOT NULL,
    CONSTRAINT path PRIMARY KEY (path_id)
);

CREATE TABLE public.resources(
    path_id bigint DEFAULT 0 NOT NULL,
    warehouse_id bigint DEFAULT 0 NOT NULL,
    priority int DEFAULT 0 NOT NULL,
    status boolean DEFAULT FALSE NOT NULL,
    operating_cost numeric(23,20) DEFAULT 0.0 NOT NULL,
    CONSTRAINT path PRIMARY KEY (path_id,warehouse_id)
);


--
---Adding constraints----
--
ALTER TABLE ONLY public.city
    ADD CONSTRAINT city_country_fk FOREIGN KEY (country_id) REFERENCES public.country(country_id);

ALTER TABLE ONLY public.warehouse
    ADD CONSTRAINT warehouse_city_fk FOREIGN KEY (city_id) REFERENCES public.city(city_id);

ALTER TABLE ONLY public.route
    ADD CONSTRAINT route_origin_fk FOREIGN KEY (origin) REFERENCES public.city(city_id);

ALTER TABLE ONLY public.route
    ADD CONSTRAINT route_destination_fk FOREIGN KEY (destination) REFERENCES public.city(city_id);

ALTER TABLE ONLY public.path
    ADD CONSTRAINT path_route_fk FOREIGN KEY (route_id) REFERENCES public.route(route_id);

ALTER TABLE ONLY public.path
    ADD CONSTRAINT path_origin_fk FOREIGN KEY (origin) REFERENCES public.city(city_id);

ALTER TABLE ONLY public.path
    ADD CONSTRAINT path_destination_fk FOREIGN KEY (destination) REFERENCES public.city(city_id);

ALTER TABLE ONLY public.resources
    ADD CONSTRAINT resources_path_fk FOREIGN KEY (path_id) REFERENCES public.path(path_id);

ALTER TABLE ONLY public.resources
    ADD CONSTRAINT resources_warehouse_fk FOREIGN KEY (warehouse_id) REFERENCES public.warehouse(warehouse_id);
