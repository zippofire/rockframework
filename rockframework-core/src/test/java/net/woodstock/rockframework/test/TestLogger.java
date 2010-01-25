package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.logging.SysLogger;

import org.apache.commons.logging.Log;

public class TestLogger extends TestCase {

	public void test1() throws Exception {
		Log log = SysLogger.getLog();
		log.warn("Teste");
	}

}
