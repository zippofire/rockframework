package net.woodstock.rockframework.test.reflection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;
import net.woodstock.rockframework.reflection.ClassFinder;
import net.woodstock.rockframework.reflection.impl.AnnotationClassFilter;
import net.woodstock.rockframework.reflection.impl.AssignableClassFilter;
import net.woodstock.rockframework.reflection.impl.ClassFinderImpl;

public class ClassSearchTest extends TestCase {

	public void xtest1() throws Exception {
		List<String> list = new ArrayList<String>();
		for (Package pkg : Package.getPackages()) {
			list.add(pkg.getName());
		}
		Collections.sort(list);
		for (String s : list) {
			System.out.println(s);
		}
	}

	@SuppressWarnings("rawtypes")
	public void xtest2() throws Exception {
		String pattern = "javax";
		ClassFinder finder = new ClassFinderImpl(pattern, null);

		for (Class c : finder.getClasses()) {
			System.out.println(c);
		}
	}

	@SuppressWarnings("rawtypes")
	public void test3() throws Exception {
		String pattern = "net";
		ClassFinder finder = new ClassFinderImpl(pattern, new AssignableClassFilter(Cloneable.class));

		for (Class c : finder.getClasses()) {
			System.out.println(c);
		}
	}

	@SuppressWarnings("rawtypes")
	public void xtest4() throws Exception {
		String pattern = "net.woodstock";
		ClassFinder finder = new ClassFinderImpl(pattern, new AnnotationClassFilter(SuppressWarnings.class));

		for (Class c : finder.getClasses()) {
			System.out.println(c);
		}
	}
}
