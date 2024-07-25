//package com.app.employeePortal.awsFileStorage.controller;
//
//import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.app.employeePortal.awsFileStorage.service.StorageService;
//
//@RestController
//@CrossOrigin(maxAge = 3600)
//@RequestMapping("/api/v1/aws")
//public class StorageController {
//	
//	@Autowired
//    private StorageService service;
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file,
//    		@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//
//			if (file.isEmpty()) {
//				return new ResponseEntity<String>("please select a file!", HttpStatus.OK);
//			} else if (null != file && file.getSize() > 0) {
//
//				try {
//					long fileSize = file.getSize();
//				
//					if (fileSize <= 10485760) {
//        return new ResponseEntity<>(service.uploadFile(file), HttpStatus.OK);
//					} else {
//						return new ResponseEntity<String>("File size exceeds 10MB", HttpStatus.OK);
//
//					}
//
//				} catch (Exception e) {
//
//					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//				}
//			}
//		}
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    }
//
//    @GetMapping("/download/{fileName}")
//    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
//        byte[] data = service.downloadFile(fileName);
//        ByteArrayResource resource = new ByteArrayResource(data);
//        return ResponseEntity
//                .ok()
//                .contentLength(data.length)
//                .header("Content-type", "application/octet-stream")
//                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
//                .body(resource);
//    }
//
//    @DeleteMapping("/delete/{fileName}")
//    public ResponseEntity<String> deleteFile(@RequestHeader("Authorization") String authorization, HttpServletRequest request,
//    		@PathVariable String fileName) {
//    	if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//        return new ResponseEntity<>(service.deleteFile(fileName), HttpStatus.OK);
//    }
//    	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//    }
//}
