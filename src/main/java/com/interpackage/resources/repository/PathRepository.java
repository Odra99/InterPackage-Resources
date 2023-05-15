package com.interpackage.resources.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interpackage.resources.model.Path;

@Repository
public interface PathRepository extends JpaRepository<Path,Long>{

    boolean existsPathByName(String name);
    boolean existsPathByNameAndPathIdIsNot(String name, Long id);

    
}
