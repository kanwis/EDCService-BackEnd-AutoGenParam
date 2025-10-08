package com.cs.BatchAutoGenerateParameter.dao;

import java.util.List;

import com.cs.BatchAutoGenerateParameter.dto.CommonParameterHDto;

public interface ICommonParameterHDao {
	public CommonParameterHDto findDtoById(int id) throws Exception;
	public void save(CommonParameterHDto dto) throws Exception;
	public void saveList(List<CommonParameterHDto> list) throws Exception;
	public void updateBatchFlag(List<Integer> ids, int updatedById) throws Exception;
}
