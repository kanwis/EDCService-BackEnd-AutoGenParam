package com.cs.BatchAutoGenerateParameter.entities;


import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "mst_individual_parameter_h")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class IndividualParameterH implements Serializable{

 	private static final long serialVersionUID = -4446200972074472480L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "merchant_id")
	private Integer merchantId;

	@Column(name = "terminal_id")
	private Integer terminalId;
	
	@Column(name = "from_terminal_id")
	private Integer fromTerminalId;

	@Column(name = "active_flag")
	private Boolean activeFlag;
	
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "application_status")
	private Integer applicationStatus;
	
	@Column(name = "reject_reason")
	private String rejectReason;
	
	@Column(name = "approve_by_id")
	private Integer approveById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approve_date")
	private Date approveDate;
	
	@Column(name = "action_form")
	private String actionForm;
	
	@Column(name = "created_by_id")
	private Integer createdById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "updated_by_id")
	private Integer updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_date")
	private Date updatedDate;
	
	@Column(name = "batch_flag")
	private boolean batchFlag;
	
}
