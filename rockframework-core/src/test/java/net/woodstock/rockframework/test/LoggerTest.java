package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.sys.SysLogger;

import org.apache.commons.logging.Log;

public class LoggerTest extends TestCase {

	public void test1() throws Exception {
		Log logger = SysLogger.getLogger();
		logger.error("Teste");
	}

}
