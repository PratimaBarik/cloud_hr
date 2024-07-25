package com.app.employeePortal.category.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "id_proof_typ_del")

public class IdProofTypeDelete {

	@Id
	@GenericGenerator(name = "id_proof_typ_del_id", strategy = "com.app.employeePortal.category.generator.IdProofTypeDeleteGenerator")
	@GeneratedValue(generator = "id_proof_typ_del_id")

	@Column(name = "id_proof_typ_del_id")
	private String idProofTypDelId;
	
	@Column(name = "id_proof_type_id")
	private String idProofTypeId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "org_id")
	private String orgId;

	@Column(name = "updation_date")
	private Date updationDate;

	@Column(name = "updated_by")
	private String updatedBy;
}
