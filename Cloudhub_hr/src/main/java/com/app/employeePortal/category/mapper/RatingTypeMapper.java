package com.app.employeePortal.category.mapper;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class RatingTypeMapper {
    private String ratingTypeId;
    private String ratingType;
    private String orgId;
    private String userId;
    private Date creationDate;
    private boolean liveInd;
    private Date updationDate;
    private String updatedBy;

}
