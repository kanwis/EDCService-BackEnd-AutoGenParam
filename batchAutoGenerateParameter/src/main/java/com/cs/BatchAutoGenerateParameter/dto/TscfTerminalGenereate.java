package com.cs.BatchAutoGenerateParameter.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TscfTerminalGenereate {
	
	private Integer terminalIdPk;
	private String terminalId;
	private String message;

}
