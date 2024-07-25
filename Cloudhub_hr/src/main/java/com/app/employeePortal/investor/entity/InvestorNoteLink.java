package com.app.employeePortal.investor.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "investor_notes_link")
public class InvestorNoteLink {
    @Id
    @GenericGenerator(name = "investor_notes_link_id", strategy = "com.app.employeePortal.investor.generator.InvestorNotesLinkGenerator")
    @GeneratedValue(generator = "investor_notes_link_id")

    @Column(name="investor_notes_link_id")
    private String id;

    @Column(name="investor_id")
    private String investorId;

    @Column(name="note_id")
    private String noteId;

    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="live_ind", nullable =false)
	private boolean liveInd = true;
}
