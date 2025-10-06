package com.cs.BatchAutoGenerateParameter.dao;


import java.util.List;

import com.cs.BatchAutoGenerateParameter.dto.IndividualGenerateDetailInfo;
import com.cs.BatchAutoGenerateParameter.dto.IndividualGenerateInfo;

public interface IIndividualParameterDDao {
	
	public List<IndividualGenerateDetailInfo> searchIndividualDetailByTerminalId(Integer terminalId) throws Exception;
	public List<IndividualGenerateInfo> searchGenerateIndividualHByTerminalId(Integer terminalId) throws Exception;
	public List<IndividualGenerateDetailInfo> searchGeneratIndividualDetail(int parameterHId, int parameterVersionId) throws Exception;
	
}
