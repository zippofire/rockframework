package net.woodstock.rockframework.test.struts;

import java.io.File;

import junit.framework.TestCase;

import org.apache.commons.digester.Digester;
import org.apache.struts.config.ConfigRuleSet;

public class DigesterTest extends TestCase {

	public void test1() throws Exception {
		Digester d = new Digester();
		d = new Digester();
		d.setNamespaceAware(true);
		d.setValidating(true);
		d.setUseContextClassLoader(true);
		d.addRuleSet(new ConfigRuleSet());

		File file = new File("C:/Documents and Settings/lourival.junior/workspace/rockframework/test-projects/struts-test/web/WEB-INF/struts-config.xml");
		d.parse(file);
	}
}
