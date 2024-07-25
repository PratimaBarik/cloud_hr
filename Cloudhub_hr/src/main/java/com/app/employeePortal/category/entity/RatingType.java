package com.app.employeePortal.category.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name="rating_type")
@Getter
@Setter
public class RatingType {
    @Id
    @GenericGenerator(name = "rating_type_id", strategy = "com.app.employeePortal.category.generator.RatingTypeGenerator")
    @GeneratedValue(generator = "rating_type_id")

    @Column(name = "rating_type_id")
    private String ratingTypeId;

    @Column(name = "rating_type")
    private String ratingType;

    @Column(name = "orgId")
    private String orgId;

    @Column(name = "userId")
    private String userId;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "live_ind")
    private boolean liveInd;

    @Column(name = "updation_date")
    private Date updationDate;

    @Column(name = "updated_by")
    private String updatedBy;
}
