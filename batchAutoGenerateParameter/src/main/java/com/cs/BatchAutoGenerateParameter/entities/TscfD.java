package com.cs.BatchAutoGenerateParameter.entities;


import java.util.Date;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "mst_tscf_d")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TscfD {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "tscf_h_id")
	private Integer tscfHId; 
	
	@Lob
	@Column(name = "tscf_tag")
	private byte[] tscfTag;
	
	@Column(name = "terminal_id")
	private Integer terminalId;
	
	@Column(name = "is_request")
	private Boolean isRequest;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "request_datetime")
	private Date requestDatetime;
	
	@Column(name = "is_success")
	private Boolean isSuccess;
	
	@Column(name = "created_by_id")
	private Integer createdById;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "success_datetime")
	private Date successDateTime;
	
	@Transient
	private String tscfJson;


}
