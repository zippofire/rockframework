package net.woodstock.rockframework.jpa.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.test.jpa.Pessoa;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;

public class SpringTest extends TestCase {

	public void test1() throws Exception {
		Class.forName("net.woodstock.rockframework.domain.hibernate.PostInsertEventListener");

		SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.getTransaction().begin();

		Pessoa pessoa = new Pessoa();
		pessoa.setAtivo(Boolean.TRUE);
		pessoa.setIdade(new Integer(100));
		pessoa.setNome("Pessoa 100");

		session.save(pessoa);
		session.getTransaction().commit();
		session.flush();
		session.close();
	}
}
