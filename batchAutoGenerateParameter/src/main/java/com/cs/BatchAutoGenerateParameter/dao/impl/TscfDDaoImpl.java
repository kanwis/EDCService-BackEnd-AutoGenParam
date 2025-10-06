package com.cs.BatchAutoGenerateParameter.dao.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.BatchAutoGenerateParameter.dao.ITscfDDao;
import com.cs.BatchAutoGenerateParameter.dto.TscfDDto;
import com.cs.BatchAutoGenerateParameter.entities.TscfD;
import com.cs.BatchAutoGenerateParameter.mapper.ITscfDMapper;
import com.cs.BatchAutoGenerateParameter.repositories.ITscfDJPARepository;


@Service
public class TscfDDaoImpl implements ITscfDDao{
	
	@Autowired
	private ITscfDJPARepository repo;
	
	@Autowired
	private ITscfDMapper mapper;

	@Override
	public TscfDDto saveTscfD(TscfDDto dto) throws Exception {
		TscfD save = mapper.toEntity(dto);
		TscfD result = repo.save(save);
		TscfDDto resultDto = mapper.toDto(result);
		return resultDto;
	}

	@Override
	public List<TscfDDto> saveAllTscfD(List<TscfDDto> list) throws Exception {
		List<TscfDDto> returnList = null;
		if(list.size()>0) {
			List<TscfD> listSave = new ArrayList<TscfD>();
			for(TscfDDto d :list) {
				listSave.add(mapper.toEntity(d));
			}
			List<TscfD> resultList = repo.saveAll(listSave);
			if(resultList.size()>0) {
				returnList = new ArrayList<TscfDDto>();
				for(TscfD d : resultList) {
					returnList.add(mapper.toDto(d));
				}
			}
		} 
		return returnList;
	}

}
