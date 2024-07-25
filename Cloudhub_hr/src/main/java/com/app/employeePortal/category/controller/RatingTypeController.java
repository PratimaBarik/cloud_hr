package com.app.employeePortal.category.controller;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.category.mapper.RatingTypeMapper;
import com.app.employeePortal.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

@RestController
@CrossOrigin(maxAge = 3600)
public class RatingTypeController {
    @Autowired
    private TokenProvider jwtTokenUtil;
    @Autowired
    CategoryService categoryService;

    @PostMapping("/api/v1/save/ratingTypes")
    public ResponseEntity<?> saveRatingTypes(@RequestBody RatingTypeMapper mapper,
                                           @RequestHeader("Authorization") String authorization) {

        Map map = new HashMap();
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            String authToken = authorization.replace(TOKEN_PREFIX, "");
            mapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
            mapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
            RatingTypeMapper Id = categoryService.saveRatingType(mapper);
            return new ResponseEntity<>(Id, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/api/v1/get/ratingTypes/{orgId}")
    public ResponseEntity<?> getRatingTypes(@PathVariable("orgId")String orgId,@RequestHeader("Authorization") String authorization) {

        Map map = new HashMap();
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            List<RatingTypeMapper> Id = categoryService.getRatingTypes(orgId);
            return new ResponseEntity<>(Id, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
