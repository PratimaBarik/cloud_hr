package com.app.employeePortal.image.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.image.entity.Images;
import com.app.employeePortal.image.service.ImageService;


@RestController
@CrossOrigin(maxAge = 3600)

public class ImageController {

	@Autowired 
	ImageService imageService;
	@Autowired
    private TokenProvider jwtTokenUtil;
	@Autowired
    private AmazonS3 amazonS3;
	
	@Value("${bucketName}")
	private String s3bucket;
	
	@PostMapping("/api/v1/image")
	public ResponseEntity<?> uploadFile(@RequestParam("image") MultipartFile uploadImage,
			                            HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		String imageId = null;
				
		  if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			  String authToken = authorization.replace(TOKEN_PREFIX, "");
			  
		if (uploadImage.isEmpty()) {
			return new ResponseEntity<String>("please select a image!", HttpStatus.OK);
		}

		try {
			/** File will get saved to file system and database */
		 imageId =	imageService.saveUploadedFiles(Arrays.asList(uploadImage),jwtTokenUtil.getUserIdFromToken(authToken), jwtTokenUtil.getOrgIdFromToken(authToken));
			return new ResponseEntity<String>(imageId,new HttpHeaders(), HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}


	  }
	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
//	/* view image by imageId*/
//	@GetMapping("/api/v1/image/{imageId}")
//	public void downloadFile3(@PathVariable("imageId") String imageId,HttpServletResponse response){
//	      
//		response.setContentType("image/jpeg");
//	     
//		Images images = imageService.getImageByImageId(imageId);
//		String folderPath = images.getImage_path();
////	  	String fileName = images.getImage_name();
//	  	S3Object s3object = amazonS3.getObject(s3bucket, folderPath);
//		S3ObjectInputStream inputStream = s3object.getObjectContent();
////		InputStream in1 = new ByteArrayInputStream(buffer);
//		try {
//			IOUtils.copy(inputStream, response.getOutputStream());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	   }
	
	/* view image by imageId*/
	@GetMapping("/api/v1/image/{imageId}")
	public void downloadFile3(@PathVariable("imageId") String imageId,HttpServletResponse response){
	      
		response.setContentType("image/jpeg");
	     
		Images images = imageService.getImageByImageId(imageId);

		if (images != null) {
			imageService.downloadImageFromCloud(images, response);
        } else {
            // Handle document not found
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
	   }

}
