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
 * Utilizada também para autenticação realizada pelo Spring Security através da
 * implementação da interface Authentication
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
	 * Construtor que popula todos os parâmetros do novo objeto Usuario
	 * 
	 * @param id
	 *            parâmetro id do objeto
	 * @param nome
	 *            parâmetro nome do objeto
	 * @param email
	 *            parâmetro email do objeto
	 * @param dataNascimento
	 *            parâmetro dataNascimento do objeto
	 * @param permissao
	 *            parâmetro permissão do objeto
	 */
	public Usuario(Long id, String nome, String email, Date dataNascimento, Permissao permissao) {

		this.id = id;
		this.nome = nome;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.permissao = permissao;
	}

	// Parâmetros

	/**
	 * Identificação dos diferentes registros de usuários. Valor gerado
	 * automáticamente ao ser persistido.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	/** String que representa o nome do usuário */
	@Column(unique = false, nullable = false, length = 75)
	@Size(min = 3, max = 40)
	private String nome;

	/** String que representa o email do usuário */
	@Column(unique = true, nullable = false, length = 125)
	@Email
	private String email;

	/** Data de nascimento do usuário */
	@Column(name = "data_nascimento", unique = false, nullable = false)
	@NotNull
	private Date dataNascimento;

	/** Nível de permissão do usuário */
	@Column(unique = false, nullable = false)
	private Permissao permissao;

	/** Chave de acesso do usuário */
	@Column(unique = false, nullable = false, length = 20)
	@Size(min = 3, max = 20)
	private String senha;

	// Getters & Setters

	/**
	 * Retorna o número de identificação do registro Usuário
	 * 
	 * @return id do usuário
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Define o número de identificação para o objeto Usuário (Sempre, ao ser
	 * persistido, o número de identificação é gerado automaticamente)
	 * 
	 * @param id
	 *            número de identificação
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna uma string referente ao nome do usuário
	 * 
	 * @return nome do usuário
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Define um nome para o objeto Usuário
	 * 
	 * @param nome
	 *            String referente ao nome do usuário
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Retorna uma string referente ao email do usuário
	 * 
	 * @return email do usuário
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Define um email para o objeto Usuário
	 * 
	 * @param email
	 *            String referente ao email do usuário
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Retorna um objeto Date que contém as informações da data de nascimento do
	 * objeto Usuário
	 * 
	 * @return data de nascimento em um objeto Date
	 */
	public Date getDataNascimento() {
		return dataNascimento;
	}

	/**
	 * Define um objeto Date que represente a data de nascimento do usuário
	 * 
	 * @param dataNascimento
	 *            objeto Date representando a data de nascimento do usuário
	 */
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	/**
	 * Retorna um enum de permissão que determina o quê o usuário pode ou não fazer
	 * na aplicação
	 * 
	 * @return enum de permissão
	 */
	public Permissao getPermissao() {
		return permissao;
	}

	/**
	 * Define uma das constantes do enum Permissao como a permissão que esse objeto
	 * Usuário tem sobre a aplicação
	 * 
	 * @param permissao
	 *            uma constante do enum Permissao
	 */
	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}

	/**
	 * Retorna a chave de acesso do usuário
	 * 
	 * @return chave de acesso do usuário
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * Define uma chave de acesso para o usuário
	 * 
	 * @param senha
	 *            chave de acesso
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	// Métodos sobreescritos para a autenticação com o Spring Security
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
