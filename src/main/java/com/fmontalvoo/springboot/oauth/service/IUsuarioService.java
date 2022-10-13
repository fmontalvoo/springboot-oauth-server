package com.fmontalvoo.springboot.oauth.service;

import com.fmontalvoo.springboot.commons.models.Usuario;

public interface IUsuarioService {

	public Usuario findByUsername(String username);

}
