package com.xmu.user.util;



import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * APP登录Token的生成和解析
 *
 */
public class JWTHelp {

    /** token秘钥，请勿泄露，请勿随便修改 */
    public static final String SECRET = "poy7go/IVV7+rly0uJY9Vw==";
    /** token 过期时间: 60小时 */
    public static final int CALENDARFIELD = Calendar.HOUR;
    public static final int CALENDARINTERVAL = 60;

    /**
     * JWT生成Token.<br/>
     *
     * JWT构成: header, payload, signature
     *
     * @param userId
     *            登录成功后用户userId, 参数userId不可传空
     */
    public static String createToken(Long userId) throws Exception {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(CALENDARFIELD, CALENDARINTERVAL);
        Date expiresDate = nowTime.getTime();

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        /**
         * build token
         * param backups {iss:Service, aud:APP}
         *
         * withHeader : header
         * withClaim : payload
         * withIssuedAt : sign time
         * withExpiresAt : expire time
         * sign :signature
         */
        return JWT.create().withHeader(map)
                .withClaim("iss", "Service")
                .withClaim("aud", "APP")
                .withClaim("user_id", null == userId ? null : userId.toString())
                .withIssuedAt(iatDate)
                .withExpiresAt(expiresDate)
                .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 解密Token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        Map<String, Claim> claims = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
            claims = jwt.getClaims();
            Optional.ofNullable(claims).orElseThrow(IllegalArgumentException::new);
        } catch (Exception e) {
            // e.printStackTrace();
            // token 校验失败, 抛出Token验证非法异常
            System.out.println("IllegalArgumentException : token 校验失败");
        }
        return claims;
    }

    /**
     * 根据Token获取user_id
     *
     * @param token
     * @return user_id
     */
    public static Long getAppUID(String token) {
        Map<String, Claim> claims = verifyToken(token);
        Claim userIdClaim = claims.get("user_id");
        if (null == userIdClaim || StringUtils.isEmpty(userIdClaim.asString())) {
            // token 校验失败, 抛出Token验证非法异常
        }
        return Long.valueOf(userIdClaim.asString());
    }
    public static void main(String[] args){
        try {
            String token = createToken(1L);
            System.out.println("token ======== " + token);
            //Long appUID = getAppUID(token);
            //System.out.println("appUID ======== " + appUID);
            Map<String, Claim> claimMap = verifyToken(token);
            System.out.println("claimMap ======== " + claimMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

