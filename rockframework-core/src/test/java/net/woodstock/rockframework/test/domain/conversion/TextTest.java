package net.woodstock.rockframework.test.domain.conversion;

import junit.framework.TestCase;
import net.woodstock.rockframework.conversion.ConverterContext;
import net.woodstock.rockframework.conversion.TextConverter;
import net.woodstock.rockframework.conversion.common.BeanConverterContext;
import net.woodstock.rockframework.conversion.text.TextConverterFactory;

public class TextTest extends TestCase {

	private Foo getFoo() {
		Foo foo = new Foo();
		foo.setId(new Integer(1));
		foo.setName("Foo");
		return foo;
	}

	public void xtestText1() throws Exception {
		Foo foo = this.getFoo();
		// foo.setBar(this.getBar());

		ConverterContext context1 = new BeanConverterContext(null, "foo", Foo.class);

		TextConverter<Object> converter = TextConverterFactory.getInstance().getConverter();
		String s = converter.to(context1, foo);
		System.out.println(s);
	}

	public void testText2() throws Exception {
		Foo foo1 = this.getFoo();
		// foo1.setBar(this.getBar());

		ConverterContext context1 = new BeanConverterContext(null, "foo", Foo.class);

		TextConverter<Object> converter = TextConverterFactory.getInstance().getConverter();
		String s = converter.to(context1, foo1);

		ConverterContext context2 = new BeanConverterContext(null, "foo", Foo.class);

		Foo foo2 = (Foo) converter.from(context2, s);

		System.out.println(foo1);
		System.out.println(foo2);
	}

}
