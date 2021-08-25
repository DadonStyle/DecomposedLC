package com.example.gateway.Auth;

import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT util is a util class for Json Web Token
 */
@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuppressWarnings("all")
public class JWTutil {
    /**
     * Signature algorithm field - type of encryption
     */
    private String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();
    /**
     * Encoded secret key field - our private key
     */
    private String encodedSecretKey = "this+is+my+key+and+it+must+be+at+least+256+bits+long";
    /**
     * Decoded secret key field - creates our private key
     */
    private Key decodedSecretKey = new SecretKeySpec(Base64.getDecoder().decode(encodedSecretKey), signatureAlgorithm);

    /**
     * Generate token
     * this method generates our key
     *
     * @param userDetails- the user's details
     * @return Token in String
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userType", userDetails.getClientType());
        claims.put("userId", userDetails.getUserId());
        String myToken = createToken(claims, userDetails.getEmail());
        System.out.println("New token was created : " + myToken);
        return myToken;
    }

    /**
     * Create token
     * this method creates our token
     *
     * @param claims  - contains the fields which we are basing the token on
     * @param subject - contains the user email
     * @return Token in String
     */
    private String createToken(Map<String, Object> claims, String subject) {
        Instant now = Instant.now();
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .signWith(this.decodedSecretKey)
                .compact();
    }

    /**
     * Extract all claims
     * this method extracts all the claims in json
     *
     * @param token - the user's token
     * @return Claims - deconstruct the token
     * @throws TokenExpiredException throws error if something went wrong
     */
    private Claims extractAllClaims(String token) throws TokenExpiredException {
        try {
            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(this.decodedSecretKey).build();
            if (jwtParser == null) {
                throw new TokenExpiredException("Finally you came back!, please log in");
            }
            return jwtParser.parseClaimsJws(token).getBody();
        } catch (TokenExpiredException e) {
            e.getMessage();
        }
        return null;
    }

    /**
     * Extract email
     * this method extracts the user's email
     *
     * @param token - the user's token
     * @return String - the user's email
     */
    public String extractEmail(String token) throws TokenExpiredException {
        return extractAllClaims(token).getSubject();
    }


    /**
     * Is token expired
     * this method checks if the token is expired
     *
     * @param token - the user's token
     * @return boolean- if it's expired
     */
    private boolean isTokenExpired(String token) {
        try {
            extractAllClaims(token);
            return false;
        } catch (ExpiredJwtException | TokenExpiredException err) {
            token = null;
            err.getMessage();
            return true;
        }
    }

    /**
     * Validate token
     * this method checks the validation of the user's details with the token
     *
     * @param token - the user's token
     * @return boolean - if the token is valid
     * @throws TokenExpiredException - throws an error if the token is expired
     */
    public boolean validateToken(String token) throws TokenExpiredException {
        if (isTokenExpired(token)) {
            throw new TokenExpiredException("Finally you came back!,please log in");
        }
        return !isTokenExpired(token);
    }
}
