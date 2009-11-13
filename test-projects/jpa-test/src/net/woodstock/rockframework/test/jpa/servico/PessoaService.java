package net.woodstock.rockframework.test.jpa.servico;

import java.util.Collection;

import net.woodstock.rockframework.domain.service.Service;
import net.woodstock.rockframework.test.jpa.Pessoa;

public interface PessoaService extends Service {

	Pessoa get(Pessoa pessoa);
	
	void save(Pessoa... pessoas);

	Collection<Pessoa> consultarPorIdade(Integer idadeInicial, Integer idadeFinal);
	
}
