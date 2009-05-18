package net.woodstock.rockframework.test;

import java.util.Collection;
import java.util.HashMap;

import net.woodstock.rockframework.utils.ClassUtils;

public class GenericTest {

	private static void print(Object o) throws Exception {
		Class<?> clazz = o.getClass();
		Collection<Class<?>> collection = ClassUtils.getGenericTypes(clazz);

		System.out.println(clazz);
		for (Class<?> c : collection) {
			System.out.println("\t" + c.getCanonicalName());
		}
	}

	public static void main(String[] args) {
		try {
			GenericTest.print(new Teste<String>("Teste"));
			GenericTest.print(new HashMap<String, String>());
			// print(new Teste<Object>());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	static class Teste<T> {

		T	value;

		public Teste() {
			super();
		}

		public Teste(T value) {
			this.value = value;
		}

		public T getValue() {
			return this.value;
		}

		public void setValue(T value) {
			this.value = value;
		}
	}

}
