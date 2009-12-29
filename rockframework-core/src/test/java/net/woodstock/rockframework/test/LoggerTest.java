package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.logging.SysLogger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggerTest extends TestCase {

	public void test1() throws Exception {
		Log logger = SysLogger.getLogger();
		logger.error("Teste");
	}

	public void xtest2() throws Exception {
		System.out.println(LogFactory.getFactory());
		System.out.println(SysLogger.getLogger());
	}

}
