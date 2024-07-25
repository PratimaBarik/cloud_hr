package com.app.employeePortal.notification.mapper;
import java.util.List;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationConfigRequest {
	
	@NotNull
	@JsonProperty("name")
	private String name;
	
	@NotNull
	@JsonProperty("type")
	private List<String> type;
	
	@JsonProperty("reportingManager")
	private boolean reportingManager;
	
	@JsonProperty("reportingManager1")
	private boolean reportingManager1;
	
	@JsonProperty("admin")
	private boolean admin;
	
}
