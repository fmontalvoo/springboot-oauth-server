package com.fmontalvoo.springboot.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		if (authentication.getDetails() instanceof WebAuthenticationDetails)
			return;

		UserDetails user = (UserDetails) authentication.getPrincipal();
		log.info("Inicio de sesion exitoso: ".concat(user.getUsername()));

	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		log.error("Algo salio mal al iniciar la sesion.");
	}

}
