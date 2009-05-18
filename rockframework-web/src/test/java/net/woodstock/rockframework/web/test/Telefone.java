package net.woodstock.rockframework.web.test;

import java.io.Serializable;

public class Telefone implements Serializable {

	private static final long	serialVersionUID	= 1L;

	private Integer				id;

	private String				ddd;

	private String				numero;

	private TipoTelefone		tipo;

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

	public TipoTelefone getTipo() {
		return this.tipo;
	}

	public void setTipo(TipoTelefone tipo) {
		this.tipo = tipo;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

}
