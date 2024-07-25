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
@Table(name = "id_proof_type")

public class IdProofType {

	@Id
	@GenericGenerator(name = "id_proof_type_id", strategy = "com.app.employeePortal.category.generator.IdProofTypeGenerator")
	@GeneratedValue(generator = "id_proof_type_id")

	@Column(name = "id_proof_type_id")
	private String idProofTypeId;

	@Column(name = "id_proof_type")
	private String idProofType;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "org_id")
	private String orgId;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "edit_ind")
	private boolean editInd;

	@Column(name = "live_ind")
	private boolean liveInd;

}
