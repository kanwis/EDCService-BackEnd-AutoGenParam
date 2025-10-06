package com.cs.BatchAutoGenerateParameter.mapper;

import com.cs.BatchAutoGenerateParameter.dto.TscfDDto;
import com.cs.BatchAutoGenerateParameter.entities.TscfD;

public interface ITscfDMapper {
	TscfDDto toDto(TscfD entity);
	TscfD toEntity(TscfDDto dto);
}
