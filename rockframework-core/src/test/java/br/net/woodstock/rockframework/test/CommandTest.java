package br.net.woodstock.rockframework.test;

import br.net.woodstock.rockframework.runtime.Command;
import br.net.woodstock.rockframework.runtime.Output;
import junit.framework.TestCase;

public class CommandTest extends TestCase {

	public void test1() throws Exception {
		Command command = new Command("java -version");
		Output output = command.execute();
		System.out.println("===== Saida =====");
		System.out.println(output.getOut());
		System.out.println("===== Erro =====");
		System.out.println(output.getErr());
	}

}
