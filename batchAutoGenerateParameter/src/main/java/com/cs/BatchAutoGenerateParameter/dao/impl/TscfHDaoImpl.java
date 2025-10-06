package com.cs.BatchAutoGenerateParameter.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.BatchAutoGenerateParameter.dao.ITscfHDao;
import com.cs.BatchAutoGenerateParameter.dto.TscfHDto;
import com.cs.BatchAutoGenerateParameter.entities.TscfH;
import com.cs.BatchAutoGenerateParameter.mapper.ITscfHMapper;
import com.cs.BatchAutoGenerateParameter.repositories.ITscfHJPARepository;


@Service
public class TscfHDaoImpl implements ITscfHDao{

	@Autowired
	private ITscfHJPARepository repo;
	
	@Autowired
	private ITscfHMapper mapper;
	
	@Override
	public TscfHDto saveTscfH(TscfHDto dto) throws Exception {
		TscfH save = mapper.toEntity(dto);
		TscfH result = repo.save(save);
		TscfHDto resultDto = mapper.toDto(result);
		return resultDto;
	}

}
