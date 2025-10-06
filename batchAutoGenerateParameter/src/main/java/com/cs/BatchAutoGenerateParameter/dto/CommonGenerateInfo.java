package com.cs.BatchAutoGenerateParameter.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonGenerateInfo {

	private Integer enterpriseIdPk;
	private Integer merchantIdPk;
	private Integer terminalIdPk;
	private Integer parameterVersion;
	private Integer commonParameterHId;
	private Integer parameterVersionId;
	private Boolean batchFlag;
	private String enterpriseId;
	private String merchantId;
	private String terminalId;
	private String enterpriseNameTh;
}
