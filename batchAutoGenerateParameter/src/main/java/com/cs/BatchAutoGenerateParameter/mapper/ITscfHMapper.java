package com.cs.BatchAutoGenerateParameter.mapper;

import com.cs.BatchAutoGenerateParameter.dto.TscfHDto;
import com.cs.BatchAutoGenerateParameter.entities.TscfH;

public interface ITscfHMapper {
	TscfHDto toDto(TscfH entity);
	TscfH toEntity(TscfHDto dto);

}
