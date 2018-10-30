package com.interest.controller.login;

import com.interest.controller.authentication.AuthenticationTokenExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;


@Component
public class LoginSuccessHandler {

	@Resource(name = "userDetailsServiceExt")
	private UserDetailsService userDetailsService;

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	private AuthorizationServerTokenServices authorizationServerTokenServices;

	/*
	 * @Autowired private ObjectMapper objectMapper;
	 */

	public OAuth2AccessToken getAccessToken(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		//通过request拼接成OAuth2Request
		OAuth2Request auth2Request = getOAuth2Request(request);
		//获取用户信息
		UserDetails user = getUserDetails(authentication);

		//根据用户信息和用户权限构建AuthenticationToken
		AuthenticationTokenExt authenticationTokenResult = new AuthenticationTokenExt(user,
				user.getAuthorities());

		authenticationTokenResult.setDetails(authentication.getDetails());

		//构建OAuth的授权对象信息
		OAuth2Authentication auth2Authentication = new OAuth2Authentication(auth2Request, authenticationTokenResult);

		//调用authorizationServerTokenServices创建token
		OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(auth2Authentication);

		return token;
	}

	public OAuth2Request getOAuth2Request(HttpServletRequest request) throws IOException {
		String header = request.getHeader("Authorization");

		if (header == null || !header.startsWith("Basic ")) {
			throw new UnapprovedClientAuthenticationException("请求头中无client信息");
		}

		//从header中获取token的信息  [client,secret]
		String[] tokens = extractAndDecodeHeader(header);
		assert tokens.length == 2;

		String clientId = tokens[0];
		String clientSecret = tokens[1];

		//从数据库中取出存储的ClientDetails并比对
		ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

		if (clientDetails == null) {
			throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在：" + clientId);
		} else if (clientSecret != null && !clientSecret.equals(clientDetails.getClientSecret().substring(6,12))) {
			throw new UnapprovedClientAuthenticationException("clientSecret不匹配：" + clientId);
		}

		TokenRequest tokenRequest = new TokenRequest(new HashMap<>(), clientId, clientDetails.getScope(),
				"custom");

		OAuth2Request auth2Request = tokenRequest.createOAuth2Request(clientDetails);

		return auth2Request;
	}

	public UserDetails getUserDetails(Authentication authentication) {

		UserDetails user = userDetailsService.loadUserByUsername((String) authentication.getPrincipal());

		if (user == null) {
			throw new InternalAuthenticationServiceException("无法获取用户信息");
		}

		return user;
	}

	private String[] extractAndDecodeHeader(String header) throws IOException {
		//获取basic 之后的数据，前端拼接的结果
		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.getDecoder().decode(base64Token);
		} catch (IllegalArgumentException e) {
			throw new BadCredentialsException("Failed to decode basic authentication token");
		}

		String token = new String(decoded, "UTF-8");

		int delim = token.indexOf(":");

		if (delim == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		}
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}

}
