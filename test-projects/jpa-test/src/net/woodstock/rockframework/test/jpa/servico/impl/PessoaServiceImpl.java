package net.woodstock.rockframework.test.jpa.servico.impl;

import java.util.Collection;

import net.woodstock.rockframework.domain.business.ValidationException;
import net.woodstock.rockframework.domain.service.impl.SpringGenericService;
import net.woodstock.rockframework.test.jpa.Pessoa;
import net.woodstock.rockframework.test.jpa.persistencia.PessoaRepository;
import net.woodstock.rockframework.test.jpa.servico.PessoaService;

public class PessoaServiceImpl extends SpringGenericService implements PessoaService {

	private PessoaRepository	repository;

	@Override
	public Pessoa get(Pessoa pessoa) {
		pessoa = this.getRepository().get(pessoa);

		if (pessoa != null) {
			pessoa.setAtivo(Boolean.FALSE);
			if (pessoa.getEmails().size() > 1) {

			}
		}

		return null;
	}

	@Override
	public void save(Pessoa... pessoas) {
		for (Pessoa pessoa : pessoas) {
			this.getRepository().save(pessoa);
		}
	}

	@Override
	public Collection<Pessoa> consultarPorIdade(Integer idadeInicial, Integer idadeFinal) {
		if (idadeInicial == null || idadeInicial.intValue() <= 0) {
			throw new ValidationException("Idade inicial invalida");
		}
		if (idadeFinal == null || idadeFinal.intValue() <= 0) {
			throw new ValidationException("Idade final invalida");
		}
		return this.repository.consultarPorIdade(idadeInicial, idadeFinal);
	}

}
