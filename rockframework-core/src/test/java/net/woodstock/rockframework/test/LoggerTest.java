package net.woodstock.rockframework.test;

import net.woodstock.rockframework.sys.SysLogger;

import org.apache.commons.logging.Log;

public class LoggerTest {

	public static void main(String[] args) {
		Log logger = SysLogger.getLogger();
		logger.error("Teste");
	}

}
