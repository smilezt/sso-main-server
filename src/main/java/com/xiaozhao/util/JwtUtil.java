package com.xiaozhao.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jack
 * Create in 16:58 2018/11/21
 * Description:
 */
public class JwtUtil {
    /**
     * 创建token
     * @param name
     * @param id
     * @return
     * @throws IllegalArgumentException
     * @throws UnsupportedEncodingException
     */
    public static String createJwt(String name,Long id) throws IllegalArgumentException, UnsupportedEncodingException {
        Algorithm al = Algorithm.HMAC256("secretkey");

        String token = JWT.create()
                .withIssuer(name)
                .withSubject("SSO")
                .withClaim("userid", id)
                .withExpiresAt(new Date(System.currentTimeMillis()+360000))
                .sign(al);
        return token;

    }

    /**
     * 验证token是否合法
     * @param token
     * @return
     */
    public static boolean verifyJwt(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secretkey");
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            System.out.println("校验失败");
        }
        return false;
    }

    /**
     * 获得token信息
     * @param token
     * @return
     */
    private static Map<String, Claim> getJwt(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secretkey");
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            System.out.println("校验失败");
        }
        return new HashMap<String, Claim>();
    }

    /**
     * 根据Token获取user_id
     *
     * @param token
     * @return user_id
     */
    public static Long getAppUID(String token) {
        if(JwtUtil.verifyJwt(token)){
            Map<String, Claim> claims = getJwt(token);
            Claim user_id_claim = claims.get("userid");
            if (null == user_id_claim) {
                // token 校验失败
                return null;
            }
            return user_id_claim.asLong();
        }
        return null;
    }
}
