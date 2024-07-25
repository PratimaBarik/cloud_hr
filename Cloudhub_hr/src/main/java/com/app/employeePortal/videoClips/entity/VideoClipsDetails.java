package com.app.employeePortal.videoClips.entity;

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
@Table(name = "video_clips_details")
public class VideoClipsDetails {

	@Id
	@GenericGenerator(name = "video_clips_details_id", strategy = "com.app.employeePortal.videoClips.generator.VideoClipsDetailsGenerator")
	@GeneratedValue(generator = "video_clips_details_id")

	@Column(name = "video_clips_details_id")
	private String videoClipsDetailsId;

	@Column(name = "video_clips_id")
	private String videoClipsId;

	@Column(name = "video_clips_name")
	private String videoClipsName;

	@Column(name = "video_clips_type")
	private String videoClipsType;

	@Column(name = "video_clips_size")
	private long videoClipsSize;

	@Column(name = "video_clips_path")
	private String videoClipsPath;

	@Column(name = "video_description")
	private String videoDescription;

	@Column(name = "last_update_date")
	private Date lastUpdateDate;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "creator_id")
	private String creatorId;

	@Column(name = "live_ind")
	private boolean liveInd;

	@Column(name = "video_clips_title")
	private String videoClipsTitle;

	@Column(name = "employee_id")
	private String employeeId;

	@Column(name = "org_id")
	private String orgId;

	@Column(name = "resource")
	private String resource;

	@Column(name = "video_clips_data")
	private byte[] videoClipsData;

	@Column(name = "extension_type")
	private String extensionType;

}
