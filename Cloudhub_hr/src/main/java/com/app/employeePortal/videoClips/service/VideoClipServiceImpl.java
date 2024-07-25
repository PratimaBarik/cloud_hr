package com.app.employeePortal.videoClips.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.app.employeePortal.videoClips.entity.VideoClipsDetails;
import com.app.employeePortal.videoClips.entity.VideoClipsInfo;
import com.app.employeePortal.videoClips.repository.VideoClipsDetailsRepository;
import com.app.employeePortal.videoClips.repository.VideoClipsInfoRepository;
import com.app.employeePortal.videoClips.repository.VideoClipsTypeRepository;

@Service
@Transactional
public class VideoClipServiceImpl implements VideoClipsService {

    @Autowired
    VideoClipsInfoRepository  videoClipsInfoRepository;
    
    @Autowired
    VideoClipsDetailsRepository videoClipsDetailsRepository;
    
    @Autowired
    VideoClipsTypeRepository videoClipsTypeRepository;
    

    public String saveUploadedFiles(List<MultipartFile> files, String employeeId, String organizationId) {
        String videoClipsId = null;

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            try {

                if (file.getOriginalFilename().trim() != null) {                
                    VideoClipsInfo videoClipsInfo = new VideoClipsInfo();
                    videoClipsInfo.setCreatorId(employeeId);
                    videoClipsInfo.setCreationDate(new Date());

                    VideoClipsInfo videoClipsInfoo = videoClipsInfoRepository.save(videoClipsInfo);
                    videoClipsId = videoClipsInfoo.getVideoClipsId();

                    if (null != videoClipsId) {

                        VideoClipsDetails videoClipsDetails = new VideoClipsDetails();
                        videoClipsDetails.setVideoClipsId(videoClipsId);
                        videoClipsDetails.setCreationDate(new Date());
                        videoClipsDetails.setCreatorId(employeeId);
                        videoClipsDetails.setVideoClipsSize(file.getSize());
                        videoClipsDetails.setVideoClipsName(file.getOriginalFilename());
                        videoClipsDetails.setExtensionType(file.getContentType());
                        videoClipsDetails.setVideoClipsData(file.getBytes());
                        videoClipsDetails.setEmployeeId(employeeId);
                        videoClipsDetails.setOrgId(organizationId);
                        videoClipsDetails.setLiveInd(true);
                        videoClipsDetailsRepository.save(videoClipsDetails);

                    }

                }

            } catch (Exception e) {

                e.printStackTrace();
            }

        }
        return videoClipsId;
    }

    @Override
    public VideoClipsDetails getVideoClipsDetailsByVideoClipsId(String videoClipsId) {

        VideoClipsDetails videoClipsDetails =videoClipsDetailsRepository.getVideoClipsDetailsById(videoClipsId);
        return videoClipsDetails;
    }

    @Override
    public File convertMultiPartFileToFile(MultipartFile multipartFile) {
        File file = new File(multipartFile.getOriginalFilename());
        try {
            FileOutputStream outputStream = new FileOutputStream(file) ;
            outputStream.write(multipartFile.getBytes());
            outputStream.close();
        } catch ( IOException ex) {
            System.out.println("Error converting the multi-part file to file= "+ex.getMessage());
        }
        return file;
    }

}
