package com.xmu.user.filter;


//import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import com.netflix.zuul.ZuulFilter;
//import org.springframework.cloud.netflix.zuul.ZuulFilter;

/**
 * 权限验证 Filter
 * 注册和登录接口不过滤
 *
 * 验证权限需要前端在 Cookie 或 Header 中（二选一即可）设置用户的 userId 和 token
 * 因为 token 是存在 Redis 中的，Redis 的键由 userId 构成，值是 token
 * 在两个地方都没有找打 userId 或 token其中之一，就会返回 401 无权限，并给与文字提示
 */

@Component
public class PreFilter extends ZuulFilter {

	@Autowired
	private RedisTemplate redisTemplate;


	//排除过滤的 uri 地址
	private static final String LOGIN_URI = "/user/user/login";
	private static final String REGISTER_URI = "/user/user/register";

	//无权限时的提示语
	private static final String INVALID_TOKEN = "invalid token";
	private static final String INVALID_USERID = "invalid userId";

	@Override
	public String filterType() {
		return PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return PRE_DECORATION_FILTER_ORDER - 1;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();//获取http请求

//		log.info("uri:{}", request.getRequestURI());//请求的url
		//注册和登录接口不拦截，其他接口都要拦截校验 token
		if (LOGIN_URI.equals(request.getRequestURI()) ||
				REGISTER_URI.equals(request.getRequestURI())) {
			return false;
		}
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		//JWT
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();

		//token对象,有可能在请求头传递过来，也有可能是通过参数传过来，实际开发一般都是请求头方式
		String token = request.getHeader("token");

//		if (StringUtils.isEmpty((token))) {
//			token = request.getParameter("token");
//		}
//		System.out.println("页面传来的token值为：" + token);
		//登录校验逻辑  如果token为null，则直接返回客户端，而不进行下一步接口调用
		if (StringUtils.isEmpty(token)) {
			// 过滤该请求，不对其进行路由
			requestContext.setSendZuulResponse(false);
			//返回错误代码
			requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
		}
//		else{
//			verifyToken();
//		}
		return null;
	}

	/**
	 * 从 header 中读取 token 并校验
	 */
	private void readTokenFromHeader(RequestContext requestContext, HttpServletRequest request) {
		//从 header 中读取
		String headerToken = request.getHeader("token");
		if (StringUtils.isEmpty(headerToken)) {
			setUnauthorizedResponse(requestContext, INVALID_TOKEN);//token为空
		} else {
			verifyToken(requestContext, request, headerToken);
		}
	}


	/**
	 * 权限验证
	 * @param userId
	 * @param role
	 * @param wantUrl
	 * @return
	 */
	boolean authentication(Integer userId,String role,String wantUrl){
		String key = "USER_" + userId;
		String roleUrl = (String) redisTemplate.opsForValue().get(key);//得到角色能访问的url
		if(canReach(wantUrl,roleUrl)){
			return true;
		}
		else{
			return false;
		}
	}

	boolean canReach(String wantUrl,String roleUrl){
		//TODO:
		return true;
	}


	/**
	 * 从Redis中校验token
	 */
//	private void verifyToken(RequestContext requestContext, HttpServletRequest request, String token) {
//		//需要从cookie或header 中取出 userId 来校验 token 的有效性，因为每个用户对应一个token，在Redis中是以 TOKEN_userId 为键的
//		Cookie userIdCookie = CookieUtils.getCookieByName(request, "userId");
//		if (userIdCookie == null || StringUtils.isEmpty(userIdCookie.getValue())) {
//			//从header中取userId
//			String userId = request.getHeader("userId");
//			if (StringUtils.isEmpty(userId)) {
//				setUnauthorizedResponse(requestContext, INVALID_USERID);
//			} else {
//				String redisToken = stringRedisTemplate.opsForValue().get(String.format(RedisConsts.TOKEN_TEMPLATE, userId));
//				if (StringUtils.isEmpty(redisToken) || !redisToken.equals(token)) {
//					setUnauthorizedResponse(requestContext, INVALID_TOKEN);
//				}
//			}
//		} else {
//			String redisToken = stringRedisTemplate.opsForValue().get(String.format(RedisConsts.TOKEN_TEMPLATE, userIdCookie.getValue()));
//			if (StringUtils.isEmpty(redisToken) || !redisToken.equals(token)) {
//				setUnauthorizedResponse(requestContext, INVALID_TOKEN);
//			}
//		}
//	}


	/**
	 * 设置 401 无权限状态
	 */
//	private void setUnauthorizedResponse(RequestContext requestContext, String msg) {
//		requestContext.setSendZuulResponse(false);
//		requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
//
//		ResultVO vo = new ResultVO();
//		vo.setCode(401);
//		vo.setMsg(msg);
//		Gson gson = new Gson();
//		String result = gson.toJson(vo);
//
//		requestContext.setResponseBody(result);
//	}
}
