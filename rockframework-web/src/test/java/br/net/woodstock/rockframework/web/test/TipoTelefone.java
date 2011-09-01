package br.net.woodstock.rockframework.web.test;

import java.io.Serializable;

public class TipoTelefone implements Serializable {

	private static final long			serialVersionUID			= -2850519769437585071L;

	public static final TipoTelefone	TELEFONE_FIXO_COMERCIAL		= new TipoTelefone(Integer.valueOf(1));

	public static final TipoTelefone	TELEFONE_FIXO_RESIDENCIAL	= new TipoTelefone(Integer.valueOf(2));

	public static final TipoTelefone	TELEFONE_CELULAR_COMERCIAL	= new TipoTelefone(Integer.valueOf(3));

	public static final TipoTelefone	TELEFONE_CELULAR_PESSOAL	= new TipoTelefone(Integer.valueOf(4));

	public static final TipoTelefone	TELEFONE_FAX_COMERCIAL		= new TipoTelefone(Integer.valueOf(5));

	public static final TipoTelefone	TELEFONE_FAX_PESSOAL		= new TipoTelefone(Integer.valueOf(6));

	private Integer						id;

	private String						descricao;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.descricao == null) ? 0 : this.descricao.hashCode());
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TipoTelefone)) {
			return false;
		}
		final TipoTelefone other = (TipoTelefone) obj;
		if (this.descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		} else if (!this.descricao.equals(other.descricao)) {
			return false;
		}
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
