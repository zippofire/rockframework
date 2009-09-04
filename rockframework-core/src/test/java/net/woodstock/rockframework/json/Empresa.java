package net.woodstock.rockframework.json;

import java.util.Collection;

import net.woodstock.rockframework.domain.util.IntegerEntity;

public class Empresa extends IntegerEntity {

	private static final long	serialVersionUID	= 1L;

	private Integer				id;

	private String				nome;

	private Collection<Pessoa>	pessoas;

	public Empresa() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Collection<Pessoa> getPessoas() {
		return this.pessoas;
	}

	public void setPessoas(Collection<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

}
