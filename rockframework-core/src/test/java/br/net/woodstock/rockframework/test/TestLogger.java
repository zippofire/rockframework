package br.net.woodstock.rockframework.test;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;

import br.net.woodstock.rockframework.utils.LogUtils;

public class TestLogger extends TestCase {

	public void test1() throws Exception {
		Log log = LogUtils.getSharedLog();
		log.warn("Teste");
	}

}
