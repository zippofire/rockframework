package net.woodstock.rockframework.json;

import net.woodstock.rockframework.domain.util.IntegerEntity;

public class Telefone extends IntegerEntity {

	private static final long	serialVersionUID	= 1L;

	private Integer				id;

	private String				ddd;

	private String				numero;

	private TipoTelefone		tipo;

	private Pessoa				pessoa;

	public Telefone() {
		super();
	}

	public Telefone(final Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getDdd() {
		return this.ddd;
	}

	public void setDdd(final String ddd) {
		this.ddd = ddd;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(final String numero) {
		this.numero = numero;
	}

	public TipoTelefone getTipo() {
		return this.tipo;
	}

	public void setTipo(final TipoTelefone tipo) {
		this.tipo = tipo;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(final Pessoa pessoa) {
		this.pessoa = pessoa;
	}

}
