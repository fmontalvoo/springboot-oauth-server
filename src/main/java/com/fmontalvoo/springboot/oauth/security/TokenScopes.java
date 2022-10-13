package com.fmontalvoo.springboot.oauth.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.fmontalvoo.springboot.commons.models.Usuario;
import com.fmontalvoo.springboot.oauth.service.IUsuarioService;

@Component
public class TokenScopes implements TokenEnhancer {

	@Autowired
	private IUsuarioService service;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> scopes = new HashMap<String, Object>();

		Usuario usuario = service.findByUsername(authentication.getName());
		scopes.put("nombre", usuario.getNombre());
		scopes.put("apellido", usuario.getApellido());
		scopes.put("email", usuario.getEmail());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(scopes);

		return accessToken;
	}

}
