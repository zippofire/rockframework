package net.woodstock.rockframework.test;

import java.io.Serializable;

import junit.framework.TestCase;

public class TestBeanInfo extends TestCase {

	// public void test1() throws Exception {
	// Pessoa pessoa = new Pessoa();
	// BeanInfo beanInfo = BeanInfo.getBeanInfo(pessoa.getClass());
	// beanInfo.getFieldInfo("id").setFieldValue(pessoa, new Integer(1));
	// beanInfo.getFieldInfo("nome").setFieldValue(pessoa, "Teste");
	//
	// System.out.println(pessoa);
	// }

	public static class Pessoa implements Serializable {

		private static final long	serialVersionUID	= 1L;

		private Integer			id;

		private String			nome;

		public Integer getId() {
			System.out.println("getId");
			return this.id;
		}

		public void setId(final Integer id) {
			System.out.println("setId");
			this.id = id;
		}

		public String getNome() {
			System.out.println("getNome");
			return this.nome;
		}

		public void setNome(final String nome) {
			System.out.println("setNome");
			this.nome = nome;
		}

		@Override
		public String toString() {
			return this.id + " => " + this.nome;
		}

	}

}
