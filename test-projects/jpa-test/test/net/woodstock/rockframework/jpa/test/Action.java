package net.woodstock.rockframework.jpa.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.test.jpa.Email;
import net.woodstock.rockframework.test.jpa.Pessoa;
import net.woodstock.rockframework.test.jpa.negocio.PessoaBusiness;
import net.woodstock.rockframework.test.jpa.negocio.impl.PessoaBusinessImpl;
import net.woodstock.rockframework.test.jpa.servico.GenericService;
import net.woodstock.rockframework.test.jpa.servico.PessoaService;

public class Action extends TestCase {

	private PessoaService	pessoaService;

	private GenericService	genericService;

	public void xxxxx() {
		this.pessoaService.save(null);
		Pessoa p = this.pessoaService.get(null);

		this.genericService.delete(null);
		this.genericService.update(null);
	}

	public void xtest1() {
		Pessoa p = new Pessoa();
		p.setNome("");
		p.setIdade(new Integer(1));
		p.setAtivo(Boolean.TRUE);
		PessoaBusiness business = new PessoaBusinessImpl();
		business.validarInserir(p);
	}

	public void test2() {
		Pessoa p = new Pessoa();
		p.setId(new Integer(1));
		Email email = new Email();
		email.setDescricao("xxx@xxx.com");
		//email.setPessoa(p);
		PessoaBusiness business = new PessoaBusinessImpl();
		business.validarInserir(p);
	}

}
