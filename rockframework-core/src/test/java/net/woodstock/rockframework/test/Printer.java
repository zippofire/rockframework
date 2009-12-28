package net.woodstock.rockframework.test;

import java.lang.reflect.Method;

import junit.framework.TestCase;

public final class Printer extends TestCase {

	private static Printer	p;

	private Printer() {
		super();
	}

	@SuppressWarnings("unused")
	private void printInternal(final Integer i) throws Exception {
		System.out.println("Integer " + i);
	}

	@SuppressWarnings("unused")
	private void printInternal(final Long l) throws Exception {
		System.out.println("Long " + l);
	}

	@SuppressWarnings("unused")
	private void printInternal(final Object o) throws Exception {
		System.out.println("Object " + o);
	}

	@SuppressWarnings("unused")
	private void printInternal(final String s) throws Exception {
		System.out.println("String " + s);
	}

	public void print(final Object o) throws Exception {
		if (Printer.p == null) {
			Printer.p = new Printer();
		}
		Method m = null;
		try {
			m = Printer.class.getDeclaredMethod("printInternal", o.getClass());
		} catch (NoSuchMethodException e) {
			m = Printer.class.getDeclaredMethod("printInternal", Object.class);
		}
		m.invoke(Printer.p, o);
	}

	public void print(final String s) throws Exception {
		System.out.println("String X " + s);
	}
}
