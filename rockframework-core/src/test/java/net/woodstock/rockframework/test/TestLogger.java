package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.utils.LogUtils;

import org.apache.commons.logging.Log;

public class TestLogger extends TestCase {

	public void test1() throws Exception {
		Log log = LogUtils.getSharedLog();
		log.warn("Teste");
	}

}
