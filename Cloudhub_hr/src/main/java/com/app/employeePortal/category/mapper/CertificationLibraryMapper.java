package com.app.employeePortal.category.mapper;
import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificationLibraryMapper {
	
	@JsonProperty("certificationId")
	private String certificationId;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("EditInd")
	private boolean EditInd;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("updatedname")
	private String updatedname;
	
}
