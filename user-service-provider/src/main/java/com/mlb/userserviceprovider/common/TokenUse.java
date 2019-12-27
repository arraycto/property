package com.mlb.userserviceprovider.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mlb
 */
public class TokenUse {
    /**
     * 过期时间24小时
     */
    private static final long OVERDEU_TIME = 1440 * 60 * 1000;
    /**
     * 私钥uuid生成，确定唯一性
     */
    private static final String TOKEN_SEC_RET = "fde35b32-0f47-46be-ae2a-49bcb7ed7d7f";

    /**
     * 生成token，用户退出后消失
     *
     * @param userCode
     * @param userId
     * @return
     */
    public static String sign(String userCode, Long userId) {
        //设置过期时间
        Date date = new Date(System.currentTimeMillis() + OVERDEU_TIME);
        //token私钥加密
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SEC_RET);
        //设置头部信息
        Map<String, Object> requestHeader = new HashMap<>(2);
        requestHeader.put("type", "JWT");
        requestHeader.put("encryption", "HS256");
        long date1 = System.currentTimeMillis();

        //返回带有用户信息的签名
        return JWT.create().withHeader(requestHeader)
                .withClaim("userCode", userCode)
                .withClaim("userId", userId)
                .withClaim("Time", date1)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 验证token是否正确
     *
     * @param token
     * @return
     */
    public static boolean tokenVerify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SEC_RET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            //验证
            DecodedJWT decodedJWT = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取登陆用户token中的用户ID
     *
     * @param token
     * @return
     */
    public static Long getUserID(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaim("userId").asLong();
    }
}
