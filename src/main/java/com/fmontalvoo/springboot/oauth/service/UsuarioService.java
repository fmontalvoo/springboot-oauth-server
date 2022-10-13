package com.fmontalvoo.springboot.oauth.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fmontalvoo.springboot.commons.models.Usuario;
import com.fmontalvoo.springboot.oauth.rest.client.UsuarioFeignClient;

@Service
public class UsuarioService implements IUsuarioService, UserDetailsService {

	private final static Logger logger = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private UsuarioFeignClient client;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = client.findByUsername(username);

		if (usuario == null)
			throw new UsernameNotFoundException("No se encontro al usuario");

		List<GrantedAuthority> authorities = usuario.getRoles().stream()
				.map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
				.peek(authority -> logger.info("Rol: ".concat(authority.getAuthority()))).collect(Collectors.toList());

		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getActivo(), true, true, true,
				authorities);
	}

	@Override
	public Usuario findByUsername(String username) {
		return client.findByUsername(username);
	}

}
