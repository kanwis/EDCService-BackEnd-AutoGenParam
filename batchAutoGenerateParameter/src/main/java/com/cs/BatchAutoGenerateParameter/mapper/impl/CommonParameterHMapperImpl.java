package com.cs.BatchAutoGenerateParameter.mapper.impl;

import org.springframework.stereotype.Component;

import com.cs.BatchAutoGenerateParameter.dto.CommonParameterHDto;
import com.cs.BatchAutoGenerateParameter.entities.CommonParameterH;
import com.cs.BatchAutoGenerateParameter.mapper.ICommonParameterHMapper;

@Component
public class CommonParameterHMapperImpl implements ICommonParameterHMapper{
	
	@Override
	public CommonParameterHDto toDto(CommonParameterH entity) {
		return new CommonParameterHDto(entity.getId(), entity.getEnterpriseId(), entity.isActiveFlag(), entity.getStartDate(),
				entity.getApplicationStatus(), entity.getRejectReason(), entity.getApproveById(), entity.getApproveDate(), 
				entity.getActionForm(), entity.getCreatedById(), entity.getCreatedDate(), entity.getUpdatedById(), entity.getUpdatedDate(), entity.isBatchFlag());
	}

	@Override
	public CommonParameterH toEntity(CommonParameterHDto dto) {
		return new CommonParameterH(dto.getId(), dto.getEnterpriseId(), dto.isActiveFlag(), dto.getStartDate(),
				dto.getApplicationStatus(), dto.getRejectReason(), dto.getApproveById(), dto.getApproveDate(), 
				dto.getActionForm(), dto.getCreatedById(), dto.getCreatedDate(), dto.getUpdatedById(), dto.getUpdatedDate(), dto.isBatchFlag());
	}

}
