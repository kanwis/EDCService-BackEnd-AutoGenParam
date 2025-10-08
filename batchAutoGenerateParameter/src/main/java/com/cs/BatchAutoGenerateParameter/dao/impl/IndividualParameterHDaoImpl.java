package com.cs.BatchAutoGenerateParameter.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.BatchAutoGenerateParameter.dao.IIndividualParameterHDao;
import com.cs.BatchAutoGenerateParameter.dto.IndividualParameterHDto;
import com.cs.BatchAutoGenerateParameter.entities.IndividualParameterH;
import com.cs.BatchAutoGenerateParameter.mapper.IIndividualParameterHMapper;
import com.cs.BatchAutoGenerateParameter.repositories.IIndividualParameterHJPARepository;

@Service
public class IndividualParameterHDaoImpl implements IIndividualParameterHDao{
	
	@Autowired
	private IIndividualParameterHJPARepository repo;
	
	@Autowired
	private IIndividualParameterHMapper mapper;
	
	
	@Value("${db.batchSize}")
	private String batchSize;

	@Override
	public void save(IndividualParameterHDto dto) throws Exception {
		IndividualParameterH save = mapper.toEntity(dto);
		repo.save(save);
	}

	@Override
	public void saveList(List<IndividualParameterHDto> list) throws Exception {
		if(list.size()>0) {
			List<IndividualParameterH> saveList = new ArrayList<IndividualParameterH>();
			for(IndividualParameterHDto i : list) {
				IndividualParameterH h = mapper.toEntity(i);
				saveList.add(h);
			}
			if(saveList.size() >0) {
				repo.saveAll(saveList);
			}
		}
	}

	@Override
	public IndividualParameterHDto findDtoById(int id) throws Exception {
		return repo.findDtoById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateBatchFlag(List<Integer> ids, int updatedById) throws Exception {
		int fetchSize = Integer.parseInt(batchSize);
	    for (int i = 0; i < ids.size(); i += fetchSize) {
	        List<Integer> subList = ids.subList(i, Math.min(i + fetchSize, ids.size()));
	        repo.updateBatchFlag(subList, updatedById);
	    }
	}

}
