package com.cs.BatchAutoGenerateParameter.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cs.BatchAutoGenerateParameter.dto.IndividualParameterHDto;
import com.cs.BatchAutoGenerateParameter.entities.IndividualParameterH;


@Repository
public interface IIndividualParameterHJPARepository extends JpaRepository<IndividualParameterH, Integer> {

	final String searchIndividualParameterH = """
				SELECT
					inph.id AS inphId,
					ver.id AS verId,
					CONCAT_WS('-', e.enterprise_id, e.enterprise_name_th) AS enterprise,
					CONCAT_WS('-', m.merchant_id, m.merchant_name_th) AS merchant,
					t.terminal_id AS terminal,
					LPAD(LAST_INSERT_ID(ver.parameter_version),4,'0') AS version,
				  	DATE_FORMAT(
				    	NULLIF(NULLIF(inph.start_date, '0000-00-00'), '0000-00-00 00:00:00'),
				    	'%Y-%m-%d'
				  	) AS startDate,
				  	CASE WHEN inph.active_flag = 1 THEN 'edit' ELSE 'view' END AS action,
				  	inph.updated_date AS updateDate,
				  	CONCAT_WS(' ', users.first_name, users.last_name) AS approveBy,
				  	users.last_login AS lastLogin
				FROM mst_individual_parameter_h inph
				INNER JOIN merchant m
				ON inph.merchant_id = m.id
				INNER JOIN enterprise e
				ON m.enterprise_id = e.id
				INNER JOIN terminal t
				ON inph.terminal_id = t.id
				INNER JOIN mst_individual_parameter_version ver
				ON ver.individual_parameter_h_id = inph.id
				INNER JOIN users
				ON inph.approve_by_id = users.id
				WHERE
				  ( :enterpriseId IS NULL OR TRIM(:enterpriseId) = '' OR m.enterprise_id = :enterpriseId )
				AND
				  ( :merchantId IS NULL OR TRIM(:merchantId) = '' OR inph.merchant_id = :merchantId )
				AND
				  ( :terminalId IS NULL OR TRIM(:terminalId) = '' OR inph.terminal_id = :terminalId )
				AND
				  inph.application_status = 1
				AND
				  ver.application_status = 1
				ORDER BY ver.parameter_version DESC, e.enterprise_name_th DESC, ver.id DESC
			""";

	@Query(value = searchIndividualParameterH, nativeQuery = true)
	List<Object[]> searchIndividualParameterH(@Param("enterpriseId") Integer enterpriseId,
			@Param("merchantId") Integer merchantId, @Param("terminalId") Integer terminalId);
	
	
	final String searchCopyParameterIndividualParameter = """
				SELECT
					inpd.id AS inpdId,
					param.parameter_code AS parameterCode,
					param.description,
					pack.pack_type AS packTypeName,
					param.parameter_length AS parameterLength,
					inpd.parameter_value AS parameterValue,
					param.id AS terminalParameterIdPk
				FROM mst_individual_parameter_h inph
				INNER JOIN mst_individual_parameter_version ver
				ON ver.individual_parameter_h_id = inph.id
				INNER JOIN mst_individual_parameter_d inpd
				ON inpd.individual_parameter_h_id = inph.id
				INNER JOIN mst_terminal_parameter param
				ON inpd.parameter_id = param.id
				INNER JOIN mst_pack_type pack
				ON param.pack_type_id = pack.id
				WHERE
					( inph.terminal_id = :fromTerminalId )
				AND
					ver.parameter_version = (
												SELECT ver2.parameter_version 
												FROM mst_individual_parameter_h inph2 
												INNER JOIN mst_individual_parameter_version ver2 
												ON ver2.individual_parameter_h_id = inph2.id 
												WHERE
													( inph2.terminal_id = :fromTerminalId )
												ORDER BY ver2.parameter_version DESC LIMIT 1
											)
				""";
		
	@Query(value = searchCopyParameterIndividualParameter, nativeQuery = true)
	List<Object[]> searchCopyParameterIndividualParameter(@Param("fromTerminalId") Integer fromTerminalId);

//	final String columnBase = """
//			select
//				h.id,
//				h.enterprise_id,
//				h.active_flag,
//				h.start_date,
//				h.application_status,
//				h.reject_reason,
//				h.approve_by_id,
//				h.approve_date,
//				h.action_form,
//				h.created_by_id,
//				h.created_date,
//				h.updated_by_id,
//				h.updated_date
//				
//				""";
//	
//	String querySearchH = columnBase + """
//			,d.id as commonDId
//			,d.parameter_id
//			,v.id as commonVersionId
//			
//		    From mst_common_parameter_h h
//		    Left join enterprise e on h.enterprise_id = e.id and e.operation_flag=true
//		    Left join mst_common_parameter_d d on d.common_parameter_h_id = h.id
//		    Left join mst_common_parameter_version v on v.common_parameter_h_id = h.id
//		    Where d.parameter_id =:parameterId
//		    
//		""";
//
//
//	@Query(value = querySearchH, nativeQuery = true)
//	List<Object[]> searchCommonParameterHByParameterId(@Param("parameterId") Integer parameterId);
	
	final String columnBase = """
			select
				h.id,
				h.merchant_id,
				h.terminal_id,
				h.from_terminal_id,
				h.active_flag,
				h.start_date,
				h.application_status,
				h.reject_reason,
				h.approve_by_id,
				h.approve_date,
				h.action_form,
				h.created_by_id,
				h.created_date,
				h.updated_by_id,
				h.updated_date,
				h.batch_flag
				
				""";
	
	String querySearchIndividualH = columnBase + """
			,d.id as individualDId
			,d.parameter_id
			,v.id as individualVersionId
			
		    From  mst_individual_parameter_h h
			Left join terminal t on t.id = h.terminal_id and t.operation_flag is true
			Left join mst_individual_parameter_d d on d.individual_parameter_h_id = h.id
			Left join mst_individual_parameter_version v on v.individual_parameter_h_id = h.id
			Where d.parameter_id = :parameterId
		    
		""";


	@Query(value = querySearchIndividualH, nativeQuery = true)
	List<Object[]> searchIndividualParameterHByParameterId(@Param("parameterId") Integer parameterId);
	
	
	final String dtoColumn = """ 
			new com.cs.BatchAutoGenerateParameter.dto.IndividualParameterHDto(	
			h.id,
			h.merchantId,
			h.terminalId,
			h.fromTerminalId,
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
			+ " FROM IndividualParameterH h where h.id = :id ")
	IndividualParameterHDto findDtoById(@Param("id") Integer id);
	
	@Modifying
	@Transactional
	@Query("""
	    UPDATE IndividualParameterH h 
	    SET h.batchFlag = true, h.updatedDate = CURRENT_TIMESTAMP , h.updatedById=:updatedById
	    WHERE h.id IN :ids
	""")
	int updateBatchFlag(@Param("ids") List<Integer> ids, int updatedById);

}
