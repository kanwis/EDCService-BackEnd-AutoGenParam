package com.cs.BatchAutoGenerateParameter.mapper.impl;


import org.springframework.stereotype.Component;

import com.cs.BatchAutoGenerateParameter.dto.TscfHDto;
import com.cs.BatchAutoGenerateParameter.entities.TscfH;
import com.cs.BatchAutoGenerateParameter.mapper.ITscfHMapper;


@Component
public class TscfHMapperImpl implements ITscfHMapper{

	@Override
	public TscfHDto toDto(TscfH entity) {
		return new TscfHDto(entity.getId(), entity.getTerminalParameterVersion(), entity.getCreatedById(), entity.getCreatedDate());
	}

	@Override
	public TscfH toEntity(TscfHDto dto) {
		return new TscfH(dto.getId(), dto.getTerminalParameterVersion(), dto.getCreatedById(), dto.getCreatedDate());
	}

}
