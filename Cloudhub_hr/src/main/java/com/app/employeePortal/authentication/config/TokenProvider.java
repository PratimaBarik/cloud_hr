package com.app.employeePortal.authentication.config;
/*import com.fokuswork.salesXL.user.service.UserService;*/

import static com.app.employeePortal.authentication.constants.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;
import static com.app.employeePortal.authentication.constants.Constants.AUTHORITIES_KEY;
import static com.app.employeePortal.authentication.constants.Constants.SIGNING_KEY;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.app.employeePortal.authentication.mapper.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.app.employeePortal.registration.service.RegistrationService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenProvider implements Serializable {
	
	
	@Autowired RegistrationService registrationService;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    
    public String getUserIdFromToken(String token) {
    	Claims claims =  getAllClaimsFromToken(token);     
    	String s= (String) claims.get("userId");      
    	return s;       
    }
    
    public String getOrgIdFromToken(String token) {
    	Claims claims =  getAllClaimsFromToken(token);     
    	String s= (String) claims.get("orgId");      
    	return s;       
    }
    
    public String getRoleFromToken(String token) {
    	Claims claims =  getAllClaimsFromToken(token);     
    	String s= (String) claims.get("scopes");      
    	return s;       
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public TokenResponse generateToken(Authentication authentication) {
    	final String authorities = authentication.getAuthorities().stream()
    	.map(GrantedAuthority::getAuthority)
    	.collect(Collectors.joining(","));


    	HashMap map = registrationService.getUserSettingsByEmail(authentication.getName());

    	String userId = (String) map.get("userId");
    	String orgId = (String) map.get("organizationId");

    	System.out.println("userId........................"+userId);
    	System.out.println("orgId........................"+orgId);

    	Claims claims = Jwts.claims().setSubject(authentication.getName());
    	claims.put("scopes", authorities);
    	claims.put("userId", userId);
    	claims.put("orgId", orgId);


        TokenResponse tokenResponse=new TokenResponse();

    	String token= Jwts.builder()
    	.setSubject(authentication.getName())
    	// .claim(AUTHORITIES_KEY, authorities)
    	.setClaims(claims)
    	.signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
    	.setIssuedAt(new Date())
    	.setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
    	.compact();
        tokenResponse.setToken(token);
        tokenResponse.setUserType((String) map.get("userType"));
        return tokenResponse;
    	}

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (
              username.equals(userDetails.getUsername())
                    && !isTokenExpired(token));
    }

    UsernamePasswordAuthenticationToken getAuthentication(final String token, final Authentication existingAuth, final UserDetails userDetails) {

        final JwtParser jwtParser = Jwts.parser().setSigningKey(SIGNING_KEY);

        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

        final Claims claims = claimsJws.getBody();

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

}
