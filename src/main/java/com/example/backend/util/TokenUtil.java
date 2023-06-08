package com.example.backend.util;
import io.jsonwebtoken.*;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;


public class TokenUtil
{
    private static final String SECRET = "rgsnsm#ldsh*ws%l&hvpm!mw@xyhndes";//私密key
    private static final Long TTL_EXPIRATION = 1000L * 60 * 30 * 2 * 24 * 4; //过期时间30分钟
    private static final String ISSUER = "pibigstar";//发行人


    public static String creatToken(Map<String,Object> params) {
        SignatureAlgorithm signature = SignatureAlgorithm.HS256;

        byte[] secretBytes = DatatypeConverter.parseBase64Binary(SECRET);
        Key secretKey = new SecretKeySpec(secretBytes, signature.getJcaName());
        Long expiration = System.currentTimeMillis() + TTL_EXPIRATION;

        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(expiration))
                .setIssuer(ISSUER)
                .setClaims(params)
                .signWith(signature,secretKey);

        return builder.compact();
    }

    public static Map<String, Object> parseToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
                    .parseClaimsJws(token).getBody();
        } catch (SignatureException | MalformedJwtException e){
            System.out.println("token解析失败");
            return null;
        } catch (ExpiredJwtException e) {
            System.out.println("token已过期");
            return null;
        }
        return claims;
    }
    public static String verifyToken(HttpServletRequest req)
    {
        String userAgent = req.getHeader("User-Agent");
        String acceptEncoding = req.getHeader("Accept-Encoding");
        String connection = req.getHeader("Connection");
        String token = req.getHeader("token");
        if(token==null||token=="")
        {
            return "-3";
        }
        Map<String, Object> tokenMap = parseToken(token);
        if(tokenMap==null)
        {
            return "-3";
        }
        String account = (String) tokenMap.get("account");
        if(userAgent.equals(tokenMap.get("User-Agent"))&&acceptEncoding.equals(tokenMap.get("Accept-Encoding"))&&connection.equals(tokenMap.get("Connection")))
        {
            return account;
        }
        //-3 token检验失败
        return "-3";
    }

}
