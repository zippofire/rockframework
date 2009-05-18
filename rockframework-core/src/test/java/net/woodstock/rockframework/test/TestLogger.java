package net.woodstock.rockframework.test;

import net.woodstock.rockframework.xml.dom.XmlDocument;

public class TestLogger {

	public static void main(String[] args) {
		try {
			// Log log = SysLogger.getLogger();
			// log.warn("Teste");

			XmlDocument doc = new XmlDocument("teste");
			doc.getRoot().addElement("valor", "#&denuncia@$");
			System.out.println(doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
