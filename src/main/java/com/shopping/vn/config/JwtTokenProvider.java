package com.shopping.vn.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.shopping.vn.entity.User;
import com.shopping.vn.repository.UserRepository;
import com.shopping.vn.utils.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {
  @Autowired
  private UserRepository userRepository;
  // Generate the token

  public String generateToken(Authentication authentication) {
    String email = authentication.getName();

    User user = userRepository.findUserByEmail(email);
    Date now = new Date(System.currentTimeMillis());

    Date expiryDate = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);

    String userId = Long.toString(user.getId());

    Map<String, Object> claims = new HashMap<>();
    claims.put("id", (Long.toString(user.getId())));
    claims.put("email", user.getEmail());
    claims.put("fullName", user.getFullName());

    return Jwts.builder().setSubject(userId).setClaims(claims).setIssuedAt(now)
        .setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
        .compact();
  }

  // Validate the token
  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token);
      return true;
    } catch (SignatureException ex) {
      log.error("Invalid JWT Signature");
    } catch (MalformedJwtException ex) {
      log.error("Invalid JWT Token");
    } catch (ExpiredJwtException ex) {
      log.error("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      log.error("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      log.error("JWT claims string is empty");
    }
    return false;
  }

  // Get user Id from token

  public Long getUserIdFromJWT(String token) {
    Claims claims =
        Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody();
    String id = (String) claims.get("id");
    return Long.parseLong(id);
  }
}
