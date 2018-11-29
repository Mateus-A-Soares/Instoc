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
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.lupus.models.serializer.Model;
import br.com.lupus.models.serializer.Serializer;


/**
 * <h1> Ambiente</h1>
 * <p> Classe modelo representativa da entidade Ambiente</p>
 * 
 * @author Mateus A.S
 */
@Entity
@Table(name = "ambiente")
@JsonSerialize(using = Serializer.class)
public class Ambiente extends Model {

	// Construtores
	
	/**
	 * Construtor vazio
	 */
	public Ambiente() {
	
	}
	
	/**
	 * Construtor que preenche todos os par�metros
	 * 
	 * @param id par�metro id do objeto Ambiente
	 * @param descricao par�metro descricao do objeto Ambiente
	 * @param cadastrante par�metro cadastrante do objeto Ambiente
	 * @param itens par�metro itens do objeto Ambiente
	 */
	public Ambiente(Long id, String descricao, Usuario cadastrante, List<Item> itens) {
		
		this.id = id;
		this.descricao = descricao;
		this.cadastrante = cadastrante;
		this.itens = itens;
	}

	// Par�metros	
	/** Identifica��o dos diferentes registros de ambientes.
	 *  Valor gerado autom�ticamente ao ser persistido. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	/** String descritiva do ambiente, m�ximo de 75 caracteres */
	@Column(unique = false, nullable = false, length = 75)
	private String descricao;

	/** Usu�rio que cadastrou/est� tentando cadastrar esse ambiente.*/
	@ManyToOne(optional = false)
	private Usuario cadastrante;
	
	/** Lista de itens anexados a esse registro */
	@OneToMany(mappedBy = "ambienteAtual", targetEntity = Item.class, fetch = FetchType.EAGER)
	private List<Item> itens;
	
	// Getters & Setters
	
	/**
	 *  Retorna o n�mero de identifica��o do registro
	 *  
	 * @return id do Ambiente
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 	Define o n�mero de identifica��o do Ambiente
	 * (Sempre, ao ser persistido, o n�mero de identifica��o � gerado automaticamente)
	 * 
	 * @param id n�mero de identifica��o do registro
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna a descri��o do registro Ambiente
	 * 
	 * @return String de descri��o do registro
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Define uma descri��o para o Ambiente
	 * 
	 * @param descricao String que descreve o ambiente
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Retorna o usuario cadastrante desse ambiente
	 * 
	 * @return usuario cadastrante
	 */
	public Usuario getCadastrante() {
		return cadastrante;
	}

	/**
	 * Define o usu�rio cadastrante para esse ambiente
	 * 
	 * @param cadastrante usu�rio que vai cadastrar esse ambiente
	 */
	public void setCadastrante(Usuario cadastrante) {
		this.cadastrante = cadastrante;
	}
	
	/**
	 * Retorna todos os itens que se encontram anexados ao id do ambiente atual
	 * 
	 * @return itens que est�o referenciados ao id desse registro
	 */
	public List<Item> getItens() {
		return itens;
	}

	/**
	 * Define os itens que pertecem a esse registro
	 * 
	 * @param itens 
	 */
	public void setItens(List<Item> itens) {
		this.itens = itens;
	}
}
