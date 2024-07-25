package com.app.employeePortal.image.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.app.employeePortal.image.entity.Images;

public interface ImageService {

	public String saveUploadedFiles(List<MultipartFile> list, String userId, String orgId);

	public Images getImageByImageId(String imageId);

	void downloadImageFromCloud(Images img, HttpServletResponse response);

}
