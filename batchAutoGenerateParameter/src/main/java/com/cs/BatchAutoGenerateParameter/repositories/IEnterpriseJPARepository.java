package com.cs.BatchAutoGenerateParameter.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cs.BatchAutoGenerateParameter.entities.Enterprise;

@Repository
public interface IEnterpriseJPARepository extends JpaRepository<Enterprise,Integer>{
	
	Enterprise findByEnterpriseId(String enterpriseId);
	
}
