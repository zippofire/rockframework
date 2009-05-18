package net.woodstock.rockframework.test;

import java.io.Serializable;

import net.woodstock.rockframework.util.BeanInfo;

public class TestBeanInfo {

	public static void main(String[] args) {
		try {
			Pessoa pessoa = new Pessoa();
			BeanInfo beanInfo = BeanInfo.getBeanInfo(pessoa.getClass());
			beanInfo.getFieldInfo("id").setFieldValue(pessoa, new Integer(1));
			beanInfo.getFieldInfo("nome").setFieldValue(pessoa, "Teste");

			System.out.println(pessoa);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class Pessoa implements Serializable {

		private static final long	serialVersionUID	= 1L;

		protected Integer			id;

		protected String			nome;

		public Integer getId() {
			System.out.println("getId");
			return this.id;
		}

		public void setId(Integer id) {
			System.out.println("setId");
			this.id = id;
		}

		public String getNome() {
			System.out.println("getNome");
			return this.nome;
		}

		public void setNome(String nome) {
			System.out.println("setNome");
			this.nome = nome;
		}

		@Override
		public String toString() {
			return this.id + " => " + this.nome;
		}

	}

}
