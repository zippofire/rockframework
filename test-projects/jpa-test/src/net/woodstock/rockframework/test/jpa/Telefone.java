package net.woodstock.rockframework.test.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.woodstock.rockframework.domain.util.IntegerEntity;

@Entity
@Table(name = "TB_TELEFONE")
public class Telefone extends IntegerEntity {

	private static final long	serialVersionUID	= 1L;

	@Id
	@Column(name = "ID_TELEFONE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer				id;

	@Column(name = "NUM_DDD", nullable = false, length = 2)
	private String				ddd;

	@Column(name = "NUM_TELEFONE", nullable = false, length = 2)
	private String				numero;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_PESSOA", referencedColumnName = "ID_PESSOA", nullable = false)
	private Pessoa				pessoa;

	public Telefone() {
		super();
	}

	public Telefone(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDdd() {
		return this.ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

}
