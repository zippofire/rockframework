package net.woodstock.rockframework.test.json;

import net.woodstock.rockframework.domain.Entity;

public class TipoTelefone implements Entity<Integer> {

	private static final long	serialVersionUID	= -2850519769437585071L;

	private Integer				id;

	private String				descricao;

	public TipoTelefone() {
		super();
	}

	public TipoTelefone(final Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(final String descricao) {
		this.descricao = descricao;
	}

}
