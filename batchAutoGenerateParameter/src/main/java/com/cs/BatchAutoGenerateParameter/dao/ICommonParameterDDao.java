package com.cs.BatchAutoGenerateParameter.dao;


import java.util.List;

import com.cs.BatchAutoGenerateParameter.dto.CommonGenerateInfo;
import com.cs.BatchAutoGenerateParameter.dto.CommonGenereateDetailInfo;


public interface ICommonParameterDDao {
	
//	public CommonParameterDDto save(CommonParameterDDto comParameterDDto) throws Exception;
//	public List<CommonParameterData> findByVersionIdAndHId(Integer parameterVersionId, Integer commonParameterHId) throws Exception;
	public List<CommonGenereateDetailInfo> searchCommonDetailByTerminalId(Integer enterpriseId, Integer merchantId, Integer terminalId) throws Exception;
	public List<CommonGenerateInfo> searchGenerateCommonH(Integer enterpriseId, Integer merchantId, Integer terminalId) throws Exception;
	public List<CommonGenereateDetailInfo> searchGenerateCommonDetail(Integer parameterHId, Integer parameterVersionId) throws Exception;
//	public void saveList(List<CommonParameterDDto> comParameterDDtoList) throws Exception;
	public List<CommonGenerateInfo> searchGenerateCommonHAll() throws Exception;
	
	
}
