package com.cs.BatchAutoGenerateParameter.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cs.BatchAutoGenerateParameter.entities.IndividualParameterD;


@Repository
public interface IIndividualParameterDJPARepository extends JpaRepository<IndividualParameterD, Integer>{
	
	
//	final String dtoColumn = " new com.internalservices.edclogservice.dto.CommonParameterDDto(v.id,"
//			+ "	v.commonParameterHId,"
//			+ "	v.parameterVersionId,"
//			+ "	v.parameterId,"
//			+ "	v.parameterValue,"
//			+ "	v.applicationStatus,"
//			+ "	v.rejectReason, "
//			+ "	v.approveById,"
//			+ "	v.approveDate,"
//			+ "	v.actionForm,"
//			+ "	v.createdById,"
//			+ "	v.createdDate,"
//			+ "	v.updatedById,"
//			+ "	v.updatedDate) ";
//	
//	
//	@Query("SELECT "
//			+ dtoColumn
//			+ " FROM CommonParameterD v where v.parameterVersionId = :parameterVersionId and v.commonParameterHId = :commonParameterHId")
//	List<CommonParameterDDto> findDtoByVersionId(@Param("parameterVersionId") Integer parameterVersionId,@Param("commonParameterHId") Integer commonParameterHId);
	
	
	final String findByVersionIdAndHIdQueue = """
			SELECT 
			inpd.parameter_id AS parameterCode,
			param.description AS description,
			type.pack_type AS packTypeName,
			param.parameter_length AS parameterLength,
			inpd.parameter_value AS parameterValue
			FROM 
			mst_individual_parameter_d inpd
			INNER JOIN mst_terminal_parameter param
			ON inpd.parameter_id = param.id
			INNER JOIN mst_pack_type type
			ON param.pack_type_id = type.id
			WHERE 
			inpd.individual_parameter_h_id = :individualParameterHId
			AND inpd.parameter_version_id = :parameterVersionId
			ORDER BY inpd.parameter_version_id DESC
			""";

	@Query(value = findByVersionIdAndHIdQueue, nativeQuery = true)
	List<Object[]> findByVersionIdAndHId(@Param("parameterVersionId") Integer parameterVersionId,@Param("individualParameterHId") Integer individualParameterHId);
	
	
	final String baseColumn = """
			SELECT
				d.id,
				d.individual_parameter_h_id,
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
			
			 	,tm.parameter_code
			 	,e.id as enterpriseId
			 	,m.id as merchantId
			
				FROM mst_individual_parameter_h h 
				LEFT JOIN mst_individual_parameter_version v ON v.individual_parameter_h_id = h.id
				LEFT JOIN mst_individual_parameter_d d ON d.parameter_version_id = v.id
				LEFT JOIN mst_terminal_parameter tm ON tm.id = d.parameter_id
				LEFT JOIN terminal t on t.id = h.terminal_id
                LEFT JOIN merchant m on m.id = t.merchant_id
                LEFT JOIN enterprise e on e.id = m.enterprise_id
				WHERE h.active_flag is true AND h.terminal_id = :terminalId
				
			""";
	@Query(value = querySearchDetailD, nativeQuery = true)
	List<Object[]> searchDetailByTerminalId(@Param("terminalId") Integer terminalId);
	
	final String queryGenerateSearchAll = """
			
			SELECT ih.id as h_Id, iv.id as V_Id,iv.parameter_version, m.enterprise_id,ih.merchant_id,ih.terminal_id,ih.batch_flag
			FROM mst_individual_parameter_h ih 
			LEFT JOIN mst_individual_parameter_version iv ON iv.individual_parameter_h_id = ih.id
			LEFT JOIN merchant m ON m.id = ih.merchant_id
			LEFT JOIN enterprise e ON e.id = m.enterprise_id
			LEFT JOIN terminal t ON t.id = ih.terminal_id
			WHERE ih.active_flag is true AND ih.terminal_id = :terminalId
		
		""";
	@Query(value = queryGenerateSearchAll, nativeQuery = true)
	List<Object[]> searchGenerateIndividualHByTerminalId(@Param("terminalId") Integer terminalId);
	
	final String queryGenerateSearchChild = baseColumn+"""
			
			,tp.parameter_code
			
			FROM mst_individual_parameter_d d
			LEFT join mst_terminal_parameter tp on tp.id = d.parameter_id
			WHERE d.individual_parameter_h_id  = :parameterHId and d.parameter_version_id = :parameterVersionId
		
		""";
	@Query(value = queryGenerateSearchChild, nativeQuery = true)
	List<Object[]> searchGenerateIndividualDetail(@Param("parameterHId") Integer parameterHId, @Param("parameterVersionId") Integer parameterVersionId);
	
}
