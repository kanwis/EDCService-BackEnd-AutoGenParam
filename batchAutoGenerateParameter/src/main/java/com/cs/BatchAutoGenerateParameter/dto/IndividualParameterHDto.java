package com.cs.BatchAutoGenerateParameter.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualParameterHDto {
	
	private Integer id;
	private Integer merchantId;
	private Integer terminalId;
	private Integer fromTerminalId;
	private Boolean activeFlag;
	private Date startDate;
	private Integer applicationStatus;
	private String rejectReason;
	private Integer approveById;
	private Date approveDate;
	private String actionForm;
	private Integer createdById;
	private Date createdDate;
	private Integer updatedById;
	private Date updatedDate;
	private boolean batchFlag;
}
