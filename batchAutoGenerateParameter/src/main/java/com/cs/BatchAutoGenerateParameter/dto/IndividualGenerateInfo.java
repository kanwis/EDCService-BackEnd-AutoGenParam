package com.cs.BatchAutoGenerateParameter.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualGenerateInfo {

	private Integer enterpirseIdPk;
	private Integer merchantIdPk;
	private Integer terminalIdPk;
	private String parameterVersion;
	private Integer individualParameterHId;
	private Integer parameterVersionId;
	private Boolean batchFlag;
}
