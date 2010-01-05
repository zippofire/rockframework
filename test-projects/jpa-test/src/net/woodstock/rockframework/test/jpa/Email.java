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

@Entity
@Table(name = "TB_EMAIL")
public class Email implements net.woodstock.rockframework.domain.Entity<Integer> {

	private static final long	serialVersionUID	= 1L;

	@Id
	@Column(name = "ID_EMAIL")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer				id;

	@Column(name = "DSC_EMAIL", nullable = false, length = 200)
	private String				descricao;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_PESSOA", referencedColumnName = "ID_PESSOA", nullable = false)
	private Pessoa				pessoa;

	public Email() {
		super();
	}

	public Email(Integer id) {
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

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

}
