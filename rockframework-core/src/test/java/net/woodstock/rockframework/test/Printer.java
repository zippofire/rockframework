package net.woodstock.rockframework.test;

import java.lang.reflect.Method;

import junit.framework.TestCase;

public class Printer extends TestCase {

	private static Printer	p;

	private Printer() {
		super();
	}

	@SuppressWarnings("unused")
	private void _print(Integer i) throws Exception {
		System.out.println("Integer " + i);
	}

	@SuppressWarnings("unused")
	private void _print(Long l) throws Exception {
		System.out.println("Long " + l);
	}

	@SuppressWarnings("unused")
	private void _print(Object o) throws Exception {
		System.out.println("Object " + o);
	}

	@SuppressWarnings("unused")
	private void _print(String s) throws Exception {
		System.out.println("String " + s);
	}

	public void print(Integer i) throws Exception {
		System.out.println("Integer X " + i);
	}

	public void print(Object o) throws Exception {
		if (Printer.p == null) {
			Printer.p = new Printer();
		}
		Method m = null;
		try {
			m = Printer.class.getDeclaredMethod("_print", o.getClass());
		} catch (NoSuchMethodException e) {
			m = Printer.class.getDeclaredMethod("_print", Object.class);
		}
		m.invoke(Printer.p, o);
	}

	public void print(String s) throws Exception {
		System.out.println("String X " + s);
	}
}
