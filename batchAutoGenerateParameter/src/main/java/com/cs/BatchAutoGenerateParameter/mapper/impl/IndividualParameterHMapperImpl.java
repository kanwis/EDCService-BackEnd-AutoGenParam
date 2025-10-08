package com.cs.BatchAutoGenerateParameter.mapper.impl;

import org.springframework.stereotype.Component;

import com.cs.BatchAutoGenerateParameter.dto.IndividualParameterHDto;
import com.cs.BatchAutoGenerateParameter.entities.IndividualParameterH;
import com.cs.BatchAutoGenerateParameter.mapper.IIndividualParameterHMapper;

@Component
public class IndividualParameterHMapperImpl implements IIndividualParameterHMapper{

	@Override
	public IndividualParameterHDto toDto(IndividualParameterH entity) {
		
		return new IndividualParameterHDto(entity.getId(), entity.getMerchantId(), entity.getTerminalId(), entity.getFromTerminalId(), entity.getActiveFlag(), entity.getStartDate(),
				entity.getApplicationStatus(), entity.getRejectReason(), entity.getApproveById(), entity.getApproveDate(), 
				entity.getActionForm(), entity.getCreatedById(), entity.getCreatedDate(), entity.getUpdatedById(), entity.getUpdatedDate(), entity.isBatchFlag());
	}

	@Override
	public IndividualParameterH toEntity(IndividualParameterHDto dto) {
		
		return new IndividualParameterH(dto.getId(), dto.getMerchantId(), dto.getTerminalId(), dto.getFromTerminalId(), dto.getActiveFlag(), dto.getStartDate(),
				dto.getApplicationStatus(), dto.getRejectReason(), dto.getApproveById(), dto.getApproveDate(), 
				dto.getActionForm(), dto.getCreatedById(), dto.getCreatedDate(), dto.getUpdatedById(), dto.getUpdatedDate(), dto.isBatchFlag());
	}

}
