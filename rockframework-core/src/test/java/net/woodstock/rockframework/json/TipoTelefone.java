package net.woodstock.rockframework.json;

import net.woodstock.rockframework.domain.util.IntegerEntity;

public class TipoTelefone extends IntegerEntity {

	private static final long	serialVersionUID	= -2850519769437585071L;

	private Integer				id;

	private String				descricao;

	public TipoTelefone() {
		super();
	}

	public TipoTelefone(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
