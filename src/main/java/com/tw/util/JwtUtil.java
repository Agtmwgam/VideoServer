package com.tw.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tw.entity.vUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haizhi on 2019/5/16.
 */
public class JwtUtil {

    private static final String SECRET = "shuangkon";

    /**
     * 生成token方法
     *
     * @param vUser 用户信息
     * @return
     */
    public static String encode(vUser vUser){

        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        // 头部信息
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        Date nowDate = new Date();
//        Date expireDate = getAfterDate(nowDate, 0, 0, 0, 2, 0, 0);  // 2小时过期

        String token = JWT.create()
                /* 设置头部信息 Header */
                .withHeader(map)
                /* 设置载荷Payload */
                // 用户ID
                .withClaim("userId", vUser.getUserID())
                // 用户昵称
                .withClaim("nickName", vUser.getNickName())
                // 手机号
                .withClaim("phone", vUser.getPhoneNumber())
                .withIssuer("SERVICE")  // 签名是由谁生成,例如 服务器
//                .withSubject("this is test token")  // 签名的主题
//                .withNotBefore(new Date())  // 定义在什么时间之前,该jwt都是不可用的
                .withAudience(vUser.getUserID())  // 签名的观众(谁接受的签名)
                .withIssuedAt(nowDate)  // 生成签名的时间
//                .withExpiresAt(expireDate)  // 签名过期的时间
                /* 签名 Signature */
                .sign(algorithm);
        return token;

    }


    /**
     * 解码token,获取userid
     *
     * @param token
     * @return userId
     */
    public static Map<String, Claim> decode(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("SERVICE")
                .build();  // Reusable verifier instance
        DecodedJWT jwt = verifier.verify(token);
//        String subject = jwt.getSubject();  // 获取token签名主题
        Map<String, Claim> claims = jwt.getClaims();  // 获取token的内容

        List<String> audience = jwt.getAudience();
//        System.out.println(audience.get(0));

        return claims;
    }

    public static Map<String, Claim> getToken(HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        Map<String, Claim> claimMap = decode(token);

        return claimMap;
    }
}