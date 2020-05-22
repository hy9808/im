package com.yj.im.project.util.RSA;

import com.alibaba.fastjson.JSON;
import com.yj.im.project.entity.pojo.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.LocalDateTime;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.UUID;

public class JwtUtils {

    private static final String JWT_PAYLOAD_USER_KEY = "user";

    /**
     * 私钥加密token
     *
     * @param userInfo   载荷中的数据
     * @param privateKey 私钥
     * @param expire     过期时间，单位分钟
     * @return JWT
     */
    public static String generateTokenExpireInMinutes(Object userInfo, PrivateKey privateKey, int expire) {
        return Jwts.builder()
                .claim(JWT_PAYLOAD_USER_KEY, JSON.toJSONString(userInfo))
                .setId(createJTI())
                .setExpiration(TimeUtils.addTime(LocalDateTime.now(), expire))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    /**
     * 私钥加密token
     *
     * @param userInfo   载荷中的数据
     * @param privateKey 私钥
     * @param expire     过期时间，单位秒
     * @return JWT
     */
    public static String generateTokenExpireInSeconds(Object userInfo, PrivateKey privateKey, int expire) {
        return Jwts.builder()
                .claim(JWT_PAYLOAD_USER_KEY, JSON.toJSONString(userInfo))
                .setId(createJTI())
                .setExpiration(TimeUtils.addTime(LocalDateTime.now(), expire))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();


    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥
     * @return Jws<Claims>
     */
    private static Jws<Claims> parserToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }

    private static String createJTI() {
        return new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
    }

    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     */
    public static <T> Payload<T> getInfoFromToken(String token, PublicKey publicKey, Class<T> userType) {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        Payload<T> claims = new Payload<>();
        claims.setId(body.getId());
        claims.setUserInfo(JSON.parseObject(body.get(JWT_PAYLOAD_USER_KEY).toString(), userType));
        claims.setExpiration(body.getExpiration());
        return claims;
    }

    /**
     * 获取token中的载荷信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     */
    public static <T> Payload<T> getInfoFromToken(String token, PublicKey publicKey) {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        Payload<T> claims = new Payload<>();
        claims.setId(body.getId());
        claims.setExpiration(body.getExpiration());
        return claims;
    }

    /**
     * 从 request中获取认证信息
     */
    public static JwtUser getUserInfo(HttpServletRequest request, PublickKeyDo prop) {

        // 取用户token 获取用户ID
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");
        Payload<JwtUser> userInfo = JwtUtils.getInfoFromToken(token, prop.getPublicKey(), JwtUser.class);
        return userInfo.getUserInfo();
    }


    /**
     * 从 request中获取认证信息
     */
    public static JwtUser getUserInfo() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 取用户token 获取用户ID
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        PublickKeyDo prop = new PublickKeyDo();
        try {
            prop.createRsaKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Payload<JwtUser> userInfo = JwtUtils.getInfoFromToken(token, prop.getPublicKey(), JwtUser.class);
        return userInfo.getUserInfo();
    }

    /**
     * 从token中获取信息
     */
    public static JwtUser getUserInfoByToken(String token) {
        // 取用户token 获取用户ID
        PublickKeyDo prop = new PublickKeyDo();
        token = token.replace("Bearer ", "");
        try {
            prop.createRsaKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Payload<JwtUser> userInfo = JwtUtils.getInfoFromToken(token, prop.getPublicKey(), JwtUser.class);
        return userInfo.getUserInfo();
    }

}