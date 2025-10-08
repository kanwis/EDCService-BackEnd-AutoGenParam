package com.cs.BatchAutoGenerateParameter.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.BatchAutoGenerateParameter.dao.ICommonParameterHDao;
import com.cs.BatchAutoGenerateParameter.dto.CommonParameterHDto;
import com.cs.BatchAutoGenerateParameter.entities.CommonParameterH;
import com.cs.BatchAutoGenerateParameter.mapper.ICommonParameterHMapper;
import com.cs.BatchAutoGenerateParameter.repositories.ICommonParameterHJPARepository;

@Service
public class CommonParameterHDaoImpl implements ICommonParameterHDao{
	
	@Autowired
	private ICommonParameterHJPARepository repo;
	
	@Autowired
	private ICommonParameterHMapper mapper;
	
	@Value("${db.batchSize}")
	private String batchSize;

	@Override
	public void save(CommonParameterHDto dto) throws Exception {
		CommonParameterH save = mapper.toEntity(dto);
		repo.save(save);
//		log.info("Insert Common Parameter H successfully.");
		
	}

	@Override
	public void saveList(List<CommonParameterHDto> list) throws Exception {
		if(list.size() > 0 ) {
			List<CommonParameterH> saveList = new ArrayList<CommonParameterH>();
			for(CommonParameterHDto dto : list) {
				CommonParameterH h = mapper.toEntity(dto);
				saveList.add(h);
			}
			if(saveList.size() >0) {
				repo.saveAll(saveList);
//				log.info("Insert Common Parameter H List successfully.");
			}
		}
		
	}

	@Override
	public CommonParameterHDto findDtoById(int id) throws Exception {
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
