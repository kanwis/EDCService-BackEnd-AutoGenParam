package com.cs.BatchAutoGenerateParameter.dao;

import java.util.List;

import com.cs.BatchAutoGenerateParameter.dto.IndividualParameterHDto;

public interface IIndividualParameterHDao {
	
	public IndividualParameterHDto findDtoById(int id) throws Exception;
	public void save(IndividualParameterHDto dto) throws Exception;
	public void saveList(List<IndividualParameterHDto> list) throws Exception;
	public void updateBatchFlag(List<Integer> ids, int updatedById) throws Exception;

}
