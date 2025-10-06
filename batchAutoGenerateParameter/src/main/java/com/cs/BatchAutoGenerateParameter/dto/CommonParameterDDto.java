package com.cs.BatchAutoGenerateParameter.dto;


import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommonParameterDDto implements Serializable{
	
	private static final long serialVersionUID = -2555844614275338627L;
	private Integer id;
	private Integer commonParameterHId;
	private Integer parameterVersionId;
	private Integer parameterId;
	private String parameterValue;
	private Integer applicationStatus;
	private String rejectReason;
	private Integer approveById;
	private Date approveDate;
	private String actionForm;
	private Integer createdById;
	private Date createdDate;
	private Integer updatedById;
	private Date updatedDate;


}
