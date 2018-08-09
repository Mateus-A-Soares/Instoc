package br.com.lupus.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

	// Parâmetros
	
	/** Identificação dos diferentes registros de tags.
	 *  Valor gerado automáticamente ao ser persistido. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long id;
	
	/** String referente ao nome da tag. Tamanho máximo de 50 caracteres. */
	@Column(unique = false, nullable = false, length = 50)
	private String cabecalho;
	
	/** String referente ao valor da tag. Tamanho máximo de 50 caracteres. */
	@Column(unique = false, nullable = true, length = 50)
	private String corpo;
	
	/** String referente ao tipo de dado que o valor da tag representa (como data, número, etc).
	 * Tamanho máximo de 50 caracteres. */
	@Column(unique = false, nullable = false, length = 50)
	private String tipo;
	
	/** TipoItem a qual a tag pertence. */
	@ManyToOne(optional = false)
	@JoinColumn(name = "tipo_item_id")
	private TipoItem tipoItem;
	
	// Getters & Setters

	/**
	 * Método que retorna o número de identificação do registro TipoItemTag
	 * 
	 * @return id da tag
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Define um número de identificação para o objeto TipoItemTag
	 * 
	 * @param id número de identificação
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Método que retorna o nome do objeto TipoItemTag (cabeçalho)
	 * 
	 * @return cabeçalho da tag
	 */
	public String getCabecalho() {
		return cabecalho;
	}

	/**
	 * Define um nome para o objeto TipoItemTag (cabeçalho)
	 * 
	 * @param cabecalho nome a ser colocado na tag
	 */
	public void setCabecalho(String cabecalho) {
		this.cabecalho = cabecalho;
	}

	/**
	 * Método que retorna o valor do objeto TipoItemTag (corpo)
	 * 
	 * @return corpo da tag
	 */
	public String getCorpo() {
		return corpo;
	}

	/**
	 * Define um nome para o objeto TipoItemTag (cabeçalho)
	 * 
	 * @param cabecalho nome a ser colocado na tag
	 */
	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}

	/**
	 * Método que retorna o tipo do objeto TipoItemTag
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
	
	/**
	 * Método que retonar o TipoItem a qual a tag está anexada.
	 * 
	 * @return objeto TipoItem a qual a tag está anexada.
	 */
	public TipoItem getTipoItem() {
		return tipoItem;
	}
	
	/**
	 * Define a qual TipoItem a tag é relacionada.
	 *  
	 * @param tipoItem objeto TipoItem (necessita ser populado com o registro de TipoItem já existente 
	 * e não pendente a existir, com o campo id correto).
	 */
	public void setTipoItem(TipoItem tipoItem) {
		this.tipoItem = tipoItem;
	}
}
