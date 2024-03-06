package com.schfoo.force.helper.util;

import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

public class JwtUtil {

    public static String generate(
            String jwtSecret, Date jwtExpiration, String subject, @Nullable Map<String, Object> claim) {
        Instant instant = jwtExpiration.toInstant();
        ZoneId zoneId = TimeZone.getDefault().toZoneId();
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId);

        Signer signer = HMACSigner.newSHA256Signer(jwtSecret);
        JWT jwt = new JWT().setSubject(subject)
                .setExpiration(zonedDateTime);

        if (claim != null) {
            claim.forEach(jwt::addClaim);
        }

        return JWT.getEncoder().encode(jwt, signer);
    }

    public static String getSubject(String jwtSecret, String encodedJwt, boolean rollbackIfError) {
        Verifier verifier = HMACVerifier.newVerifier(jwtSecret);
        try {
            JWT jwt = JWT.getDecoder().decode(encodedJwt, verifier);
            return jwt.subject;
        } catch (Exception e) {
            //e.printStackTrace();
            throw ExceptionUtil.thr("invalid token", rollbackIfError);
        }
    }

    public static Map<String, Object> getClaim(String jwtSecret, String encodedJwt) {
        Verifier verifier = HMACVerifier.newVerifier(jwtSecret);
        JWT jwt = JWT.getDecoder().decode(encodedJwt, verifier);
        return jwt.getAllClaims();
    }

    public static boolean isValid(String jwtSecret, String encodedJwt) {
        Verifier verifier = HMACVerifier.newVerifier(jwtSecret);
        try {
            JWT.getDecoder().decode(encodedJwt, verifier);
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
        return true;
    }

}
