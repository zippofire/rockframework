package net.woodstock.rockframework.json;

import java.util.HashSet;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.pojo.converter.JsonConverter;

public class TestJavascript extends TestCase {

	private Telefone getTelefone(Pessoa pessoa, String ddd, String numero) {
		Telefone telefone = new Telefone();
		telefone.setDdd(ddd);
		telefone.setId(new Integer(1));
		telefone.setNumero(numero);
		telefone.setTipo(new TipoTelefone(new Integer(1)));
		telefone.setPessoa(pessoa);

		return telefone;
	}

	private Pessoa getPessoa(String nome) {
		Pessoa pessoa = new Pessoa();
		pessoa.setId(new Integer(1));
		pessoa.setNome(nome);
		pessoa.setTelefones(new HashSet<Telefone>());

		return pessoa;
	}

	private Empresa getEmpresa(String nome) {
		Empresa empresa = new Empresa();
		empresa.setId(new Integer(1));
		empresa.setNome(nome);
		empresa.setPessoas(new HashSet<Pessoa>());

		return empresa;

	}

	public void xtestPessoa() throws Exception {
		Pessoa pessoa = getPessoa("Teste");
		pessoa.getTelefones().add(getTelefone(pessoa, "61", "34754728"));
		pessoa.getTelefones().add(getTelefone(pessoa, "61", "92310557"));

		System.out.println(new JsonConverter().to(pessoa));
	}

	public void testEmpresa() throws Exception {
		Empresa empresa = getEmpresa("Teste");

		Pessoa pessoa1 = getPessoa("Teste 1");
		pessoa1.getTelefones().add(getTelefone(pessoa1, "61", "34754728"));
		pessoa1.getTelefones().add(getTelefone(pessoa1, "61", "92310557"));

		Pessoa pessoa2 = getPessoa("Teste 2");
		pessoa2.getTelefones().add(getTelefone(pessoa2, "61", "34754728"));
		pessoa2.getTelefones().add(getTelefone(pessoa2, "61", "92310557"));

		empresa.getPessoas().add(pessoa1);
		empresa.getPessoas().add(pessoa2);

		System.out.println(new JsonConverter().to(empresa));
	}
}
