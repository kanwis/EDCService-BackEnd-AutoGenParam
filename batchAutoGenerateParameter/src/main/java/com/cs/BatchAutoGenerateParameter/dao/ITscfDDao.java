package com.cs.BatchAutoGenerateParameter.dao;


import java.util.List;

import com.cs.BatchAutoGenerateParameter.dto.TscfDDto;


public interface ITscfDDao {
	
	public TscfDDto saveTscfD(TscfDDto save) throws Exception;
	public List<TscfDDto> saveAllTscfD(List<TscfDDto> list) throws Exception;

}
