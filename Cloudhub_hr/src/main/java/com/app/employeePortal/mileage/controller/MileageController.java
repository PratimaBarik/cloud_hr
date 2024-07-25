package com.app.employeePortal.mileage.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.mileage.mapper.MileageMapper;
import com.app.employeePortal.mileage.mapper.MileageRateMapper;
import com.app.employeePortal.mileage.service.MileageService;
import com.app.employeePortal.voucher.mapper.VoucherMapper;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/v1")
public class MileageController {

	@Autowired  
	MileageService mileageService;
	
	@Autowired
    private TokenProvider jwtTokenUtil;
	@PostMapping("/mileage")
	public ResponseEntity<?> saveExpense( @RequestBody List<MileageMapper> mileageList ,
			                     @RequestHeader("Authorization") String authorization
			                     ,HttpServletRequest request) {
		
		
		
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
        	String authToken = authorization.replace(TOKEN_PREFIX, "");

			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			String loggedInUserOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			VoucherMapper voucherId = mileageService.saveToMileageProcess(mileageList,loggedInUserId,loggedInUserOrgId);

  			return new ResponseEntity<>(voucherId,HttpStatus.OK);

        }
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
     }
	
	@GetMapping("/mileage/{mileageId}")

	public ResponseEntity<?> fetchExpense(@PathVariable("mileageId") String mileageId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			MileageMapper mileageMapper = mileageService.getMileageRelatedDetails(mileageId);

			return new ResponseEntity<MileageMapper>(mileageMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	/* retrieve all expenses of an user */
	@GetMapping("/mileage/user/{userId}")

	public ResponseEntity<?> getMileagesByUserId(@PathVariable("userId") String userId,HttpServletRequest request,
			                                 @RequestHeader("Authorization") String authorization){
			                    
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<MileageMapper> mileageList = mileageService.getMileageDetailsListByUserId(userId);	
			Collections.sort(mileageList, (MileageMapper m1, MileageMapper m2) -> m2.getCreationDate()
 					.compareTo(m1.getCreationDate()));
        return new ResponseEntity<>(mileageList, HttpStatus.OK);
       }
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/mileage/organization/{orgId}")

	public ResponseEntity<?> getMileagesByOrgId(@PathVariable("orgId") String orgId,HttpServletRequest request,
			                                 @RequestHeader("Authorization") String authorization){
			                    
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<MileageMapper> mileageList = mileageService.getMileageDetailsListByOrganizationId(orgId);
			Collections.sort(mileageList, (MileageMapper m1, MileageMapper m2) -> m2.getCreationDate()
 					.compareTo(m1.getCreationDate()));
        return new ResponseEntity<>(mileageList, HttpStatus.OK);
       }
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("/mileage/voucher/{voucherId}")

	public ResponseEntity<?> getMileageByVoucherId(@PathVariable("voucherId") String voucherId,HttpServletRequest request,
			                                 @RequestHeader("Authorization") String authorization){
			                    
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<MileageMapper> mileageList = mileageService.getMileageListByVoucherId(voucherId);

        return new ResponseEntity<>(mileageList, HttpStatus.OK);
       }
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@PutMapping("/mileage")

	public ResponseEntity<?> updateMileage(@RequestBody MileageMapper mileageMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			MileageMapper resultMapper = mileageService.updateMileageDetails(mileageMapper);

			return new ResponseEntity<MileageMapper>(resultMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	
	@PostMapping("/mileage/rate")
	public ResponseEntity<?> saveMileageRate( @RequestBody List<MileageRateMapper> mileageRatelist,
			                     @RequestHeader("Authorization") String authorization
			                     ,HttpServletRequest request) {
		
		
		
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
        	String authToken = authorization.replace(TOKEN_PREFIX, "");

        	String userId=jwtTokenUtil.getUserIdFromToken(authToken);
        	String orgId=jwtTokenUtil.getOrgIdFromToken(authToken);
			String id = mileageService.saveMileageRate(mileageRatelist,userId,orgId);
			return new ResponseEntity<>(id, HttpStatus.OK);

        }
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
     }
	
	
	@GetMapping("/mileage/rate/{orgId}")
	public ResponseEntity<?> getMileageRate(@PathVariable("orgId") String orgId,
			                     @RequestHeader("Authorization") String authorization
			                     ,HttpServletRequest request) {
		
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
        	String authToken = authorization.replace(TOKEN_PREFIX, "");

        	List<MileageRateMapper> rateList = mileageService.getMileageRate(orgId);
        	
			return new ResponseEntity<>(rateList, HttpStatus.OK);

        }
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
     }
	
	@DeleteMapping("/mileage/delete/{mileageId}")

	public ResponseEntity<?> deleteMileage(@PathVariable("mileageId") String mileageId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String message = mileageService.deleteMileage(mileageId);
			return new ResponseEntity<>(message, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@DeleteMapping("/mileage/voucher/delete/{voucherId}")

	public ResponseEntity<?> deleteVoucher(@PathVariable("voucherId") String voucherId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String message = mileageService.deleteVoucher(voucherId);
			return new ResponseEntity<>(message, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/mileage/status/{userId}/{status}/{pageNo}")
	public ResponseEntity<?> getMileageStatusListByUserId(@PathVariable("userId") String userId,@PathVariable("status") String status,
			@PathVariable("pageNo") int pageNo,@RequestHeader("Authorization") String authorization, HttpServletRequest request){
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
				List<VoucherMapper> expenseList = mileageService.getMileageStatusListByUserId(userId,status,pageNo,pageSize);
				return new ResponseEntity<>(expenseList, HttpStatus.OK);
			
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping("/api/v1/mileage/notes")
	public ResponseEntity<?> createMileageNotes(@RequestBody NotesMapper notesMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			notesMapper.setEmployeeId(jwtTokenUtil.getUserIdFromToken(authToken));
			String id = mileageService.saveMileageNotes(notesMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/mileage/note/{mileageId}")
	public ResponseEntity<?> getNoteListByMileageId(@PathVariable("mileageId") String mileageId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<NotesMapper> notesMapper = mileageService.getNoteListByMileageId(mileageId);
			if (null != notesMapper && !notesMapper.isEmpty()) {
                notesMapper.sort((NotesMapper m1, NotesMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
                return new ResponseEntity<>(notesMapper, HttpStatus.OK);
            } else{
                return new ResponseEntity<>(notesMapper, HttpStatus.OK);
            }

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

//	@PutMapping("/api/v1/mileage/note/update/{notesId}")
//	public ResponseEntity<?> updateNoteDetails(@RequestBody NotesMapper notesMapper,
//			@PathVariable("notesId") String notesId, @RequestHeader("Authorization") String authorization,
//			HttpServletRequest request) {
//		NotesMapper notesMapperr = null;
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			notesMapperr = mileageService.updateNoteDetails(notesId, notesMapper);
//			return new ResponseEntity<>(notesMapperr, HttpStatus.OK);
//		}
//
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//	}
	
	@DeleteMapping("/api/v1/mileage/note/{notesId}")
	public ResponseEntity<?> deleteMileageNote(@PathVariable("notesId") String notesId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			mileageService.deleteMileageNotesById(notesId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

}
