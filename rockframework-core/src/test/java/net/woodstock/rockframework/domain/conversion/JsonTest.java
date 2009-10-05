package net.woodstock.rockframework.domain.conversion;

import junit.framework.TestCase;
import net.woodstock.rockframework.conversion.ConverterContext;
import net.woodstock.rockframework.conversion.TextConverter;
import net.woodstock.rockframework.conversion.common.BeanConverterContext;
import net.woodstock.rockframework.conversion.json.converters.BeanConverter;

public class JsonTest extends TestCase {

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

	public void testText1() throws Exception {
		Foo foo = this.getFoo();
		foo.setBar(this.getBar());

		ConverterContext context1 = new BeanConverterContext(null, Foo.class);

		TextConverter<Object> converter = new BeanConverter();
		String s = converter.to(context1, foo);
		System.out.println(s);
	}

}
