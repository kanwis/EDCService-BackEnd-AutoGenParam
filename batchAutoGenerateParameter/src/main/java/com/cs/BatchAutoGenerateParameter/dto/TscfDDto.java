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
public class TscfDDto {
	
	private Integer id;
	private Integer tscfHId; 
	private byte[] tscfTag;
	private Integer terminalId;
	private Boolean isRequest;
	private Date requestDatetime;
	private Boolean isSuccess;
	private Integer createdById;
	private Date createdDate;
	private Date successDateTime;
	
	private String tscfJson;

}
