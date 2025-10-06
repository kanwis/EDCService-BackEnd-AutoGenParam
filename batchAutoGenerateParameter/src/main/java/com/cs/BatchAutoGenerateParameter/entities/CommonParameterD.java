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
@Table(name = "mst_common_parameter_d")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommonParameterD implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -5385255679499228729L;
	
	@Id
	@Column(name="id", length = 11)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	

	@Column(name = "common_parameter_h_id")
	private Integer commonParameterHId;

	@Column(name = "parameter_version_id")
	private Integer parameterVersionId;

	@Column(name = "parameter_id")
	private Integer parameterId;

	@Column(name = "parameter_value")
	private String parameterValue;

	@Column(name = "application_status")
	private Integer applicationStatus;

	@Column(name = "reject_reason")
	private String rejectReason;

	@Column(name = "approve_by_id")
	private Integer approveById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approve_date")
	private Date approveDate;

	/** Insert, Update, Delete */
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

}
