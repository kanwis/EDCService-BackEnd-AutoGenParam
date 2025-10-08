package com.cs.BatchAutoGenerateParameter.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cs.BatchAutoGenerateParameter.dto.CommonParameterHDto;
import com.cs.BatchAutoGenerateParameter.entities.CommonParameterH;

@Repository
public interface ICommonParameterHJPARepository extends JpaRepository<CommonParameterH, Integer>{
	
	final String dtoColumn = """ 
				new com.cs.BatchAutoGenerateParameter.dto.CommonParameterHDto(			
				h.id,
				h.enterpriseId,
				h.activeFlag,
				h.startDate,
				h.applicationStatus,
				h.rejectReason,
				h.approveById,
				h.approveDate,
				h.actionForm,
				h.createdById,
				h.createdDate,
				h.updatedById,
				h.updatedDate,
				h.batchFlag)
					""";
	@Query(" SELECT "
			+ dtoColumn
			+ " FROM CommonParameterH h where h.id = :id ")
	CommonParameterHDto findDtoById(@Param("id") Integer id);
	
	
	@Modifying
	@Transactional
	@Query("""
	    UPDATE CommonParameterH h 
	    SET h.batchFlag = true, h.updatedDate = CURRENT_TIMESTAMP , h.updatedById=:updatedById
	    WHERE h.id IN :ids
	""")
	int updateBatchFlag(@Param("ids") List<Integer> ids, int updatedById);

	
	
	
}
