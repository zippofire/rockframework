package br.net.woodstock.rockframework.domain.test.security;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.domain.security.EncodedEntity;

public class EncodedEntityTest extends TestCase {

	public void test1() throws Exception {
		Pessoa pessoa = new Pessoa();
		pessoa.setId(Long.valueOf(1));

		for (int i = 0; i < 10; i++) {
			Pessoa p = new Pessoa();
			System.out.println("ID : " + p.getId());
			System.out.println("--------------------------------------------------------------------------");
		}
	}

	public void test2() throws Exception {
		Pessoa pessoa = new Pessoa();
		pessoa.setId(Long.valueOf(1));

		String[] cids = new String[10];
		for (int i = 0; i < cids.length; i++) {
			String cid = pessoa.getCid();
			cids[i] = cid;
		}

		for (int i = 0; i < cids.length; i++) {
			Pessoa p = new Pessoa();
			p.setCid(cids[i]);
			System.out.println("CID: " + cids[i]);
			System.out.println("ID : " + p.getId());
			System.out.println("--------------------------------------------------------------------------");
		}
	}
	
	public void test3() throws Exception {
		Pessoa pessoa = new Pessoa();
		pessoa.setId(Long.valueOf(1));

		String[] cids = new String[10];
		for (int i = 0; i < cids.length; i++) {
			String cid = pessoa.getCid();
			cids[i] = cid;
		}

		for (int i = 0; i < cids.length; i++) {
			Pessoa p = new Pessoa();
			p.setCid(cids[i]);
			System.out.println("CID: " + cids[i]);
			System.out.println("ID : " + p.getId());
			System.out.println("--------------------------------------------------------------------------");
		}
	}

	public static class Pessoa extends EncodedEntity<Long> {

		private static final long	serialVersionUID	= 1L;

		private Long				id;

		@Override
		public final Long getId() {
			return this.id;
		}

		@Override
		public final void setId(final Long id) {
			this.id = id;
		}

		@Override
		protected String getIdAsString() {
			if (this.id != null) {
				return this.id.toString();
			}
			return null;
		}

		@Override
		protected void setIdAsString(final String id) {
			this.id = Long.valueOf(id);
		}

	}

}
