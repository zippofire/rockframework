package br.net.woodstock.rockframework.test;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.net.woodstock.rockframework.utils.LogUtils;

public class LoggerTest extends TestCase {

	public void test1() throws Exception {
		Logger log = LogUtils.getSharedLog();
		log.error("Teste");
	}

	public void xtest2() throws Exception {
		System.out.println(LoggerFactory.getILoggerFactory());
		System.out.println(LogUtils.getSharedLog());
	}

}
