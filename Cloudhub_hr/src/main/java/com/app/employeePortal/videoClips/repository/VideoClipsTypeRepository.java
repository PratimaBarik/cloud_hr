package com.app.employeePortal.videoClips.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.document.entity.DocumentType;

@Repository
public interface VideoClipsTypeRepository extends JpaRepository<DocumentType, String> {

	//@Query(value = "select a  from VideoClipsType a  where a.videoClipsTypeId=:videoClipsTypeId")
	//public VideoClipsType getTypeDetails(@Param(value = "videoClipsTypeId") String videoClipsTypeId);

}
