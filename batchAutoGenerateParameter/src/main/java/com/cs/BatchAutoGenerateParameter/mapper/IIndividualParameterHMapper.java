package com.cs.BatchAutoGenerateParameter.mapper;

import com.cs.BatchAutoGenerateParameter.dto.IndividualParameterHDto;
import com.cs.BatchAutoGenerateParameter.entities.IndividualParameterH;

public interface IIndividualParameterHMapper {
	public IndividualParameterHDto toDto(IndividualParameterH entity);
	public IndividualParameterH toEntity(IndividualParameterHDto dto);
}
