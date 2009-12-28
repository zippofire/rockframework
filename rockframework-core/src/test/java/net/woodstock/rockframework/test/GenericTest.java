package net.woodstock.rockframework.test;

import java.util.Collection;
import java.util.HashMap;

import junit.framework.TestCase;
import net.woodstock.rockframework.utils.ClassUtils;

public class GenericTest extends TestCase {

	private static void print(final Object o) throws Exception {
		Class<?> clazz = o.getClass();
		Collection<Class<?>> collection = ClassUtils.getGenericTypes(clazz);

		System.out.println(clazz);
		for (Class<?> c : collection) {
			System.out.println("\t" + c.getCanonicalName());
		}
	}

	public void test1() throws Exception {
		GenericTest.print(new Teste<String>("Teste"));
		GenericTest.print(new HashMap<String, String>());
		// print(new Teste<Object>());
	}

	static class Teste<T> {

		private T	value;

		public Teste() {
			super();
		}

		public Teste(final T value) {
			this.value = value;
		}

		public T getValue() {
			return this.value;
		}

		public void setValue(final T value) {
			this.value = value;
		}
	}

}
