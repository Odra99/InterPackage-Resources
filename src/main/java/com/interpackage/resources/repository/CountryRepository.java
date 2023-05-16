package com.interpackage.resources.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.interpackage.resources.model.Country;
@Repository
public interface CountryRepository extends JpaRepository<Country,Long> {
}
