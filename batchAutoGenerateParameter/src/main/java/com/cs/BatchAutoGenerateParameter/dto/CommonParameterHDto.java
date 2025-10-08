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
public class CommonParameterHDto implements Serializable{

	private static final long serialVersionUID = -8007480315726947883L;
	private Integer id;
	private Integer enterpriseId;
	private boolean activeFlag;
	private Date startDate;
	private Integer applicationStatus;
	private String rejectReason;
	private Integer approveById;
	private String approveDate;
	private String actionForm;
	private Integer createdById;
	private Date createdDate;
	private Integer updatedById;
	private Date updatedDate;
	private boolean batchFlag;
}
