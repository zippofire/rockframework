package net.woodstock.rockframework.test.jpa;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.woodstock.rockframework.domain.business.validation.Operation;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateIntConstraint;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateLength;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateNotNull;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateNull;
import net.woodstock.rockframework.domain.util.IntegerEntity;

@Entity
@Table(name = "TB_PESSOA")
public class Pessoa extends IntegerEntity {

	private static final long	serialVersionUID	= 1L;

	@Id
	@Column(name = "ID_PESSOA")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ValidateNotNull(operation = { Operation.GET, Operation.DELETE, Operation.UPDATE })
	@ValidateNull(operation = { Operation.SAVE })
	private Integer				id;

	@Column(name = "NOM_PESSOA", nullable = false, length = 50)
	@ValidateLength(min = 10, max = 50)
	private String				nome;

	@Column(name = "NUM_IDADE", nullable = false)
	@ValidateIntConstraint(values = { 18, 50 })
	private Integer				idade;

	@Column(name = "FLG_ATIVO", nullable = false)
	private Boolean				ativo;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "pessoa", fetch = FetchType.LAZY)
	private Set<Email>			emails;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "pessoa", fetch = FetchType.LAZY)
	private Set<Endereco>		enderecos;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "pessoa", fetch = FetchType.LAZY)
	private Set<Telefone>		telefones;

	public Pessoa() {
		super();
	}

	public Pessoa(Integer id) {
		super();
		this.id = id;
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

	public Integer getIdade() {
		return this.idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public Boolean getAtivo() {
		return this.ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Set<Email> getEmails() {
		return this.emails;
	}

	public void setEmails(Set<Email> emails) {
		this.emails = emails;
	}

	public Set<Endereco> getEnderecos() {
		return this.enderecos;
	}

	public void setEnderecos(Set<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<Telefone> getTelefones() {
		return this.telefones;
	}

	public void setTelefones(Set<Telefone> telefones) {
		this.telefones = telefones;
	}

}
