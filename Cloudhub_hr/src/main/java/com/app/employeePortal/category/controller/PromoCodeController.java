package com.app.employeePortal.category.controller;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.category.mapper.PromoCodeRequestMapper;
import com.app.employeePortal.category.service.PromoCodeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/promocode")
public class PromoCodeController {
    @Autowired
    PromoCodeRequestService promoCodeRequestService;
    @Autowired
    private TokenProvider jwtTokenUtil;
    @PostMapping("/save")
    public ResponseEntity<?> savePromoCode(@RequestHeader("Authorization") String authorization, HttpServletRequest request, @RequestBody PromoCodeRequestMapper promoCodeRequestMapper) {

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            String authToken = authorization.replace(TOKEN_PREFIX, "");
            promoCodeRequestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
            promoCodeRequestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
            return  ResponseEntity.ok(promoCodeRequestService.savePromoCode(promoCodeRequestMapper));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllPromoCode(@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            return  ResponseEntity.ok(promoCodeRequestService.getAllPromoCode());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
