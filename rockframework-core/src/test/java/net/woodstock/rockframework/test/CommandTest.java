package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.runtime.Command;
import net.woodstock.rockframework.runtime.Command.Output;

public class CommandTest extends TestCase {

	public void test1() throws Exception {
		Command command = new Command("java -version");
		Output output = command.execute();
		for (String out : output.getOut()) {
			System.out.println(out);
		}
		for (String err : output.getErr()) {
			System.out.println(err);
		}
	}

}
