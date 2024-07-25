package com.app.employeePortal.videoClips.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.videoClips.entity.VideoClipsInfo;

@Repository
public interface VideoClipsInfoRepository extends JpaRepository<VideoClipsInfo, String>{

}
