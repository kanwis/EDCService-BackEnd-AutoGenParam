package com.cs.BatchAutoGenerateParameter.mapper;

import com.cs.BatchAutoGenerateParameter.dto.CommonParameterHDto;
import com.cs.BatchAutoGenerateParameter.entities.CommonParameterH;

public interface ICommonParameterHMapper {
	public CommonParameterHDto toDto(CommonParameterH entity);
	public CommonParameterH toEntity(CommonParameterHDto dto);
}
