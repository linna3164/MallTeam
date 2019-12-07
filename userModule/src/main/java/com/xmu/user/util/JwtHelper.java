package com.xmu.user.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.*;

public class JwtHelper {
	// 秘钥
	static final String SECRET = "X-Litemall-Token";
	// 签名是有谁生成
	static final String ISSUSER = "LITEMALL";
	// 签名的主题
	static final String SUBJECT = "this is litemall token";
	// 签名的观众
	static final String AUDIENCE = "MINIAPP";

	/**
	 * 创建一个token
	 * @param userId
	 * @param role
	 * @return
	 */
	public String createToken(Integer userId,String role){
		try {
		    Algorithm algorithm = Algorithm.HMAC256(SECRET);
		    Map<String, Object> map = new HashMap<String, Object>();
		    Date nowDate = new Date();
		    // 过期时间：3分钟
		    Date expireDate = getAfterDate(nowDate,0,0,0,2,3,0);
	        map.put("alg", "HS256");
	        map.put("typ", "JWT");
		    String token = JWT.create()
		    	// 设置头部信息 Header
		    	.withHeader(map)
		    	// 设置 载荷 Payload
		    	.withClaim("userId", userId).withClaim("role",role)
		        .withIssuer(ISSUSER)//发行人，在有效载荷中
		        .withSubject(SUBJECT)//主题，在有效载荷中
		        .withAudience(AUDIENCE)//关观众，在有效载荷中
		        // 生成签名的时间 ,在有效载荷中
		        .withIssuedAt(nowDate)
		        // 签名过期的时间 ，在有效载荷中
		        .withExpiresAt(expireDate)
		        // 签名 Signature
		        .sign(algorithm);
		    return token;
		} catch (JWTCreationException exception){
			exception.printStackTrace();
		}
		return null;
	}



	/**
	 * 验证合法性，过期30分钟内有效
	 * @param token
	 * @return
	 */
	public TokenD  validation(String token){
		try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			JWTVerifier verifier = JWT.require(algorithm).acceptExpiresAt(30*60)
					.withIssuer(ISSUSER)
					.build();
			DecodedJWT jwt = verifier.verify(token);
			Map<String, Claim> claims = jwt.getClaims();
			Integer claimId = claims.get("userId").asInt();
			String  claimRole=claims.get("role").asString();
			TokenD tokenD=new TokenD(claimId,claimRole);
			return tokenD;
		} catch (JWTVerificationException exception){//token无效
//			exception.printStackTrace();
			return null;
		}
	}

	/**
	 * 验证合法性，过期则不合法
	 * @param token
	 * @return
	 */
	public boolean  expireValidation(String token){
		try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			JWTVerifier verifier = JWT.require(algorithm).acceptExpiresAt(30*60)
					.withIssuer(ISSUSER)
					.build();
			DecodedJWT jwt = verifier.verify(token);
//			Map<String, Claim> claims = jwt.getClaims();
//			Integer claimId = claims.get("userId").asInt();
//			String  claimRole=claims.get("role").asString();
//			TokenD tokenD=new TokenD(claimId,claimRole);
			return true;
		} catch (JWTVerificationException exception){//token无效
//			exception.printStackTrace();
			return false;
		}
	}


//
//	/**
//	 *
//	 * @param token
//	 * @return
//	 */
//	public TokenD validation(String token){
//		try {
//			DecodedJWT jwt = JWT.decode(token);
//			Map<String, Claim> claims = jwt.getClaims();
//			Integer claimId = claims.get("userId").asInt();
//			String  claimRole=claims.get("role").asString();
//			TokenD tokenD=new TokenD(claimId,claimRole);
//			return  tokenD;
//		} catch (JWTDecodeException exception) {
//			//Invalid token
//			return null;
//		}
//	}



	public  Date getAfterDate(Date date, int year, int month, int day, int hour, int minute, int second){
		if(date == null){
			date = new Date();
		}
		
		Calendar cal = new GregorianCalendar();
		
		cal.setTime(date);
		if(year != 0){
			cal.add(Calendar.YEAR, year);
		}
		if(month != 0){
			cal.add(Calendar.MONTH, month);
		}
		if(day != 0){
			cal.add(Calendar.DATE, day);
		}
		if(hour != 0){
			cal.add(Calendar.HOUR_OF_DAY, hour);
		}
		if(minute != 0){
			cal.add(Calendar.MINUTE, minute);
		}
		if(second != 0){
			cal.add(Calendar.SECOND, second);
		}
		return cal.getTime();
	}
	
}
