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
@Table(name="data_room_include_link")
public class DataRoomIncludeLink {
	
	@Id
	@GenericGenerator(name = "data_room_include_link_id", strategy = "com.app.employeePortal.room.generator.DataRoomIncludeGenerator")
	@GeneratedValue(generator = "data_room_include_link_id")
	
	@Column(name="data_room_include_link_id")
	private String dataRoomIncludeLinkId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="data_room_id")
	private String dataRoomId;
	
}
