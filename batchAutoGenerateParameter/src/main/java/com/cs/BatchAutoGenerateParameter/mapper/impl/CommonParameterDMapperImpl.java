package com.cs.BatchAutoGenerateParameter.mapper.impl;

import org.springframework.stereotype.Component;

import com.cs.BatchAutoGenerateParameter.dto.CommonParameterDDto;
import com.cs.BatchAutoGenerateParameter.entities.CommonParameterD;
import com.cs.BatchAutoGenerateParameter.mapper.ICommonParameterDMapper;


@Component
public class CommonParameterDMapperImpl implements ICommonParameterDMapper{

	@Override
	public CommonParameterDDto toDto(CommonParameterD entity) {
		return new CommonParameterDDto(entity.getId(),entity.getCommonParameterHId(),
				entity.getParameterVersionId(),entity.getParameterId(),entity.getParameterValue(),
				entity.getApplicationStatus(),entity.getRejectReason(),entity.getApproveById(),
				entity.getApproveDate(),entity.getActionForm(),entity.getCreatedById(),entity.getCreatedDate()
				,entity.getUpdatedById(),entity.getUpdatedDate()
				
				);

	}

	@Override
	public CommonParameterD toEntity(CommonParameterDDto dto) {
		return new CommonParameterD(dto.getId(),dto.getCommonParameterHId(),
				dto.getParameterVersionId(),dto.getParameterId(),dto.getParameterValue(),
				dto.getApplicationStatus(),dto.getRejectReason(),dto.getApproveById(),
				dto.getApproveDate(),dto.getActionForm(),dto.getCreatedById(),dto.getCreatedDate()
				,dto.getUpdatedById(),dto.getUpdatedDate()
				
				);
	}

}
