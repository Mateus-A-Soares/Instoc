package br.com.lupus.models;

import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * <h1>Usuario</h1>
 * <p>
 * Classe modelo representativa da entidade Usuario
 * </p>
 * <p>
 * Utilizada tamb�m para autentica��o realizada pelo Spring Security atrav�s da
 * implementa��o da interface Authentication
 * </p>
 * 
 * @author Mateus A.S
 */
@Entity
@Table(name = "usuario")
public class Usuario implements Authentication {

	private static final long serialVersionUID = 1L;

	// Construtores
	/**
	 * Construtor vazio
	 */
	public Usuario() {
	}

	/**
	 * Construtor que popula todos os par�metros do novo objeto Usuario
	 * 
	 * @param id
	 *            par�metro id do objeto
	 * @param nome
	 *            par�metro nome do objeto
	 * @param email
	 *            par�metro email do objeto
	 * @param dataNascimento
	 *            par�metro dataNascimento do objeto
	 * @param permissao
	 *            par�metro permiss�o do objeto
	 */
	public Usuario(Long id, String nome, String email, Date dataNascimento, Permissao permissao) {

		this.id = id;
		this.nome = nome;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.permissao = permissao;
	}

	// Par�metros

	/**
	 * Identifica��o dos diferentes registros de usu�rios. Valor gerado
	 * autom�ticamente ao ser persistido.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	/** String que representa o nome do usu�rio */
	@Column(unique = false, nullable = false, length = 75)
	@Size(min = 3, max = 40)
	private String nome;

	/** String que representa o email do usu�rio */
	@Column(unique = true, nullable = false, length = 125)
	@Email
	private String email;

	/** Data de nascimento do usu�rio */
	@Column(name = "data_nascimento", unique = false, nullable = false)
	@NotNull
	private Date dataNascimento;

	/** N�vel de permiss�o do usu�rio */
	@Column(unique = false, nullable = false)
	private Permissao permissao;

	/** Chave de acesso do usu�rio */
	@Column(unique = false, nullable = false, length = 20)
	@Size(min = 3, max = 20)
	private String senha;

	// Getters & Setters

	/**
	 * Retorna o n�mero de identifica��o do registro Usu�rio
	 * 
	 * @return id do usu�rio
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Define o n�mero de identifica��o para o objeto Usu�rio (Sempre, ao ser
	 * persistido, o n�mero de identifica��o � gerado automaticamente)
	 * 
	 * @param id
	 *            n�mero de identifica��o
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna uma string referente ao nome do usu�rio
	 * 
	 * @return nome do usu�rio
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Define um nome para o objeto Usu�rio
	 * 
	 * @param nome
	 *            String referente ao nome do usu�rio
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Retorna uma string referente ao email do usu�rio
	 * 
	 * @return email do usu�rio
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Define um email para o objeto Usu�rio
	 * 
	 * @param email
	 *            String referente ao email do usu�rio
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Retorna um objeto Date que cont�m as informa��es da data de nascimento do
	 * objeto Usu�rio
	 * 
	 * @return data de nascimento em um objeto Date
	 */
	public Date getDataNascimento() {
		return dataNascimento;
	}

	/**
	 * Define um objeto Date que represente a data de nascimento do usu�rio
	 * 
	 * @param dataNascimento
	 *            objeto Date representando a data de nascimento do usu�rio
	 */
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	/**
	 * Retorna um enum de permiss�o que determina o qu� o usu�rio pode ou n�o fazer
	 * na aplica��o
	 * 
	 * @return enum de permiss�o
	 */
	public Permissao getPermissao() {
		return permissao;
	}

	/**
	 * Define uma das constantes do enum Permissao como a permiss�o que esse objeto
	 * Usu�rio tem sobre a aplica��o
	 * 
	 * @param permissao
	 *            uma constante do enum Permissao
	 */
	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}

	/**
	 * Retorna a chave de acesso do usu�rio
	 * 
	 * @return chave de acesso do usu�rio
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * Define uma chave de acesso para o usu�rio
	 * 
	 * @param senha
	 *            chave de acesso
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	// M�todos sobreescritos para a autentica��o com o Spring Security
	@Override
	public String getName() {
		return nome;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public void setAuthenticated(boolean arg0) throws IllegalArgumentException {
	}
}
