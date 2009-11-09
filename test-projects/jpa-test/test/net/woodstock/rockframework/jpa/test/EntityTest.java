package net.woodstock.rockframework.jpa.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.persistence.query.QueryBuilder;
import net.woodstock.rockframework.domain.persistence.query.impl.HibernateQueryBuilder;
import net.woodstock.rockframework.jpa.Email;
import net.woodstock.rockframework.jpa.Endereco;
import net.woodstock.rockframework.jpa.Pessoa;
import net.woodstock.rockframework.jpa.Telefone;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class EntityTest extends TestCase {

	private EntityManagerFactory	entityManagerfactory;

	private SessionFactory			sessionFactory;

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

	private SessionFactory getSessionFactory() {
		if (this.sessionFactory == null) {
			this.sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		}
		return this.sessionFactory;
	}

	private Session getSession() {
		SessionFactory factory = this.getSessionFactory();
		Session session = factory.openSession();
		return session;
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

	public void xtest2() throws Exception {
		EntityManager manager = this.getEntityManager();
		Email email = manager.find(Email.class, new Integer(4));
		Pessoa pessoa = email.getPessoa();
		Endereco endereco = pessoa.getEnderecos().iterator().next();
		Telefone telefone = pessoa.getTelefones().iterator().next();

		this.printClass(pessoa);
		this.printClass(email);
		this.printClass(endereco);
		this.printClass(telefone);

		manager.close();
	}

	public void xtest3() throws Exception {
		Session session = this.getSession();
		Email email = (Email) session.get(Email.class, new Integer(4));
		Pessoa pessoa = email.getPessoa();
		Endereco endereco = pessoa.getEnderecos().iterator().next();
		Telefone telefone = pessoa.getTelefones().iterator().next();

		this.printClass(pessoa);
		this.printClass(email);
		this.printClass(endereco);
		this.printClass(telefone);

		session.close();
	}

	public void test4() throws Exception {
		Session session = this.getSession();
		Email email = (Email) session.get(Email.class, new Integer(4));

		QueryBuilder builder = new HibernateQueryBuilder();
		builder.parse(email);
		Query query = (Query) builder.getQuery(session);
		query.list();

		session.close();
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
