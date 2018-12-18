package br.com.lupus.models;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.lupus.models.serializer.Model;
import br.com.lupus.models.serializer.Serializer;

/**
 * <h1>TipoItem</h1>
 * 
 * <p>
 * Classe modelo representativa da entidade tipo item, entidade que cont�m todos
 * os aspectos de um tipo de item
 * </p>
 * 
 * @author Mateus A.S
 */
@Entity
@Table(name = "tipo_item")
@JsonSerialize(using = Serializer.class)
public class TipoItem extends Model {

	// Construtores

	/**
	 * Construtor vazio
	 */
	public TipoItem() {
	}

	/**
	 * Construtor que preenche todos os par�metros do objeto TipoItem
	 * 
	 * @param id
	 *            par�metro id do objeto TipoItem
	 * @param nome
	 *            par�metro nome do objeto TipoItem
	 * @param tagsAnexadas
	 *            par�metro tagsAnexadas do objeto TipoItem
	 */
	public TipoItem(Long id, String nome, Usuario cadastrante, List<TipoItemTag> tagsAnexadas) {

		this.id = id;
		this.nome = nome;
		this.cadastrante = cadastrante;
		this.tagsAnexadas = tagsAnexadas;
	}

	// Par�metros

	/**
	 * Identifica��o dos diferentes registros de tipos de itens. Valor gerado
	 * autom�ticamente ao ser persistido.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	/**
	 * String referente ao nome descritivo do item, com largura m�xima de 75
	 * caracteres
	 */
	@Column(unique = false, nullable = false, length = 75)
	private String nome;

	/** Usu�rio que cadastrou/est� tentando cadastrar esse registro */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Usuario cadastrante;

	/**
	 * Lista de tags que est�o referenciadas por uma chave estrangeira ao id desse
	 * objeto
	 */
	@OneToMany(mappedBy = "tipoItem", targetEntity = TipoItemTag.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<TipoItemTag> tagsAnexadas;
	
	/**
	 * Lista de itens que est�o referenciadas por uma chave estrangeira ao id desse
	 * objeto
	 */
	@OneToMany(mappedBy = "tipo", targetEntity = Item.class, fetch = FetchType.LAZY)
	private List<Item> itensAnexados;

	// Getters & Setters

	/**
	 * M�todo que retorna o n�mero de identifica��o (id) do registro TipoItem
	 * 
	 * @return id do TipoItem
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Define um n�mero de identifica��o para o objeto. (Sempre, ao ser persistido,
	 * o n�mero de identifica��o � gerado automaticamente)
	 * 
	 * @param id
	 *            n�mero positivo a ser definido como id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * M�todo que retorna o nome do registro
	 * 
	 * @return nome do TipoItem
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Define um nome (at� 75 caracteres) descritivo para o TipoItem
	 * 
	 * @param nome
	 *            nome descritivo de at� 75 caracteres
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Retorna o usuario cadastrante desse registro
	 * 
	 * @return usuario cadastrante
	 */
	public Usuario getCadastrante() {
		return cadastrante;
	}

	/**
	 * Define o usu�rio cadastrante para esse registro
	 * 
	 * @param cadastrante
	 *            usu�rio que vai cadastrar esse registro
	 */
	public void setCadastrante(Usuario cadastrante) {
		this.cadastrante = cadastrante;
	}

	/**
	 * M�todo que retorna uma lista de tags que est�o referenciadas ao id desse
	 * objeto
	 * 
	 * @return lista de tags referenciadas ao id desse objeto
	 */
	public List<TipoItemTag> getTagsAnexadas() {
		return tagsAnexadas;
	}

	/**
	 * Define as tags anexadas a esse id
	 * 
	 * @param tagsAnexadas
	 *            lista de tags a ser anexada a esse objeto
	 */
	public void setTagsAnexadas(List<TipoItemTag> tagsAnexadas) {
		this.tagsAnexadas = tagsAnexadas;
	}

	/**
	 * M�todo que retorna uma lista de itens que est�o referenciadas ao id desse
	 * objeto
	 * 
	 * @return lista de itens referenciadas ao id desse objeto
	 */
	public List<Item> getItensAnexados() {
		return itensAnexados;
	}
	/**
	 * Define os itens anexados a esse id
	 * 
	 * @param itensAnexados
	 *            lista de itens a ser anexado a esse objeto
	 */
	public void setItensAnexados(List<Item> itensAnexados) {
		this.itensAnexados = itensAnexados;
	}
	
	@Override
	public String toString() {
		return "TipoItem [id=" + id + ", nome=" + nome + "]";
	}
}
