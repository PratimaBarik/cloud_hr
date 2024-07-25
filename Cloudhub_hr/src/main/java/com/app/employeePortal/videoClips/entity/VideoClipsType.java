package com.app.employeePortal.videoClips.entity;


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
    @Table(name="videoClips_type")
    public class VideoClipsType {
        
        @Id
        @GenericGenerator(name = "videoClips_type_id", strategy = "com.app.employeePortal.videoClips.generator.VideoClipsInfoGenerator")
        @GeneratedValue(generator = "videoClips_type_id")
        
        @Column(name="videoClips_type_id")
        private String videoClipsTypeId;
        
        @Column(name="videoClips_type_name")
        private String videoClipsTypeName;
        
        
        @Column(name="creation_date")
        private Date creationDate;
        
        @Column(name="creator_id")
        private String creatorId;
        
        @Column(name="org_id")
        private String orgId;
        

        @Column(name="live_ind")
        private boolean liveInd;

        @Column(name="edit_ind")
        private boolean editInd;
        
        


}
