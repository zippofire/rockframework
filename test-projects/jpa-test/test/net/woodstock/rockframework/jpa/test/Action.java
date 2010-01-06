package net.woodstock.rockframework.jpa.test;

import junit.framework.TestCase;

import net.woodstock.rockframework.domain.business.GenericBusiness;
import net.woodstock.rockframework.domain.business.ValidationResult;
import net.woodstock.rockframework.domain.business.impl.AbstractJPABusiness;
import net.woodstock.rockframework.test.jpa.Email;
import net.woodstock.rockframework.test.jpa.Pessoa;

public class Action extends TestCase {

	private GenericBusiness getBusiness() {
		return new AbstractJPABusiness() {
			//
		};
	}

	public void xtest1() {
		Pessoa p = new Pessoa();
		// p.setNome("");
		// p.setIdade(new Integer(1));
		// p.setAtivo(Boolean.TRUE);
		GenericBusiness business = this.getBusiness();
		ValidationResult result = business.validateSave(p);
		System.out.println(result.getMessage());
	}

	public void test2() {
		Pessoa p = new Pessoa();
		p.setId(new Integer(1));
		Email e = new Email();
		e.setDescricao("xxx@xxx.com");
		e.setPessoa(p);
		GenericBusiness business = this.getBusiness();
		ValidationResult result = business.validateSave(p);
		System.out.println(result.getMessage());
	}

}
