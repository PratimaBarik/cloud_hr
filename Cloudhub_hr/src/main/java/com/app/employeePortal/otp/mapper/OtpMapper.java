package com.app.employeePortal.otp.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class OtpMapper {
	
	 private String emailId;

	 private Integer otp;

}
