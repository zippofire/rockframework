package net.woodstock.rockframework.domain.conversion;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;
import net.woodstock.rockframework.conversion.ConverterContext;
import net.woodstock.rockframework.conversion.TextConverter;
import net.woodstock.rockframework.conversion.common.BeanConverterContext;
import net.woodstock.rockframework.conversion.json.JsonConverterFactory;

public class JsonTest extends TestCase {

	private Foo getFoo() {
		Foo foo = new Foo();
		foo.setId(new Integer(1));
		foo.setName("Foo");
		foo.setBars(new ArrayList<Bar>());
		return foo;
	}

	private Bar getBar(Integer id, String name, Foo foo) {
		Bar bar = new Bar();
		bar.setId(id);
		bar.setName(name);
		bar.setFoo(foo);
		return bar;
	}

	private Baz getBaz(Integer id, String name, Bar bar) {
		Baz baz = new Baz();
		baz.setId(id);
		baz.setName(name);
		baz.setBar(bar);
		return baz;
	}

	public void xtestText1() throws Exception {
		Foo foo = this.getFoo();
		for (int i = 0; i < 1; i++) {
			Bar bar = this.getBar(new Integer(i), "Bar " + i, foo);
			bar.setBaz(this.getBaz(new Integer(i), "Baz " + i, bar));
			foo.getBars().add(bar);
		}

		ConverterContext context1 = new BeanConverterContext(null, "foo", Foo.class);
		// context1.getIgnored().add("foo\\.bars\\..*");
		// context1.getIgnored().add(".*\\.(id|name|value)");

		TextConverter<Object> converter = JsonConverterFactory.getInstance().getConverter();
		String s = converter.to(context1, foo);
		System.out.println(s);
	}

	public void testText2() throws Exception {
		Collection<Bar> bars = new ArrayList<Bar>();
		for (int i = 0; i < 15; i++) {
			Bar bar = this.getBar(new Integer(i), "Bar " + i, null);
			bar.setBaz(this.getBaz(new Integer(i), "Baz " + i, bar));
			bars.add(bar);
		}

		ConverterContext context1 = new BeanConverterContext(null, "bars", bars.getClass());
		// context1.getIgnored().add("foo\\.bars\\..*");
		// context1.getIgnored().add(".*\\.(id|name|value)");

		TextConverter<Object> converter = JsonConverterFactory.getInstance().getConverter();
		String s = converter.to(context1, bars);
		System.out.println(s);
	}

}
