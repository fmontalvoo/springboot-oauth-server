package com.fmontalvoo.springboot.oauth.service;

import com.fmontalvoo.springboot.commons.models.Usuario;

public interface IUsuarioService {

	public Usuario findByUsername(String username);

	public Usuario update(Long id, Usuario usuario);

}
