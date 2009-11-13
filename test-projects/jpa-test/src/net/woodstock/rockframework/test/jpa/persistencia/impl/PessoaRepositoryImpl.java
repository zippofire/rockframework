package net.woodstock.rockframework.test.jpa.persistencia.impl;

import java.util.Collection;

import net.woodstock.rockframework.domain.persistence.impl.SpringHibernateRepository;
import net.woodstock.rockframework.test.jpa.Pessoa;
import net.woodstock.rockframework.test.jpa.persistencia.PessoaRepository;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class PessoaRepositoryImpl extends SpringHibernateRepository implements PessoaRepository {

	@Override
	public Collection<Pessoa> consultarPorIdade(Integer idadeInicial, Integer idadeFinal) {
		Criteria criteria = this.getSession().createCriteria(Pessoa.class);

		criteria.add(Restrictions.between("idade", idadeInicial, idadeFinal));

		return criteria.list();
	}

}
