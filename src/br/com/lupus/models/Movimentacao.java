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
 * Classe representativa da entidade movimenta��o
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
	 * Construtor que popula o novo objeto com todos os par�metros
	 * 
	 * @param id
	 *            par�metro id do objeto Movimenta��o
	 * @param dataMovimentacao
	 *            par�metro dataMovimentacao do objeto Movimenta��o
	 * @param itemMovimentado
	 *            par�metro itemMovimentado do objeto Movimenta��o
	 * @param ambienteAnterior
	 *            par�metro ambienteAnterior do objeto Movimenta��o
	 * @param ambientePosterior
	 *            par�metro ambientePosterior do objeto Movimenta��o
	 * @param movimentador
	 *            par�metro movimentador do objeto Movimenta��o
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

	// V�riaveis

	/**
	 * Identifica��o dos diferentes registros de movimenta��es. Valor gerado
	 * autom�ticamente ao ser persistido.
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

	/** Objeto que representa o registro do usu�rio que efetuou a movimenta��o */
	@ManyToOne
	private Usuario movimentador;

	// Getters and Setters

	/**
	 * Retorna o n�mero de identifica��o do objeto Movimenta��o
	 * 
	 * @return id da movimenta��o
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Define o n�mero de identifica��o do Usu�rio (Sempre, ao ser persistido, o
	 * n�mero de identifica��o � gerado automaticamente)
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna um objeto Date que representa a data em que a movimenta��o foi
	 * efetuada
	 * 
	 * @return data de movimenta��o em um objeto Date
	 */
	public Date getDataMovimentacao() {
		return dataMovimentacao;
	}

	/**
	 * Define um objeto date que represent� a data em que foi efetuada a
	 * movimenta��o
	 * 
	 * @param dataMovimentacao
	 *            objeto Date representando a data em que foi efetuada a
	 *            movimenta��o
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
	 * Define um objeto Item que representar� o item movimentado
	 * 
	 * @param itemMovimentado
	 *            objeto Item que ser� anexado a movimenta��o
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
	 * Define um objeto Ambiente que representar� o ambiente em que o item se
	 * encontrava
	 * 
	 * @param ambienteAnterior
	 *            objeto Ambiente que ser� anexado como ambiente anterior
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
	 * Define um objeto Ambiente que representar� o ambiente em que o item ir� se
	 * encontrar
	 * 
	 * @param ambientePosterior
	 *            objeto Ambiente que ser� anexado com ambiente posterior
	 */
	public void setAmbientePosterior(Ambiente ambientePosterior) {
		this.ambientePosterior = ambientePosterior;
	}

	/**
	 * Retorna um objeto Usu�rio populado com os dados do registro do usu�rio
	 * movimentador
	 * 
	 * @return usu�rio movimentador
	 */
	public Usuario getMovimentador() {
		return movimentador;
	}

	/**
	 * Define um usu�rio que representar� o usu�rio movimentador
	 * 
	 * @param movimentador
	 *            objeto Usu�rio que ser� anexado a movimenta��o como movimentador
	 */
	public void setMovimentador(Usuario movimentador) {
		this.movimentador = movimentador;
	}
}
