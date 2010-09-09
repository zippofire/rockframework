package net.woodstock.rockframework.jpa.test;

import java.io.InputStream;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.persistence.query.impl.JPAQueryBuilder;
import net.woodstock.rockframework.test.jpa.Email;
import net.woodstock.rockframework.test.jpa.Endereco;
import net.woodstock.rockframework.test.jpa.Pessoa;
import net.woodstock.rockframework.test.jpa.Telefone;
import net.woodstock.rockframework.utils.IOUtils;

public class JPAEntityTest extends TestCase {

	private EntityManagerFactory	entityManagerfactory;

	private EntityManagerFactory getEntityManagerFactory() {
		if (this.entityManagerfactory == null) {
			this.entityManagerfactory = Persistence.createEntityManagerFactory("default");
		}
		return this.entityManagerfactory;
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = this.getEntityManagerFactory();
		EntityManager manager = factory.createEntityManager();
		return manager;
	}

	public void xtest1() throws Exception {
		EntityManager manager = this.getEntityManager();
		Pessoa pessoa = manager.find(Pessoa.class, new Integer(4));
		Email email = pessoa.getEmails().iterator().next();
		Endereco endereco = pessoa.getEnderecos().iterator().next();
		Telefone telefone = pessoa.getTelefones().iterator().next();

		this.printClass(pessoa);
		this.printClass(email);
		this.printClass(endereco);
		this.printClass(telefone);

		manager.close();
	}

	//@SuppressWarnings("unchecked")
	public void xtest2() throws Exception {
		EntityManager manager = this.getEntityManager();
		Email email = manager.find(Email.class, new Integer(4));

		JPAQueryBuilder builder = new JPAQueryBuilder(manager);
		builder.setEntity(email);
		
		System.out.println(builder.getQueryString());
		
		// Query query = builder.getQuery();
		// List<Email> emails = query.getResultList();

		// for (Email e : emails) {
		// System.out.println(e.getDescricao());
		// }

		// manager.close();
	}

	@SuppressWarnings("unchecked")
	public void xtest3() throws Exception {
		EntityManager manager = this.getEntityManager();
		Email email = new Email();
		email.setPessoa(new Pessoa(new Integer(8)));

		JPAQueryBuilder builder = new JPAQueryBuilder(manager);
		builder.setEntity(email);
		builder.build();

		System.out.println(builder.getQueryString());

		Query query = builder.getQuery();
		List<Email> emails = query.getResultList();

		for (Email e : emails) {
			System.out.println(e.getDescricao());
		}

		manager.close();
	}

	@SuppressWarnings("unchecked")
	public void test4() throws Exception {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("Email.hql");
		String sql = IOUtils.toString(inputStream);
		EntityManager manager = this.getEntityManager();

		Query query = manager.createQuery(sql);

		List<Email> emails = query.getResultList();

		for (Email e : emails) {
			System.out.println(e.getDescricao());
		}

		manager.close();
	}

	private void printClass(Object o) {
		Class<?> clazz = o.getClass();
		while (clazz != null) {
			System.out.println(clazz.getCanonicalName());
			for (Class<?> c : clazz.getInterfaces()) {
				System.out.println("\t" + c.getCanonicalName());
			}
			clazz = clazz.getSuperclass();
		}
	}

}
