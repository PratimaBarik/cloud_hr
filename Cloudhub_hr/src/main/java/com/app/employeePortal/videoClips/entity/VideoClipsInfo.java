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
    @Table(name="video_clips_info")
    public class VideoClipsInfo {
        

        @Id
        @GenericGenerator(name = "videoClips_id", strategy = "com.app.employeePortal.videoClips.generator.VideoClipsInfoGenerator")
        @GeneratedValue(generator = "videoClips_id")
        
        @Column(name="video_clips_id")
        private String videoClipsId;
        
        @Column(name="creation_date")
        private Date creationDate;
        
        @Column(name="creator_id")
        private String creatorId;

        

        
    

}
