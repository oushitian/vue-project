package com.interest.controller.login;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.interest.controller.authentication.IAuthentication;
import com.interest.controller.authentication.AuthenticationTokenExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@Autowired
	private LoginSuccessHandler loginSuccessHandler;

	@Autowired
	private IAuthentication gitHubAuthentication;

//	public static final String WEIXIN_TOKEN = "helloKitty";
//
//	public static final String WEIGUANG_FORM_MOBILE_KEY = "mobile";


	@PostMapping("/authentication/github")
	public OAuth2AccessToken loginForGithHub(HttpServletRequest request, HttpServletResponse response, @RequestParam("code") String code)
			throws IOException {

		// String code = request.getParameter(WEIXIN_CODE)
		if (code == null) {
			code = "";
		}

		code = code.trim();

		//通过第三方登录获取用户信息
		String id = gitHubAuthentication.getUserId(code);

		AuthenticationTokenExt authRequest = new AuthenticationTokenExt(id);

		authRequest.setDetails(new OAuth2AuthenticationDetails(request));

		OAuth2AccessToken oAuth2AccessToken = loginSuccessHandler.getAccessToken(request, response, authRequest);

		return oAuth2AccessToken;
	}

}
