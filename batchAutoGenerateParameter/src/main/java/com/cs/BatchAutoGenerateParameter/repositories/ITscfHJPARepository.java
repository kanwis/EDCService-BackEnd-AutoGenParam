package com.cs.BatchAutoGenerateParameter.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cs.BatchAutoGenerateParameter.entities.TscfH;


@Repository
public interface ITscfHJPARepository extends JpaRepository<TscfH, Integer>{

}