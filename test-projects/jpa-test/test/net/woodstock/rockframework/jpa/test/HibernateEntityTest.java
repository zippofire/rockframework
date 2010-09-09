package net.woodstock.rockframework.jpa.test;

import java.io.InputStream;
import java.util.List;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.persistence.query.impl.HibernateQueryBuilder;
import net.woodstock.rockframework.test.jpa.Email;
import net.woodstock.rockframework.test.jpa.Endereco;
import net.woodstock.rockframework.test.jpa.Pessoa;
import net.woodstock.rockframework.test.jpa.Telefone;
import net.woodstock.rockframework.utils.IOUtils;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateEntityTest extends TestCase {

	private SessionFactory	sessionFactory;

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

	@SuppressWarnings("unchecked")
	public void xtest2() throws Exception {
		Session session = this.getSession();
		Email email = (Email) session.get(Email.class, new Integer(4));

		HibernateQueryBuilder builder = new HibernateQueryBuilder(session);
		builder.setEntity(email);
		Query query = builder.getQuery();
		List<Email> emails = query.list();

		for (Email e : emails) {
			System.out.println(e.getDescricao());
		}

		session.close();
	}

	@SuppressWarnings("unchecked")
	public void test3() throws Exception {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("Email.hql");
		String sql = IOUtils.toString(inputStream);
		Session session = this.getSession();

		Query query = session.createQuery(sql);

		List<Email> emails = query.list();

		for (Email e : emails) {
			System.out.println(e.getDescricao());
		}

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
