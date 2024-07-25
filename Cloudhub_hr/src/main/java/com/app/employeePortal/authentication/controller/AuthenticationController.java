package com.app.employeePortal.authentication.controller;




import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.authentication.mapper.LoginUser;
import com.app.employeePortal.authentication.mapper.TokenResponse;
import com.app.employeePortal.employee.service.EmployeeService;


@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/token")
public class AuthenticationController {

	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

   @Autowired
   EmployeeService employeeService;
    
 

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody LoginUser loginUser, HttpServletRequest request) {
    	
    

    	
    	try {
    		System.out.println("Email of=="+loginUser.getUsername()+"==received from frontend");
    		boolean b = employeeService.getEmployeeDetailsByEmailId(loginUser.getUsername());
    		if(b==false) {
    		final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUser.getUsername().toLowerCase(),
                            loginUser.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            final TokenResponse token = jwtTokenUtil.generateToken(authentication);
                       
            if (null != token) {
            	System.out.println("Generated"+token);
            	return ResponseEntity.ok(token);
			}else {
				System.out.println("Token not Generated");
       return new ResponseEntity<String>("You have entered an invalid username or password", HttpStatus.FORBIDDEN);
				
			  }
    		}else {
    			System.out.println("User has been suspended");
    			return new ResponseEntity<String>("Your account has been suspended", HttpStatus.FORBIDDEN);
    		}
        	
    		
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("catch block");
			return new ResponseEntity<String>("You have entered an invalid username or password ", HttpStatus.FORBIDDEN);
		}
		
    	

    }

    
    @RequestMapping(value = "/generate-token/otp", method = RequestMethod.POST)
    public ResponseEntity<?> registerByOtp(@RequestBody LoginUser loginUser, HttpServletRequest request) {
    	
    

    	
    	try {
    		final Authentication authentication = authenticationManager.authenticate(
    				 new UsernamePasswordAuthenticationToken(
                             loginUser.getUsername(),
                             loginUser.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            final TokenResponse token = jwtTokenUtil.generateToken(authentication);
                       
            if (null != token) {
            	
            	return ResponseEntity.ok(token);
			}else {
       return new ResponseEntity<String>("You have entered an invalid username or password", HttpStatus.FORBIDDEN);
				
			  }
    		
		} catch (Exception e) {
			e.printStackTrace();
		   	
			return new ResponseEntity<String>("You have entered an invalid username or password ", HttpStatus.FORBIDDEN);
		}
		
    	

    }

}
