package com.app.employeePortal.videoClips.service;


import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.employeePortal.videoClips.entity.VideoClipsDetails;

public interface VideoClipsService {
        public String saveUploadedFiles(List<MultipartFile> files, String employeeId, String organizationId);

        public VideoClipsDetails getVideoClipsDetailsByVideoClipsId(String videoClipsId);

        public File convertMultiPartFileToFile(MultipartFile multipartFile);

      /* public String addVideoClipsType(VideoClipsTypeMapper videoClipsTypeMapper);
        
        public List<VideoClipsTypeMapper> getVideoClipsTypesByOrgId(String orgIdFromToken);

        public boolean deleteVideoClipsType(String videoClipsTypeId);

        public VideoClipsTypeMapper updatevideoClipsType(VideoClipsTypeMapper videoClipsTypeMapper);*/
    }



