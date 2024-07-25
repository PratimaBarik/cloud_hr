package com.app.employeePortal.Language.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="words")
public class Words {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="english")
    private String english;

    @Column(name="Dutch")
    private String dutch;

    @Column(name="french")
    private String french;

    @Column(name="italian")
    private String italian;

    @Column(name="spanish")
    private String spanish;

    @Column(name="german")
    private String german;

    @Column(name="polish")
    private String polish;

    @Column(name="arabic")
    private String arabic;

    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="live_ind",nullable = false)
    private boolean liveInd;
}
