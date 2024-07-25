package com.app.employeePortal.investor.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
@Getter
@Setter
@Entity
@Table(name = "investor_doctype")
public class InvestorDocumentType {

    @Id
    @GenericGenerator(name = "inv_doctype_id", strategy = "com.app.employeePortal.investor.generator.InvestorDocumentTypeGenerator")
    @GeneratedValue(generator = "inv_doctype_id")

    @Column(name="inv_doctype_id")
    private String invDocTypeId;

    @Column(name="document_type_id")
    private String documentTypeId;

    @Column(name="investor_id")
    private String investorId;

    @Column(name="user_id")
    private String userId;

    @Column(name="org_id")
    private String orgId;

    @Column(name = "available_ind",nullable = false)
    private boolean availableInd=true;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "live_ind",nullable = false)
    private boolean liveInd=true;

    @Column(name="updated_by")
    private String updatedBy;

    @Column(name = "updation_date")
    private Date updationDate;

}
