package com.app.employeePortal.Language.Controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.employeePortal.Language.mapper.LanguageDTO;
import com.app.employeePortal.Language.service.LanguageService;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/language")
public class LanguageController {

	@Autowired
	LanguageService languageService; 
	
	@GetMapping("/all")
    public ResponseEntity<?> all(@RequestHeader("Authorization") String authorization) {
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            return ResponseEntity.ok(languageService.all());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

//    @GetMapping("/mandatory")
//    public ResponseEntity<?> selected() {
//        return ResponseEntity.ok(languageService.selected());
//    }
//
//    @PostMapping("/update/mandatory")
//    public ResponseEntity<?> updateMandatory(@RequestHeader("Authorization") String authorization, @RequestBody LanguageDTO language) {
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//            return ResponseEntity.ok(languageService.updateMandatory(language));
//        }
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    }

    @PostMapping("/update/base")
    public ResponseEntity<?> updateBaseLanguage(@RequestHeader("Authorization") String authorization, @RequestBody LanguageDTO language) {
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            return ResponseEntity.ok(languageService.updateBaseLanguage(language));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
	
	
}
