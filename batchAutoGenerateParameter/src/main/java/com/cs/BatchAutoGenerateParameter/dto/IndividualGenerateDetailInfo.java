package com.cs.BatchAutoGenerateParameter.dto;


import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class IndividualGenerateDetailInfo implements Serializable{
	
	private static final long serialVersionUID = 7270567999585993885L;

	private Integer individualDId;
	private Integer individualParameterHId;
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
	private String parameterCode;
	
	private int enterpriseIdPk;
	private int merchantIdPk;

}
