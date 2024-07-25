package com.app.employeePortal.room.entity;

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
@Table(name = "room_document_link")
public class RoomDocumentLink {

	
	@Id
	@GenericGenerator(name = "room_document_link_id", strategy = "com.app.employeePortal.room.generator.RoomDocumentGenerator")
    @GeneratedValue(generator = "room_document_link_id")
	
	@Column(name="room_document_link_id")
	private String id;
	
	@Column(name="room_id")
	private String roomId;
	
	@Column(name="document_id")
	private String documentId;
	
	@Column(name="creation_date")
	private Date creationDate;

	@Column(name="share_ind", nullable =false)
    private boolean shareInd = false;
	
	@Column(name="shared_user")
	private String sharedUser;

	@Column(name="contract_Ind", nullable = false)
	private boolean contractInd =false;
}
