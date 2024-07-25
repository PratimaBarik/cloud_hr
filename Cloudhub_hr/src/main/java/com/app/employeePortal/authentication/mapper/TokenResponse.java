package com.app.employeePortal.authentication.mapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {
    private String token;
    private String userType;
}
