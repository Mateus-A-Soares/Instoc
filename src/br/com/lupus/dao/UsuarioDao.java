package br.com.lupus.dao;

import java.util.List;

import br.com.lupus.models.Usuario;

public interface UsuarioDao extends DAO<Usuario> {
	
	public Usuario getEmailSenha(String email, String senha);
	
	public List<Usuario> getAtivo(boolean ativo);
}
