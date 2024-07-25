package com.app.employeePortal.videoClips.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonPropertyOrder({"videoClipsId","videoClipsName", "videoClipsContentType","videoClipsDescription", "videoClipsSize", "videoClipsData", "employeeId", "organizationId","creationDate","stageId","videoClipsTitle","association"})
public class VideoClipsMapper {

	@JsonProperty("videoClipsId")
	private String videoClipsId;

	@JsonProperty("videoClipsName")
	private String videoClipsName;

	@JsonProperty("videoClipsDescription")
	private String videoClipsDescription;

	@JsonProperty("videoClipsSize")
	private long videoClipsSize;
	
	@JsonProperty("videoClipsPath")
	private String videoClipsPath;

	@JsonProperty("candidateId")
	private String candidateId;

	@JsonProperty("employeeId")
	private String employeeId;

	@JsonProperty("organizationId")
	private String organizationId;

	@JsonProperty("videoClipsTitle")
	private String videoClipsTitle;

	@JsonProperty("videoClipsType")
	private String videoClipsType;

	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("lastUpdateDate")
	private String lastUpdateDate;

	@JsonProperty("uploadedBy")
	private String uploadedBy;

	@JsonProperty("shareInd")
	private boolean shareInd;
	
	@JsonProperty("creatorId")
	private String creatorId;

	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("resource")
	private String resource;
	
	@JsonProperty("extensionType")
	private String extensionType;


}
