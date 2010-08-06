package net.woodstock.rockframework.test.json;

import java.util.Collection;

import net.woodstock.rockframework.domain.Entity;

public class Pessoa implements Entity<Integer> {

	private static final long		serialVersionUID	= 1L;

	private Integer					id;

	private String					nome;

	private Collection<Telefone>	telefones;

	public Pessoa() {
		super();
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public void setId(final Integer id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(final String nome) {
		this.nome = nome;
	}

	public Collection<Telefone> getTelefones() {
		return this.telefones;
	}

	public void setTelefones(final Collection<Telefone> telefones) {
		this.telefones = telefones;
	}

}
