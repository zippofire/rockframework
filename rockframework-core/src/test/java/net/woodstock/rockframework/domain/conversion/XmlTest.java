package net.woodstock.rockframework.domain.conversion;

import junit.framework.TestCase;
import net.woodstock.rockframework.conversion.Converter;
import net.woodstock.rockframework.conversion.ConverterContext;
import net.woodstock.rockframework.conversion.common.BeanConverterContext;
import net.woodstock.rockframework.conversion.xml.XmlConverterFactory;
import net.woodstock.rockframework.xml.dom.XmlElement;

public class XmlTest extends TestCase {

	private Foo getFoo() {
		Foo foo = new Foo();
		foo.setId(new Integer(1));
		foo.setName("Foo");
		return foo;
	}

	private Bar getBar() {
		Bar bar = new Bar();
		bar.setId(new Integer(1));
		bar.setName("Bar");
		return bar;
	}

	public void xtestXml1() throws Exception {
		Foo foo = this.getFoo();
		// foo.setBar(this.getBar());

		ConverterContext context1 = new BeanConverterContext(null, "foo", Foo.class);

		Converter<XmlElement, Object> converter = XmlConverterFactory.getInstance().getConverter();
		XmlElement e = converter.to(context1, foo);
		System.out.println(e);
	}

	public void testXml2() throws Exception {
		Foo foo1 = this.getFoo();
		// foo1.setBar(this.getBar());

		Converter<XmlElement, Object> converter = XmlConverterFactory.getInstance().getConverter();
		XmlElement e = converter.to(new BeanConverterContext(null, "foo", Foo.class), foo1);

		Foo foo2 = (Foo) converter.from(new BeanConverterContext(null, "foo", Foo.class), e);

		System.out.println(foo1);
		System.out.println(foo2);

		System.out.println(converter.to(new BeanConverterContext(null, "foo", Foo.class), foo1));
		System.out.println(converter.to(new BeanConverterContext(null, "foo", Foo.class), foo2));
	}

}
