package com.fmontalvoo.springboot.oauth.rest.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fmontalvoo.springboot.commons.models.Usuario;

@FeignClient(name = "ms-usuarios/api/v1/usuarios")
public interface UsuarioFeignClient {

	@GetMapping("/search/find")
	public Usuario findByUsername(@RequestParam String username);

}
