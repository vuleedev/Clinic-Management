package com.hamter.authService;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public interface OAuth2Service {
	
	void loginFromOAuth2(OAuth2AuthenticationToken oauth2);
	
}
