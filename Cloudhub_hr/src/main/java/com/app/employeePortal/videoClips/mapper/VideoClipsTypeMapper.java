package com.app.employeePortal.videoClips.mapper;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonPropertyOrder({"videoClipsTypeId","creatorId","videoClipsName","creationDate","userId","organizationId"})

public class VideoClipsTypeMapper {

	@JsonProperty("videoClipsTypeId")
	private String videoClipsTypeId;

	@JsonProperty("creatorId")
	private String creatorId;

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("organizationId")
	private String organizationId;

	@JsonProperty("videoClipsTypeName")
	private String videoClipsTypeName;

	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("edit_ind")
	private boolean editInd;
	
	
}
