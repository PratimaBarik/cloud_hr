package com.app.employeePortal.category.mapper;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter

public class PromoCodeRequestMapper {

    private String promoCodeId;

    private String promoCodeName;

    private String promoCode;

    private boolean productInd;

    private boolean materialInd;

    private boolean supplierInventoryInd;

    private float discountValue;

    private String startDate;

    private String endDate;

    private String orgId;

    private String userId;

    private String creationDate;

    private String createdBy;
    
    private boolean liveInd;

    private String updationDate;

    private String updatedBy;

}
