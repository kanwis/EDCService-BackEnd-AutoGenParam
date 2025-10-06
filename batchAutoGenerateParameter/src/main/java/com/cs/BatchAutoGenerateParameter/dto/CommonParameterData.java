package com.cs.BatchAutoGenerateParameter.dto;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class CommonParameterData {
	private Integer terminalParameterVIdPk;
	private Integer terminalParameterHIdPk;
	private Integer terminalParameterDIdPk;
	private String parameterCode;
	private Integer packTypeId;

	// เก็บเป็น String ตามฟรอนต์ แล้วไปตรวจ length ตอน service
	private String parameterLength;
	private String parameterValue;

	private String description;
	private String packTypeName;

	private Integer applicationStatus;
	private String rejectReason;

	private Integer approveById;
	private Date approveDate;

	private Integer createdById;
	private Date createdDate;
	private Integer updatedById;
	private Date updatedDate;
	private Integer tmpIdPk;
	private Integer terminalParameterIdPk;
	private String messageTitle;
	
}
