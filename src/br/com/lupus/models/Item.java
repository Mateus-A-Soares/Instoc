package br.com.lupus.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.lupus.models.serializer.Model;
import br.com.lupus.models.serializer.Serializer;

/**
 * <h1>Item</h1>
 * <p>
 * Classe modelo da entidade item.
 * </p>
 * 
 * @author Mateus A.S
 */
@Entity
@Table(name = "item")
@JsonSerialize(using = Serializer.class)
public class Item extends Model {

	// Construtores

	/**
	 * Construtor vazio.
	 */
	public Item() {
	}

	/**
	 * Construtor com todos os par�metros.
	 * 
	 * @param id
	 *            par�metro id do Objeto
	 * @param tipo
	 *            par�metro tipo do Objeto
	 * @param cadastrante
	 *            par�metro cadastrante do Objeto
	 * @param ambienteAtual
	 *            par�metro ambienteAtual do Objeto
	 */
	public Item(Long id, TipoItem tipo, Usuario cadastrante, Ambiente ambienteAtual) {

		this.id = id;
		this.tipo = tipo;
		this.cadastrante = cadastrante;
		this.ambienteAtual = ambienteAtual;
	}

	// Par�metros

	/**
	 * Identifica��o dos diferentes registros de itens. Valor gerado autom�ticamente
	 * ao ser persistido.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	/**
	 * Tipo cadastrado que representa esse item. Registro com as
	 * defini��es/descri��es desse item.
	 */
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private TipoItem tipo;

	/** Usu�rio que cadastrou/est� tentando cadastrar esse item. */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Usuario cadastrante;

	/** Ambiente em que o item se encontra. */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ambiente_atual_id")
	private Ambiente ambienteAtual;

	// Getters & Setters

	/**
	 * M�todo que retorna o n�mero de identifica��o do registro Item
	 * 
	 * @return id do item
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Define o n�mero de identifica��o para o objeto Item (Sempre, ao ser
	 * persistido, o n�mero de identifica��o � gerado automaticamente)
	 * 
	 * @param id
	 *            n�mero de identifica��o
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna um objeto TipoItem populado com as defini��es/descri��es desse item
	 * 
	 * @return TipoItem que representa esse item
	 */
	public TipoItem getTipo() {
		return tipo;
	}

	/**
	 * Define um objeto TipoItem que representa as defini��es/descri��es desse tipo
	 * de item
	 * 
	 * @param tipo
	 *            objeto TipoItem com as defini��es desse item
	 */
	public void setTipo(TipoItem tipo) {
		this.tipo = tipo;
	}

	/**
	 * Retorna o usuario cadastrante desse item
	 * 
	 * @return usuario cadastrante
	 */
	public Usuario getCadastrante() {
		return cadastrante;
	}

	/**
	 * Define o usu�rio cadastrante para esse item
	 * 
	 * @param cadastrante
	 *            usu�rio que vai cadastrar esse item
	 */
	public void setCadastrante(Usuario cadastrante) {
		this.cadastrante = cadastrante;
	}

	/**
	 * Retorna um objeto Ambiente que representa onde esse item de encontra
	 * 
	 * @return objeto que representa o ambiente desse item
	 */
	public Ambiente getAmbienteAtual() {
		return ambienteAtual;
	}

	/**
	 * Define um objeto Ambiente que representa onde o item vai ser encontrado
	 * 
	 * @param ambienteAtual
	 *            ambiente onde o item deve ser encontrado
	 */
	public void setAmbienteAtual(Ambiente ambienteAtual) {
		this.ambienteAtual = ambienteAtual;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", tipo=" + tipo + ", cadastrante=" + cadastrante + ", ambienteAtual=" + ambienteAtual
				+ "]";
	}
}
