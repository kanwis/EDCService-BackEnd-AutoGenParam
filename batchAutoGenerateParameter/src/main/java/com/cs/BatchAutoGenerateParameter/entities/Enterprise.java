package com.cs.BatchAutoGenerateParameter.entities;


import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "enterprise")
@Data
@NoArgsConstructor
@ToString
public class Enterprise implements Serializable{
	
	private static final long serialVersionUID = 4854500711886679292L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "enterprise_id")
	private String enterpriseId;
	
	@Column(name = "enterprise_name_th")
	private String enterpriseNameTh;  
	
	@Column(name = "enterprise_name_en")
	private String enterpriseNameEn;
	
	@Column(name = "contact_person")
	private String contactPerson;
	
	@Column(name = "address_no")
	private String addressNo;
	
	@Column(name = "building")
	private String building; 
	
	
	@Column(name = "floor")
	private String floor;  
	
	@Column(name = "room")
	private String room; 
	
	@Column(name = "moo")
	private String moo;
	
	@Column(name = "soi")
	private String soi;
	
	@Column(name = "road")
	private String road;
	
	@Column(name = "subdistrict_id")
	private int subdistrictId;  
	
	@Column(name = "district_id")
	private int districtId;
	
	@Column(name = "province_id")
	private int provinceId; 
	
	@Column(name = "phone_no")
	private String phoneNo;  
	
	@Column(name = "mail")
	private String mail; 
	
	
	@Column(name = "operation_flag")
	private boolean operationFlag;
	
	@Column(name = "reject_reason")
	private String rejectReason;
	
	@Column(name = "application_status")
	private int applicationStatus;
	
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name = "approve_by_id")
	private Integer approveById;
	
	@Column(name = "approve_date")
	private Date approveDate;
	
	@Column(name = "action_form")
	private String actionForm;
	
	@Column(name = "created_by_id")
	private Integer createdById;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "updated_by_id")
	private Integer updatedById;
	
	@Column(name = "updated_date")
	private Date updatedDate;

	public Enterprise(Integer id, String enterpriseId, String enterpriseNameTh, String enterpriseNameEn,
			String contactPerson, String addressNo, String building, String floor, String room, String moo, String soi,
			String road, int subdistrictId, int districtId, int provinceId, String phoneNo, String mail,
			boolean operationFlag, String rejectReason, int applicationStatus, Date startDate, Date endDate,
			Integer approveById, Date approveDate, String actionForm, Integer createdById, Date createdDate,
			Integer updatedById, Date updatedDate) {
		super();
		this.id = id;
		this.enterpriseId = enterpriseId;
		this.enterpriseNameTh = enterpriseNameTh;
		this.enterpriseNameEn = enterpriseNameEn;
		this.contactPerson = contactPerson;
		this.addressNo = addressNo;
		this.building = building;
		this.floor = floor;
		this.room = room;
		this.moo = moo;
		this.soi = soi;
		this.road = road;
		this.subdistrictId = subdistrictId;
		this.districtId = districtId;
		this.provinceId = provinceId;
		this.phoneNo = phoneNo;
		this.mail = mail;
		this.operationFlag = operationFlag;
		this.rejectReason = rejectReason;
		this.applicationStatus = applicationStatus;
		this.startDate = startDate;
		this.endDate = endDate;
		this.approveById = approveById;
		this.approveDate = approveDate;
		this.actionForm = actionForm;
		this.createdById = createdById;
		this.createdDate = createdDate;
		this.updatedById = updatedById;
		this.updatedDate = updatedDate;
	}

//	@Column(name = "import_type")
//	private Integer importType;
	
	
}
