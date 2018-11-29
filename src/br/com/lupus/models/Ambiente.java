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
	 * Construtor que preenche todos os parâmetros
	 * 
	 * @param id parâmetro id do objeto Ambiente
	 * @param descricao parâmetro descricao do objeto Ambiente
	 * @param cadastrante parâmetro cadastrante do objeto Ambiente
	 * @param itens parâmetro itens do objeto Ambiente
	 */
	public Ambiente(Long id, String descricao, Usuario cadastrante, List<Item> itens) {
		
		this.id = id;
		this.descricao = descricao;
		this.cadastrante = cadastrante;
		this.itens = itens;
	}

	// Parâmetros	
	/** Identificação dos diferentes registros de ambientes.
	 *  Valor gerado automáticamente ao ser persistido. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	/** String descritiva do ambiente, máximo de 75 caracteres */
	@Column(unique = false, nullable = false, length = 75)
	private String descricao;

	/** Usuário que cadastrou/está tentando cadastrar esse ambiente.*/
	@ManyToOne(optional = false)
	private Usuario cadastrante;
	
	/** Lista de itens anexados a esse registro */
	@OneToMany(mappedBy = "ambienteAtual", targetEntity = Item.class, fetch = FetchType.EAGER)
	private List<Item> itens;
	
	// Getters & Setters
	
	/**
	 *  Retorna o número de identificação do registro
	 *  
	 * @return id do Ambiente
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 	Define o número de identificação do Ambiente
	 * (Sempre, ao ser persistido, o número de identificação é gerado automaticamente)
	 * 
	 * @param id número de identificação do registro
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna a descrição do registro Ambiente
	 * 
	 * @return String de descrição do registro
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Define uma descrição para o Ambiente
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
	 * Define o usuário cadastrante para esse ambiente
	 * 
	 * @param cadastrante usuário que vai cadastrar esse ambiente
	 */
	public void setCadastrante(Usuario cadastrante) {
		this.cadastrante = cadastrante;
	}
	
	/**
	 * Retorna todos os itens que se encontram anexados ao id do ambiente atual
	 * 
	 * @return itens que estão referenciados ao id desse registro
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
