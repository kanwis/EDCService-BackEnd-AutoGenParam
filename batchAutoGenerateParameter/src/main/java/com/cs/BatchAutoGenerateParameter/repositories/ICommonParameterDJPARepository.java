package com.cs.BatchAutoGenerateParameter.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cs.BatchAutoGenerateParameter.dto.CommonParameterDDto;
import com.cs.BatchAutoGenerateParameter.entities.CommonParameterD;

import jakarta.transaction.Transactional;

@Repository
public interface ICommonParameterDJPARepository extends JpaRepository<CommonParameterD, Integer>{
	
	
	final String dtoColumn = " new com.cs.BatchAutoGenerateParameter.dto.CommonParameterDDto(v.id,"
			+ "	v.commonParameterHId,"
			+ "	v.parameterVersionId,"
			+ "	v.parameterId,"
			+ "	v.parameterValue,"
			+ "	v.applicationStatus,"
			+ "	v.rejectReason, "
			+ "	v.approveById,"
			+ "	v.approveDate,"
			+ "	v.actionForm,"
			+ "	v.createdById,"
			+ "	v.createdDate,"
			+ "	v.updatedById,"
			+ "	v.updatedDate) ";
	
	
	@Query("SELECT "
			+ dtoColumn
			+ " FROM CommonParameterD v where v.parameterVersionId = :parameterVersionId and v.commonParameterHId = :commonParameterHId")
	List<CommonParameterDDto> findDtoByVersionId(@Param("parameterVersionId") Integer parameterVersionId,@Param("commonParameterHId") Integer commonParameterHId);
	
	final String baseColumn = """
			SELECT
			 d.id,
			 d.common_parameter_h_id,
			 d.parameter_version_id, 
			 d.parameter_id,
			 d.parameter_value,
			 d.application_status, 
			 d.reject_reason, 
			 d.approve_by_id, 
			 d.approve_date, 
			 d.action_form, 
			 d.created_by_id, 
			 d.created_date, 
			 d.updated_by_id, 
			 d.updated_date
			 
			""";
	
	final String querySearchDetailD = baseColumn+"""
			,tp.parameter_code
			,e.id as enterpriseId
			,m.id as merchantId
			,h.batch_flag
			
			FROM mst_common_parameter_h h 
			LEFT JOIN mst_common_parameter_version v ON v.common_parameter_h_id = h.id
			LEFT JOIN mst_common_parameter_d d ON d.parameter_version_id = v.id
			LEFT JOIN mst_terminal_parameter tp ON tp.id = d.parameter_id
			LEFT JOIN enterprise e ON e.id = h.enterprise_id
			LEFT JOIN merchant m ON m.enterprise_id = e.id
			LEFT JOIN terminal t ON t.merchant_id = m.id
			WHERE h.active_flag is true 
			and (:enterpriseId IS NULL OR e.id = :enterpriseId) 
			and (:merchantId IS NULL OR m.id = :merchantId) 
			and (:terminalId IS NULL OR t.id = :terminalId) 
			
			""";
	@Query(value = querySearchDetailD, nativeQuery = true)
	List<Object[]> searchDetailByTerminalId(@Param("enterpriseId") Integer enterpriseId , @Param("merchantId") Integer merchantId, @Param("terminalId") Integer terminalId);
	
	
	
	final String queryGenerateSearchAll = """
				
			SELECT h.id as h_Id, v.id as V_Id,v.parameter_version, h.enterprise_id,m.id as merchantId,h.batch_flag
			,e.enterprise_id,m.merchant_id,e.enterprise_name_th
			FROM mst_common_parameter_h h 
			LEFT JOIN mst_common_parameter_version v ON v.common_parameter_h_id = h.id 
			LEFT JOIN enterprise e ON e.id = h.enterprise_id 
			LEFT JOIN merchant m ON m.enterprise_id = e.id 
			WHERE h.active_flag is true 
		
		""";
	@Query(value = queryGenerateSearchAll, nativeQuery = true)
	List<Object[]> searchGenerateCommonHAll();
	
	final String querySearchByEnterpriseId = """
			
			SELECT h.id as h_Id, v.id as V_Id,v.parameter_version, h.enterprise_id,m.id as merchantId ,t.id as terminalId,h.batch_flag
			,e.enterprise_id,m.merchant_id,t.terminal_id
			FROM mst_common_parameter_h h 
			LEFT JOIN mst_common_parameter_version v ON v.common_parameter_h_id = h.id 
			LEFT JOIN enterprise e ON e.id = h.enterprise_id 
			LEFT JOIN merchant m ON m.enterprise_id = e.id 
			LEFT JOIN terminal t ON t.merchant_id = m.id
			WHERE h.active_flag is true and t.id is not null
			and (:enterpriseId IS NULL OR e.id = :enterpriseId) 
			and (:merchantId IS NULL OR m.id = :merchantId) 
			and (:terminalId IS NULL OR t.id = :terminalId) 
		
		""";
	@Query(value = querySearchByEnterpriseId, nativeQuery = true)
	List<Object[]> searchGenerateCommonH(@Param("enterpriseId") Integer enterpriseId , @Param("merchantId") Integer merchantId, @Param("terminalId") Integer terminalId);
	
	final String queryGenerateSearchChild = baseColumn+"""
			
			,tp.parameter_code
			
			FROM mst_common_parameter_d d
			Left join mst_terminal_parameter tp on tp.id = d.parameter_id
			where d.common_parameter_h_id  = :parameterHId and d.parameter_version_id = :parameterVersionId
		
		""";
	@Query(value = queryGenerateSearchChild, nativeQuery = true)
	List<Object[]> searchGenerateCommonDetail(@Param("parameterHId") Integer parameterHId, @Param("parameterVersionId") Integer parameterVersionId);

	
	final String queryDeleteByParameterHIdAndParameterVersionId = "DELETE FROM `mst_common_parameter_d` WHERE `common_parameter_h_id` =:parameterHId and `parameter_version_id` =:parameterVersionId ";
	@Query(value=queryDeleteByParameterHIdAndParameterVersionId,nativeQuery = true)
	@Modifying
	@Transactional
	void deleteByParameterHIdAndParameterVersionId(@Param("parameterHId") Integer parameterHId, @Param("parameterVersionId") Integer parameterVersionId);
	
}
