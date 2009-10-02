package net.woodstock.rockframework.domain.conversion;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import junit.framework.TestCase;
import net.woodstock.rockframework.conversion.Converter;
import net.woodstock.rockframework.conversion.TextConverter;
import net.woodstock.rockframework.conversion.text.converters.BeanConverter;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorFactory;
import net.woodstock.rockframework.xml.dom.XmlDocument;

public class TextTest extends TestCase {

	public void test1() throws Exception {
		Foo foo = new Foo();
		foo.setId(new Integer(1));
		foo.setName("Lourival Sabino");

		TextConverter<Object> converter = new BeanConverter();
		String s = converter.to(null, foo);
		System.out.println(s);
	}

	public void test2() throws Exception {
		Foo foo = new Foo();
		foo.setId(new Integer(1));
		foo.setName("Lourival Sabino");

		Converter<XmlDocument, Object> converter = new net.woodstock.rockframework.conversion.xml.converters.BeanConverter();
		XmlDocument doc = converter.to(null, foo);
		System.out.println(doc);
	}

	public void xtest2() throws Exception {
		Foo foo = new Foo();
		foo.setId(new Integer(1));
		foo.setName("Lourival Sabino");

		BeanDescriptor descriptor = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(foo.getClass());

		for (PropertyDescriptor p : descriptor.getProperties()) {
			Annotation[] aa = p.getAnnotations();
			System.out.println(p.getName());
			if (aa != null) {
				for (Annotation a : aa) {
					System.out.println("\t" + a);
				}
			}
		}
	}

	public void xtest3() throws Exception {
		Foo foo = new Foo();
		foo.setId(new Integer(1));
		foo.setName("Lourival Sabino");

		Field f = foo.getClass().getDeclaredField("id");

		Annotation[] aa = f.getAnnotations();
		System.out.println(f.getName());
		if (aa != null) {
			for (Annotation a : aa) {
				System.out.println("\t" + a);
			}
		}
	}

}
