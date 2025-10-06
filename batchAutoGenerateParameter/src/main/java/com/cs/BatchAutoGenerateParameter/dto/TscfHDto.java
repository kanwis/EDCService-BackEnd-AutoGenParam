package com.cs.BatchAutoGenerateParameter.dto;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TscfHDto {
	
	private Integer id;
	private String terminalParameterVersion;  
	private Integer createdById;
	private Date createdDate;

}
