package com.app.employeePortal.videoClips.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.recruitment.entity.Website;
import com.app.employeePortal.recruitment.repository.WebsiteRepository;
import com.app.employeePortal.videoClips.entity.VideoClipsDetails;
import com.app.employeePortal.videoClips.service.VideoClipsService;


@RestController
@CrossOrigin(maxAge = 3600)
public class VideoClipsController {

    @Autowired
    private VideoClipsService videoClipsService;

    @Autowired
    private TokenProvider jwtTokenUtil;
    
    @Autowired
    WebsiteRepository websiteRepository;
    
    
    @PostMapping("/api/v1/videoClips/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("videoClips") MultipartFile uploadfile,
                                        @RequestHeader("Authorization") String authorization,
                                        HttpServletRequest request) {

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

            String authToken = authorization.replace(TOKEN_PREFIX, "");

            if (uploadfile.isEmpty()) {
                return new ResponseEntity<String>("please select a file!", HttpStatus.OK);
            } else if (null != uploadfile && uploadfile.getSize() > 0) {

                try {
                    // File will get saved to file system and database 

                    //File file = documentService.convertMultiPartFileToFile(uploadfile);
                    long videoClipsSize = uploadfile.getSize();

                    if (videoClipsSize <= 104857600) {
                        String videoClipsId = videoClipsService.saveUploadedFiles(Arrays.asList(uploadfile),
                                jwtTokenUtil.getUserIdFromToken(authToken), jwtTokenUtil.getOrgIdFromToken(authToken));
                        return new ResponseEntity<String>(videoClipsId, HttpStatus.OK);

                    } else {
                        return new ResponseEntity<String>("File size exceeds 10MB", HttpStatus.OK);

                    }

                } catch (Exception e) {

                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    
    /* view video by videoClipId*/
    @GetMapping("/api/v1/videoClips/{videoClipsId}")
    public void downloadVideoFile(@PathVariable("videoClipsId") String videoClipsId, 
                              HttpServletResponse response)throws IOException {

        VideoClipsDetails vio = videoClipsService.getVideoClipsDetailsByVideoClipsId(videoClipsId);
        try {
            response.setHeader("Content-Disposition", "attachment;filename=\"" + vio.getVideoClipsName() + "\"");
            response.setContentType(vio.getVideoClipsType());
            if(null!=vio) {
            byte[] buffer =  vio.getVideoClipsData();
            InputStream in1 = new ByteArrayInputStream(buffer);
            IOUtils.copy(in1, response.getOutputStream());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @PostMapping("/api/v1/videoClips/upload/website")
    public ResponseEntity<?> uploadFileInWebSite(@RequestParam("videoClips") MultipartFile uploadfile,
    		@RequestParam(value = "url", required = true)String url,HttpServletRequest request) {
    	Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {
                    if (uploadfile.isEmpty()) {
                return new ResponseEntity<String>("please select a file!", HttpStatus.OK);
            } else if (null != uploadfile && uploadfile.getSize() > 0) {

                try {
                    // File will get saved to file system and database 

                    //File file = documentService.convertMultiPartFileToFile(uploadfile);
                    long videoClipsSize = uploadfile.getSize();

                    if (videoClipsSize <= 104857600) {
                        String videoClipsId = videoClipsService.saveUploadedFiles(Arrays.asList(uploadfile),
                        		web.getUser_id(),web.getOrgId());
                        return new ResponseEntity<String>(videoClipsId, HttpStatus.OK);

                    } else {
                        return new ResponseEntity<String>("File size exceeds 10MB", HttpStatus.OK);

                    }

                } catch (Exception e) {

                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
		} else {
			map.put("website", true);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
        return null;
      }

    
}
    
/*
 * @PostMapping("/api/v1/videoClips")
 * public ResponseEntity<?> addVideoClipsType(@RequestBody VideoClipsTypeMapper
 * videoClipsTypeMapper,@RequestHeader("Authorization") String authorization,
 * HttpServletRequest request){
 * 
 * 
 * if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
 * 
 * String authToken = authorization.replace(TOKEN_PREFIX, "");
 * videoClipsTypeMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(
 * authToken));
 * videoClipsTypeMapper.setCreatorId(jwtTokenUtil.getUserIdFromToken(authToken))
 * ;
 * 
 * String videoClipsid
 * =videoClipsService.addVideoClipsType(videoClipsTypeMapper);
 * 
 * return new ResponseEntity<>(videoClipsid, HttpStatus.OK);
 * }
 * 
 * return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
 * 
 * 
 * 
 * 
 * }
 * 
 * @GetMapping("/api/v1/videoClips")
 * public ResponseEntity<?> getAllDocumentType(@RequestHeader("Authorization")
 * String authorization,
 * HttpServletRequest request) throws JsonGenerationException,
 * JsonMappingException, IOException{
 * 
 * 
 * List<VideoClipsTypeMapper> typeList = null;
 * 
 * if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
 * 
 * String authToken = authorization.replace(TOKEN_PREFIX,"");
 * typeList =
 * videoClipsService.getVideoClipsTypesByOrgId(jwtTokenUtil.getOrgIdFromToken(
 * authToken));
 * 
 * Collections.sort(typeList, ( m1, m2) ->
 * m2.getCreationDate().compareTo(m1.getCreationDate()));
 * 
 * if(null!=typeList && !typeList.isEmpty()) {
 * 
 * 
 * return new ResponseEntity<>(typeList, HttpStatus.OK);
 * }
 * 
 * }
 * return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
 * 
 * 
 * 
 * }
 */

/*
 * @PutMapping("/api/v1/videoClips")
 * 
 * public ResponseEntity<?> updatevideoClipsType( @RequestBody
 * VideoClipsTypeMapper videoClipsTypeMapper,
 * 
 * @RequestHeader("Authorization") String authorization, HttpServletRequest
 * request) {
 * 
 * if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
 * 
 * 
 * VideoClipsTypeMapper videoClipsTypeMapperr =
 * videoClipsService.updatevideoClipsType(videoClipsTypeMapper);
 * 
 * return new ResponseEntity<VideoClipsTypeMapper>(videoClipsTypeMapperr,
 * HttpStatus.OK);
 * 
 * }
 * 
 * return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
 * 
 * }
 * 
 * 
 * 
 * @DeleteMapping("/api/v1/videoClips")
 * 
 * public ResponseEntity<?>
 * deleteVideoClipsType(@PathVariable("videoClipsTypeId") String
 * videoClipsTypeId,
 * 
 * @RequestHeader("Authorization") String authorization ,
 * HttpServletRequest request){
 * 
 * 
 * if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
 * 
 * boolean b = videoClipsService.deleteVideoClipsType(videoClipsTypeId);
 * return new ResponseEntity<>(b, HttpStatus.OK);
 * 
 * } return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
 * 
 * 
 * }
 * 
 * }
 */
