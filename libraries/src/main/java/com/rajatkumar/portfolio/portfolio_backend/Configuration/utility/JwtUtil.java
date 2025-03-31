package com.rajatkumar.portfolio.portfolio_backend.Configuration.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Component
//public class JwtUtil {
//  @Value("${app.jwt.secret}")
//  private String jwtSecret;
//
//  @Value("${app.jwt.expiration}")
//  private int jwtExpiration; // in milliseconds
//
//  @Value("${app.jwt.refresh-expiration}")
//  private int refreshExpiration; // in milliseconds
//
//  public String generateToken(UserDetails userDetails) {
//    return generateToken(new HashMap<>(), userDetails);
//  }
//
//  public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
//    return Jwts.builder()
//        .setClaims(extraClaims)
//        .setSubject(userDetails.getUsername())
//        .setIssuedAt(new Date())
//        .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
//        .signWith(getSignKey(), SignatureAlgorithm.HS256)
//        .compact();
//  }
//
//  public String generateRefreshToken(UserDetails userDetails) {
//    return Jwts.builder()
//        .setSubject(userDetails.getUsername())
//        .setIssuedAt(new Date())
//        .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
//        .signWith(getSignKey(), SignatureAlgorithm.HS256)
//        .compact();
//  }
//
//  private Key getSignKey() {
//    byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
//    return Keys.hmacShaKeyFor(keyBytes);
//  }
//
//  public String extractUsername(String token) {
//    return extractClaim(token, Claims::getSubject);
//  }
//
//  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//    final Claims claims = extractAllClaims(token);
//    return claimsResolver.apply(claims);
//  }
//
//  private Claims extractAllClaims(String token) {
//    return Jwts.parser()
//        .verifyWith(getSignInKey())
//        .build()
//        .parseSignedClaims(token)
//        .getPayload();
//  }
//
//  private Key getSignInKey() {
//    byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
//    return Keys.hmacShaKeyFor(keyBytes);
//  }
//
//  public boolean isTokenValid(String token, UserDetails userDetails) {
//    final String username = extractUsername(token);
//    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//  }
//
//  private boolean isTokenExpired(String token) {
//    return extractExpiration(token).before(new Date());
//  }
//
//  private Date extractExpiration(String token) {
//    return extractClaim(token, Claims::getExpiration);
//  }
//}


//
//@Service
//public class JwtUtil {
//  @Value("${app.jwt.secret}")
//  private String secretKey;
//
//  @Value("${app.jwt.expiration}")
//  private long jwtExpiration;
//
//  @Value("${app.jwt.refresh-expiration}")
//  private int refreshExpiration; // in milliseconds
//
//  public String extractUsername(String token) {
//    return extractClaim(token, Claims::getSubject);
//  }
//
//  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//    final Claims claims = extractAllClaims(token);
//    return claimsResolver.apply(claims);
//  }
//
//  public String generateToken(UserDetails userDetails) {
//    return generateToken(new HashMap<>(), userDetails);
//  }
//
//  public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
//    return buildToken(extraClaims, userDetails, jwtExpiration);
//  }
//
//    public String generateRefreshToken(UserDetails userDetails) {
//    return Jwts.builder()
//        .setSubject(userDetails.getUsername())
//        .setIssuedAt(new Date())
//        .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
//        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//        .compact();
//  }
//
//  public long getExpirationTime() {
//    return jwtExpiration;
//  }
//
//  private String buildToken(
//      Map<String, Object> extraClaims,
//      UserDetails userDetails,
//      long expiration
//  ) {
//    return Jwts
//        .builder()
//        .setClaims(extraClaims)
//        .setSubject(userDetails.getUsername())
//        .setIssuedAt(new Date(System.currentTimeMillis()))
//        .setExpiration(new Date(System.currentTimeMillis() + expiration))
//        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//        .compact();
//  }
//
//  public boolean isTokenValid(String token, UserDetails userDetails) {
//    final String username = extractUsername(token);
//    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
//  }
//
//  private boolean isTokenExpired(String token) {
//    return extractExpiration(token).before(new Date());
//  }
//
//  private Date extractExpiration(String token) {
//    return extractClaim(token, Claims::getExpiration);
//  }
//
//  private Claims extractAllClaims(String token) {
//    return Jwts.parser()
//        .verifyWith(getSignInKey())
//        .build()
//        .parseSignedClaims(token)
//        .getPayload();
//  }
//
//  private Key getSignInKey() {
//    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//    return Keys.hmacShaKeyFor(keyBytes);
//  }
//}
@Component
public class JwtUtil {
  @Value("${app.jwt.secret}")
  private String secretKey;

  @Value("${app.jwt.expiration}")
  private long jwtExpiration;

  @Value("${app.jwt.refresh-expiration}")
  private long refreshExpiration;

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    return buildToken(extraClaims, userDetails, jwtExpiration);
  }

  public String generateRefreshToken(UserDetails userDetails) {
    return buildToken(new HashMap<>(), userDetails, refreshExpiration);
  }

  private String buildToken(
      Map<String, Object> extraClaims,
      UserDetails userDetails,
      long expiration
  ) {
    return Jwts.builder()
        .claims(extraClaims)  // New method name (not setClaims)
        .subject(userDetails.getUsername())  // New method name (not setSubject)
        .issuedAt(new Date(System.currentTimeMillis()))  // New method name (not setIssuedAt)
        .expiration(new Date(System.currentTimeMillis() + expiration))  // New method name (not setExpiration)
        .signWith(getSignInKey(), (SecureDigestAlgorithm) Jwts.SIG.HS256)  // Correct signature for 0.12.x
        .compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .verifyWith((SecretKey) getSignInKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}