package com.app.employeePortal.image.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.app.employeePortal.document.entity.DocumentDetails;
import com.app.employeePortal.document.service.DocumentService;
import com.app.employeePortal.image.entity.Images;
import com.app.employeePortal.image.repository.ImageRepository;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.models.BlobStorageException;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

	@Autowired
	ImageRepository imageRepository;
	@Autowired
	private AmazonS3 amazonS3;

	@Value("${bucketName}")
	private String s3bucket;
	
	@Value("${azure.storage.account-name}")
    private String accountName;

    @Value("${azure.storage.account-key}")
    private String accountKey;

    @Value("${azure.storage.blob-endpoint}")
    private String blobEndpoint;
    
    private final String containerName = "docsupload";
	
	@Autowired
	DocumentService documentService;
	
	@Override
	public String saveUploadedFiles(List<MultipartFile> list, String userId, String orgId) {
		String imageId = null;
		for (MultipartFile file : list) {
			if (file.isEmpty()) {
				continue; 
			}
			
			try {
				
				if (file.getOriginalFilename().trim() != null) {
					String fileName = file.getOriginalFilename();
					File actualfile = documentService.convertMultiPartFileToFile(file);
					String path = orgId + "/" + userId + "/" + fileName;
					/*for aws s3 */
					documentService.uploadFile(s3bucket, path, actualfile);
					/*for Azure blob*/
//					documentService.uploadFileForAzureBlob(path, actualfile);
					
				    Images images = new Images();
				    images.setCreation_date(new Date());
				    images.setImage_name(fileName);
				    images.setImage_size(file.getSize());
				    images.setImage_type(file.getContentType());
//				    images.setImage_data(file.getBytes());
				    images.setImage_path(path);
				    images.setLive_ind(true);
				    imageId = imageRepository.save(images).getImage_id();
				    actualfile.delete();	
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
		}
		return imageId;
	}

	@Override
	public Images getImageByImageId(String imageId) {
      Images images = imageRepository.getById(imageId);
	return images;
		
//		return images.getImage_data();
	}
	
//	/*Getting file from Azure Blob*/
//	@Override
//    public void downloadImageFromCloud(Images img, HttpServletResponse response) {
//        String folderPath = img.getImage_path();
//        String fileName = img.getImage_name();
//
//        BlobClient blobClient = new BlobClientBuilder()
//                .connectionString("DefaultEndpointsProtocol=https;AccountName=" + accountName + ";AccountKey=" + accountKey + ";EndpointSuffix=core.windows.net")
//                .containerName(containerName)
//                .blobName(folderPath)
//                .buildClient();
//
//        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
//            blobClient.download(outputStream);
//            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
//            response.setContentType(blobClient.getProperties().getContentType());
//            StreamUtils.copy(outputStream.toByteArray(), response.getOutputStream());
//            response.flushBuffer();
//        } catch (BlobStorageException | IOException e) {
//            e.printStackTrace();
//        }
//    }	    

	/*Getting file from Aws s3*/
	@Override
    public void downloadImageFromCloud(Images img, HttpServletResponse response) {
        String folderPath = img.getImage_path();
        String fileName = img.getImage_name();

	  	S3Object s3object = amazonS3.getObject(s3bucket, folderPath);
		S3ObjectInputStream inputStream = s3object.getObjectContent();
		try {
			response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
			response.setContentType(img.getImage_type());
			if (null != img) {
//				byte[] buffer = doc.getDocument_data();
				IOUtils.copy(inputStream, response.getOutputStream());
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	   
	}

}
