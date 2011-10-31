package br.net.woodstock.rockframework.test;

import junit.framework.TestCase;

import org.slf4j.Logger;

import br.net.woodstock.rockframework.utils.LogUtils;

public class TestLogger extends TestCase {

	public void test1() throws Exception {
		Logger log = LogUtils.getSharedLog();
		log.warn("Teste");
	}

}
