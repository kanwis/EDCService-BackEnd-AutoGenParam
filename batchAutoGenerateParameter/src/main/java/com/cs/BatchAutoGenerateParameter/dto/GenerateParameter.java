package com.cs.BatchAutoGenerateParameter.dto;


import com.cs.BatchAutoGenerateParameter.entities.Enterprise;
import com.cs.BatchAutoGenerateParameter.entities.Terminal;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GenerateParameter {
	
//	private Integer enterpriseIdPk;
//	private Integer merchantIdPk;
//	private Integer terminalIdPk;
	private int updateById;
	
	private Enterprise enterprise;
	private Terminal terminal;

}
