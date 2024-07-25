package com.app.employeePortal.organization.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="invoice_to_user")
public class InvoiceToUser {
    @Id
    @GenericGenerator(name = "invoice_to_user_id", strategy = "com.app.employeePortal.organization.generator.InvoiceToUserGenerator")
    @GeneratedValue(generator = "invoice_to_user_id")

    @Column(name="invoice_to_user_id")
    private String invoiceToUserId;

    @Column(name="org_id")
    private String orgId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "updated_by")
    private String updatedBy;
}
