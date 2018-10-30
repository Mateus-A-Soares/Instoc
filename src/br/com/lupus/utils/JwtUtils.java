package br.com.lupus.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.lupus.models.Permissao;
import br.com.lupus.models.Usuario;

/**
 * Classe auxiliar com os m�todos de auxilio a implementa��o do padr�o JWT para autentica��o
 * 
 * @author Mateus A.S
 */
public class JwtUtils {

	/**
	 * 	M�todo que extrai um token de um usu�rio, qual ser� utilizado para autenticar o usu�rio a cada requisi��o
	 *  
	 * @param usuario usuario populado com as informa��es a serem extraidas para um token de acesso
	 * @return token extraido das informa��es do usu�rio
	 * @throws IllegalArgumentException disparada quando um dos campos utilizados para criar o token (id, nome, email e permissao) est� nulo
	 * @throws JWTCreationException disparada quando os campos criados n�o podem criar um token
	 */
	public static String getToken(Usuario usuario)
			throws IllegalArgumentException, JWTCreationException, UnsupportedEncodingException {

		String token = "";
		token = JWT.create()
				// Dados gerais
				.withIssuer("~Instock!~").withIssuedAt(new Date()).withSubject("Authentic")
				// PAYLOAD
				.withClaim("id", usuario.getId())
				.withClaim("nome", usuario.getNome())
				.withClaim("email", usuario.getEmail())
				.withClaim("permissao", usuario.getPermissao().toString())
				// ASSINATURA
				// criptografia usada HMAC512
				.sign(Algorithm.HMAC512(
						"H[w=Ym`j*\".xTZ&b?-Gnw`e(\"=V.F,G5j[`U4'xfcYb@F:c9ptu,9AKeBJ3NqG4/yMjP=$8@s$TpJC:7Jax=nQ*eU5JPvzUn='Fk"));

		return token;
	}

	/**
	 * M�todo utilizado para extrair um objeto usuario de um token que foi criado por essa aplica��o
	 * 
	 * @param token token com as informa��es para criar o objeto usuario
	 * @return usuario populado com as informa��es presentes no token
	 */
	public static Usuario getUsuarioToken(String token) {
		Usuario usuario = new Usuario();
		DecodedJWT decodeJwt = JWT.decode(token);
		usuario.setId(decodeJwt.getClaim("id").asLong());
		usuario.setNome(decodeJwt.getClaim("nome").asString());
		usuario.setEmail(decodeJwt.getClaim("email").asString());
		usuario.setPermissao(Permissao.valueOf(decodeJwt.getClaim("permissao").asString()));
		return usuario;
	}

	/**
	 * M�todo que valida a assinatura utilizada no token
	 * 
	 * @param token token a ser validado
	 * @throws JWTVerificationException disparada se a assinatura for inv�lida
	 */
	public static void validarToken(String token) throws JWTVerificationException, IllegalArgumentException, UnsupportedEncodingException {
		JWT.require(Algorithm.HMAC512(
				"H[w=Ym`j*\".xTZ&b?-Gnw`e(\"=V.F,G5j[`U4'xfcYb@F:c9ptu,9AKeBJ3NqG4/yMjP=$8@s$TpJC:7Jax=nQ*eU5JPvzUn='Fk"))
				.build().verify(token);
	}

}
