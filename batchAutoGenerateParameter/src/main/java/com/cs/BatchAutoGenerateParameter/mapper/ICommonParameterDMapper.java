package com.cs.BatchAutoGenerateParameter.mapper;

import com.cs.BatchAutoGenerateParameter.dto.CommonParameterDDto;
import com.cs.BatchAutoGenerateParameter.entities.CommonParameterD;

public interface ICommonParameterDMapper {
	public CommonParameterDDto toDto(CommonParameterD entity);
	public CommonParameterD toEntity(CommonParameterDDto dto);
}
