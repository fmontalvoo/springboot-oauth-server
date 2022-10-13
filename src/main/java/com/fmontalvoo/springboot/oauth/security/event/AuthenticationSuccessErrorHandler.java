package com.fmontalvoo.springboot.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.fmontalvoo.springboot.commons.models.Usuario;
import com.fmontalvoo.springboot.oauth.service.IUsuarioService;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	private Logger log = LoggerFactory.getLogger(getClass());
	private final int intentos = 7;

	@Autowired
	private IUsuarioService service;

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		if (authentication.getDetails() instanceof WebAuthenticationDetails)
			return;

		UserDetails user = (UserDetails) authentication.getPrincipal();
		log.info("Inicio de sesion exitoso: ".concat(user.getUsername()));
		Usuario usuario = service.findByUsername(user.getUsername());
		usuario.setIntentos(0);
		service.update(usuario.getId(), usuario);

	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		log.error("Algo salio mal al iniciar la sesion.");
		try {
			Usuario usuario = service.findByUsername(authentication.getName());

			if (usuario.getIntentos() == null)
				usuario.setIntentos(0);

			usuario.setIntentos(usuario.getIntentos() + 1);

			if (usuario.getIntentos() >= intentos) {
				usuario.setActivo(false);
				log.warn(String.format("Usuario: %s deshabilitado por exceder el maximo de intentos de login",
						usuario.getNombre().concat(" ").concat(usuario.getApellido())));
			} else {
				log.error(String.format("Itentos restantes: %d", intentos - usuario.getIntentos()));
			}

			service.update(usuario.getId(), usuario);
		} catch (Exception e) {
			log.error("Usuario no existe");
		}
	}

}
