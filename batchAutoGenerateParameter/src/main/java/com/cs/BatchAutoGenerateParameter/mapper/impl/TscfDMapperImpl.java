package com.cs.BatchAutoGenerateParameter.mapper.impl;


import org.springframework.stereotype.Component;

import com.cs.BatchAutoGenerateParameter.dto.TscfDDto;
import com.cs.BatchAutoGenerateParameter.entities.TscfD;
import com.cs.BatchAutoGenerateParameter.mapper.ITscfDMapper;


@Component
public class TscfDMapperImpl implements ITscfDMapper{
	
	@Override
	public TscfDDto toDto(TscfD entity) {
		return new TscfDDto(entity.getId(), entity.getTscfHId(), entity.getTscfTag(), entity.getTerminalId(), entity.getIsRequest(),
				entity.getRequestDatetime(), entity.getIsSuccess(), entity.getCreatedById(), entity.getCreatedDate(), entity.getSuccessDateTime(), entity.getTscfJson());
	}

	@Override
	public TscfD toEntity(TscfDDto dto) {
		return new TscfD(dto.getId(), dto.getTscfHId(), dto.getTscfTag(), dto.getTerminalId(), dto.getIsRequest(),
				dto.getRequestDatetime(), dto.getIsSuccess(), dto.getCreatedById(), dto.getCreatedDate(), dto.getSuccessDateTime(), dto.getTscfJson());
	}

}
