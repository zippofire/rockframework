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

import net.woodstock.rockframework.domain.business.validation.Operation;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateLength;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateNotNull;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateNull;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateReference;
import net.woodstock.rockframework.domain.util.IntegerEntity;

@Entity
@Table(name = "TB_EMAIL")
public class Email extends IntegerEntity {

	private static final long	serialVersionUID	= 1L;

	@Id
	@Column(name = "ID_EMAIL")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ValidateNotNull(operation = { Operation.GET, Operation.DELETE, Operation.UPDATE })
	@ValidateNull(operation = { Operation.SAVE })
	private Integer				id;

	@Column(name = "DSC_EMAIL", nullable = false, length = 200)
	@ValidateLength(min = 10, max = 200)
	private String				descricao;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_PESSOA", referencedColumnName = "ID_PESSOA", nullable = false)
	@ValidateReference(operation = { Operation.SAVE, Operation.UPDATE }, referenceOperaton = Operation.GET)
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
