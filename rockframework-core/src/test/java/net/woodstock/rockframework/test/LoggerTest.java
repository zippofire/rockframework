package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.utils.LogUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggerTest extends TestCase {

	public void test1() throws Exception {
		Log log = LogUtils.getSharedLog();
		log.error("Teste");
	}

	public void xtest2() throws Exception {
		System.out.println(LogFactory.getFactory());
		System.out.println(LogUtils.getSharedLog());
	}

}
