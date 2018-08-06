package br.com.lupus.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <h1>TipoItemTag</h1>
 * 
 * <p>Classe modelo representativa da entidade tag pertencente aos tipos de itens.</p>
 * 
 * @author Mateus A.S
 */
@Entity
@Table(name = "tipo_item_tag")
public class TipoItemTag {

	// Par�metros
	
	/** Identifica��o dos diferentes registros de tags.
	 *  Valor gerado autom�ticamente ao ser persistido. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** String referente ao nome da tag. Tamanho m�ximo de 50 caracteres. */
	@Column(unique = false, nullable = false, length = 50)
	private String cabecalho;
	
	/** String referente ao valor da tag. Tamanho m�ximo de 50 caracteres. */
	@Column(unique = false, nullable = true, length = 50)
	private String corpo;
	
	/** String referente ao tipo de dado que o valor da tag representa (como data, n�mero, etc).
	 * Tamanho m�ximo de 50 caracteres. */
	@Column(unique = false, nullable = false, length = 50)
	private String tipo;
	
	// Getters & Setters

	/**
	 * M�todo que retorna o n�mero de identifica��o do objeto TipoItemTag
	 * 
	 * @return id da tag
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Define um n�mero de identifica��o para o objeto TipoItemTag
	 * 
	 * @param id n�mero de identifica��o
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * M�todo que retorna o nome do objeto TipoItemTag (cabe�alho)
	 * 
	 * @return cabe�alho da tag
	 */
	public String getCabecalho() {
		return cabecalho;
	}

	/**
	 * Define um nome para o objeto TipoItemTag (cabe�alho)
	 * 
	 * @param cabecalho nome a ser colocado na tag
	 */
	public void setCabecalho(String cabecalho) {
		this.cabecalho = cabecalho;
	}

	/**
	 * M�todo que retorna o valor do objeto TipoItemTag (corpo)
	 * 
	 * @return corpo da tag
	 */
	public String getCorpo() {
		return corpo;
	}

	/**
	 * Define um nome para o objeto TipoItemTag (cabe�alho)
	 * 
	 * @param cabecalho nome a ser colocado na tag
	 */
	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}

	/**
	 * M�todo que retorna o tipo do objeto TipoItemTag
	 * 
	 * @return tipo de dado do valor da tag
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Define um tipo para o objeto TipoItemTag
	 * 
	 * @param tipo tipo de dado do valor da tag
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
