package com.cs.BatchAutoGenerateParameter.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GenerateParameterInput {
	
	private String enterpriseId;
	private String terminalId;
	private boolean generateAll;

}
