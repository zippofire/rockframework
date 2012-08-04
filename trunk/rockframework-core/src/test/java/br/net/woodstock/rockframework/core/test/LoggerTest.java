package br.net.woodstock.rockframework.core.test;

import java.util.logging.Logger;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.utils.LogUtils;

public class LoggerTest extends TestCase {

	public void test1() throws Exception {
		Logger log = LogUtils.getSharedLog();
		log.severe("Teste");
	}

}
