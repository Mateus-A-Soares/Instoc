package br.com.lupus.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
 * <h1>Movimentacao</h1>
 * 
 * <p>
 * Classe representativa da entidade movimentação
 * </p>
 * 
 * @author Mateus A.S
 */
@Entity
@Table(name = "movimentacao")
@JsonSerialize(using = Serializer.class)
public class Movimentacao extends Model {

	// Construtores

	/**
	 * Construtor vazio
	 */
	public Movimentacao() {
	}

	/**
	 * Construtor que popula o novo objeto com todos os parâmetros
	 * 
	 * @param id
	 *            parâmetro id do objeto Movimentação
	 * @param dataMovimentacao
	 *            parâmetro dataMovimentacao do objeto Movimentação
	 * @param itemMovimentado
	 *            parâmetro itemMovimentado do objeto Movimentação
	 * @param ambienteAnterior
	 *            parâmetro ambienteAnterior do objeto Movimentação
	 * @param ambientePosterior
	 *            parâmetro ambientePosterior do objeto Movimentação
	 * @param movimentador
	 *            parâmetro movimentador do objeto Movimentação
	 */
	public Movimentacao(Long id, Date dataMovimentacao, Item itemMovimentado, Ambiente ambienteAnterior,
			Ambiente ambientePosterior, Usuario movimentador) {

		this.id = id;
		this.dataMovimentacao = dataMovimentacao;
		this.itemMovimentado = itemMovimentado;
		this.ambienteAnterior = ambienteAnterior;
		this.ambientePosterior = ambientePosterior;
		this.movimentador = movimentador;
	}

	// Váriaveis

	/**
	 * Identificação dos diferentes registros de movimentações. Valor gerado
	 * automáticamente ao ser persistido.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long id;

	/** Objeto Date que representa a data em que o item foi movimentado */
	@Column(name = "data_movimentacao", nullable = false, unique = false)
	private Date dataMovimentacao;

	/** Objeto que representa o registro do item movimentado */
	@ManyToOne
	@JoinColumn(name = "item_movimentado_id")
	private Item itemMovimentado;

	/**
	 * Objeto que representa o registro do ambiente em que o item estava anexado
	 * anteriormente
	 */
	@ManyToOne
	@JoinColumn(name = "ambiente_anterior_id")
	private Ambiente ambienteAnterior;

	/**
	 * Objeto que representa o registro do ambiente a qual o item vai ser anexado
	 */
	@ManyToOne
	@JoinColumn(name = "ambiente_posterior_id")
	private Ambiente ambientePosterior;

	/** Objeto que representa o registro do usuário que efetuou a movimentação */
	@ManyToOne
	private Usuario movimentador;

	// Getters and Setters

	/**
	 * Retorna o número de identificação do objeto Movimentação
	 * 
	 * @return id da movimentação
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Define o número de identificação do Usuário (Sempre, ao ser persistido, o
	 * número de identificação é gerado automaticamente)
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna um objeto Date que representa a data em que a movimentação foi
	 * efetuada
	 * 
	 * @return data de movimentação em um objeto Date
	 */
	public Date getDataMovimentacao() {
		return dataMovimentacao;
	}

	/**
	 * Define um objeto date que representá a data em que foi efetuada a
	 * movimentação
	 * 
	 * @param dataMovimentacao
	 *            objeto Date representando a data em que foi efetuada a
	 *            movimentação
	 */
	public void setDataMovimentacao(Date dataMovimentacao) {
		this.dataMovimentacao = dataMovimentacao;
	}

	/**
	 * Retorna um objeto Item populado com os dados do registro do item movimentado
	 * 
	 * @return item movimentado
	 */
	public Item getItemMovimentado() {
		return itemMovimentado;
	}

	/**
	 * Define um objeto Item que representará o item movimentado
	 * 
	 * @param itemMovimentado
	 *            objeto Item que será anexado a movimentação
	 */
	public void setItemMovimentado(Item itemMovimentado) {
		this.itemMovimentado = itemMovimentado;
	}

	/**
	 * Retorna um objeto Ambiente populado com os dados do registro do ambiente
	 * anterior
	 * 
	 * @return ambiente anterior
	 */
	public Ambiente getAmbienteAnterior() {
		return ambienteAnterior;
	}

	/**
	 * Define um objeto Ambiente que representará o ambiente em que o item se
	 * encontrava
	 * 
	 * @param ambienteAnterior
	 *            objeto Ambiente que será anexado como ambiente anterior
	 */
	public void setAmbienteAnterior(Ambiente ambienteAnterior) {
		this.ambienteAnterior = ambienteAnterior;
	}

	/**
	 * Retorna um objeto Ambiente populado com os dados do registro do ambiente
	 * posterior
	 * 
	 * @return ambiente posterior
	 */
	public Ambiente getAmbientePosterior() {
		return ambientePosterior;
	}

	/**
	 * Define um objeto Ambiente que representará o ambiente em que o item irá se
	 * encontrar
	 * 
	 * @param ambientePosterior
	 *            objeto Ambiente que será anexado com ambiente posterior
	 */
	public void setAmbientePosterior(Ambiente ambientePosterior) {
		this.ambientePosterior = ambientePosterior;
	}

	/**
	 * Retorna um objeto Usuário populado com os dados do registro do usuário
	 * movimentador
	 * 
	 * @return usuário movimentador
	 */
	public Usuario getMovimentador() {
		return movimentador;
	}

	/**
	 * Define um usuário que representará o usuário movimentador
	 * 
	 * @param movimentador
	 *            objeto Usuário que será anexado a movimentação como movimentador
	 */
	public void setMovimentador(Usuario movimentador) {
		this.movimentador = movimentador;
	}
}
