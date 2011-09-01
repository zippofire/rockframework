package br.net.woodstock.rockframework.domain.test.persistence;

import java.sql.Connection;

import br.net.woodstock.rockframework.domain.persistence.orm.util.JDBCPersistenceHelper;

import junit.framework.TestCase;

public class JDBCTest extends TestCase {

	public void test1() throws Exception {
		JDBCPersistenceHelper helper = JDBCPersistenceHelper.getInstance();
		Connection c = helper.get();
		System.out.println(c);
		helper.close();
	}

}
