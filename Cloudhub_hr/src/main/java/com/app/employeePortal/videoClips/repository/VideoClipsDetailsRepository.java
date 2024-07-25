package com.app.employeePortal.videoClips.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.videoClips.entity.VideoClipsDetails;

@Repository
public interface VideoClipsDetailsRepository extends JpaRepository<VideoClipsDetails, String>{

    @Query(value = "select a  from VideoClipsDetails a  where a.videoClipsId=:videoClipsId  and a.liveInd=true" )
    VideoClipsDetails getVideoClipsDetailsById(@Param(value="videoClipsId") String videoClipsId);

    @Query(value = "select a  from VideoClipsDetails a  where a.videoClipsId=:videoClipsId " )
    VideoClipsDetails getVideoClipsDetailsByIdWithOutLiveInd(@Param(value="videoClipsId") String videoClipsId);

    

}
