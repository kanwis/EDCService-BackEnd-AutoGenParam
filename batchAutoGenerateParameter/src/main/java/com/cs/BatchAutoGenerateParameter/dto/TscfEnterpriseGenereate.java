package com.cs.BatchAutoGenerateParameter.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TscfEnterpriseGenereate {
	
	private Integer enterpriseIdPk;
	private Integer merchantIdPk;
	private String enterpriseId;
	private String merchantId;
	private String enterpriseNameTh;
	private List<TscfTerminalGenereate> terminalIdList;
	private String message;
//	private int countTscfD;
	private List<CommonGenereateDetailInfo> comDList;
	private List<IndividualGenerateDetailInfo> inDList;


//	private String statusSaveTscfH;
//	private String statusSaveTscfD;
	
//	private TscfHDto saveH;
//	private List<TscfDDto> saveDList;
	private TscfHDto tscfHResult; 
	private List<TscfDDto> tscfDResult;

}
