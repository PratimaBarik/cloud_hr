package com.app.employeePortal.category.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Date;

@ToString
@Entity
@Getter
@Setter
@Table(name = "promoCode_details")
public class PromoCodeDetails {
    @Id
    @GenericGenerator(name = "promoCode_id", strategy = "com.app.employeePortal.category.generator.PromoCodeIdGenerator")
    @GeneratedValue(generator = "promoCode_id")

    @Column(name = "promoCode_id")
    private String promoCodeId;

    @Column(name = "promoCode_name")
    private String promoCodeName;

    @Column(name = "promoCode")
    private String promoCode;


    @Column(name = "product_ind", nullable = false)
    private boolean productInd;

    @Column(name = "material_ind", nullable = false)
    private boolean materialInd;

    @Column(name = "supplier_inventory_ind", nullable = false)
    private boolean supplInvtryInd;

    @Column(name = "discount_value",nullable = false)
    private float discountValue;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "orgId")
    private String orgId;

    @Column(name = "userId")
    private String userId;

    @Column(name = "creationDate")
    private Date creationDate;

    @Column(name = "liveInd")
    private boolean liveInd;

    @Column(name = "updationDate")
    private Date updationDate;

    @Column(name = "updatedBy")
    private String updatedBy;
}
