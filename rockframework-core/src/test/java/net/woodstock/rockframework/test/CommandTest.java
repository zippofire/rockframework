package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.runtime.Command;
import net.woodstock.rockframework.runtime.Output;

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
