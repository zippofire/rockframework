package net.woodstock.rockframework.test;

import java.io.FileInputStream;

import junit.framework.TestCase;
import net.woodstock.rockframework.xml.dom.XmlDocument;
import net.woodstock.rockframework.xml.dom.XmlElement;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class XmlText extends TestCase {

	public void test1() throws Exception {
		XmlDocument doc = XmlDocument.read(new FileInputStream("D:/operations.xml"));
		XmlElement root = doc.getRoot();

		for (XmlElement e : root.getElements()) {
			NodeList list = e.getChildNodes();

			for (int i = 0; i < list.getLength(); i++) {
				if (list.item(i).getNodeType() == Node.CDATA_SECTION_NODE) {
					CDATASection cdata = (CDATASection) list.item(i);
					System.out.println("CDATA: " + cdata.getData().trim());
				} else if (list.item(i).getNodeType() == Node.TEXT_NODE) {
					Text text = (Text) list.item(i);
					System.out.println("Text: " + text.getData().trim());
				} else {
					System.out.println(list.item(i).getNodeType() + ": " + list.item(i).getNodeValue());
				}
				System.out.println("XXX: " + list.item(i).getNodeValue());
			}
		}
	}

}
