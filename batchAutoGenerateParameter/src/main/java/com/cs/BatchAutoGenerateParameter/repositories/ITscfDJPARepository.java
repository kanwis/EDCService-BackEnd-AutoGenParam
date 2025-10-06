package com.cs.BatchAutoGenerateParameter.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cs.BatchAutoGenerateParameter.entities.TscfD;


@Repository
public interface ITscfDJPARepository extends JpaRepository<TscfD, Integer>{

}
