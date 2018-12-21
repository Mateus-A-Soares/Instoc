package br.com.lupus.dao;

import java.util.List;


import br.com.lupus.models.Usuario;

public interface UsuarioDao extends DAO<Usuario> {
	
	public Usuario buscar(String email, String senha);
	
	public Usuario buscar(String email);
	
	public List<Usuario> buscarTodos(boolean ativo);
	
	public void atualizarSenha(Usuario usuario);
}
