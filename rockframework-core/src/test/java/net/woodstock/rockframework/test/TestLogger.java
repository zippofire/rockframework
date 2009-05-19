package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.xml.dom.XmlDocument;

public class TestLogger extends TestCase {

	public void test1() throws Exception {
		// Log log = SysLogger.getLogger();
		// log.warn("Teste");

		XmlDocument doc = new XmlDocument("teste");
		doc.getRoot().addElement("valor", "#&denuncia@$");
		System.out.println(doc);
	}

}
