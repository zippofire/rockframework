package net.woodstock.rockframework.domain.test.persistence;

import java.sql.Connection;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.persistence.util.JDBCPersistenceHelper;

public class JDBCTest extends TestCase {

	public void test1() throws Exception {
		JDBCPersistenceHelper helper = JDBCPersistenceHelper.getInstance();
		Connection c = helper.get();
		helper.close();
	}

}
