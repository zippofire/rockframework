package net.woodstock.rockframework.test.jpa.persistencia;

import java.util.Collection;

import net.woodstock.rockframework.domain.persistence.Repository;
import net.woodstock.rockframework.test.jpa.Pessoa;

public interface PessoaRepository extends Repository {

	Collection<Pessoa> consultarPorIdade(Integer idadeInicial, Integer idadeFinal);

}
