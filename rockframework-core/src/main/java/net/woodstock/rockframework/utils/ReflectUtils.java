/*
 * This file is part of rockframework.
 * 
 * rockframework is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * rockframework is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>;.
 */
package net.woodstock.rockframework.utils;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@Deprecated
public abstract class ReflectUtils {

	private ReflectUtils() {
		//
	}

	public static void printClassDeclaration(final Object o) {
		ReflectUtils.printClassDeclaration(o.getClass(), System.out);
	}

	public static void printClassDeclaration(final Object o, final PrintStream out) {
		ReflectUtils.printClassDeclaration(o.getClass(), out);
	}

	public static void printClassDeclaration(final Class<?> c, final PrintStream out) {
		StringBuilder s = new StringBuilder();
		int m = c.getModifiers();
		s.append(Modifier.isPrivate(m) ? "private " : StringUtils.BLANK);
		s.append(Modifier.isProtected(m) ? "protected " : StringUtils.BLANK);
		s.append(Modifier.isPublic(m) ? "public " : StringUtils.BLANK);
		s.append(Modifier.isAbstract(m) ? "abstract " : StringUtils.BLANK);
		s.append(Modifier.isFinal(m) ? "final " : StringUtils.BLANK);
		s.append(Modifier.isInterface(m) ? "interface " : "class ");
		s.append(c.getSimpleName());
		s.append(" extends ");
		s.append(c.getSuperclass().getSimpleName());

		Class<?>[] i = c.getInterfaces();
		if (i.length > 0) {
			s.append(" implements ");
			for (int j = 0; j < i.length; j++) {
				s.append(i[j].getSimpleName());
				if (j + 1 < i.length) {
					s.append(", ");
				}
			}
		}

		s.append("{ }");
		out.println(s);
	}

	public static void printConstructors(final Object o) {
		ReflectUtils.printConstructors(o.getClass(), System.out);
	}

	public static void printConstructors(final Object o, final PrintStream out) {
		ReflectUtils.printConstructors(o.getClass(), out);
	}

	public static void printConstructors(final Class<?> c, final PrintStream out) {
		Constructor<?>[] con = c.getDeclaredConstructors();
		for (Constructor<?> t : con) {
			int m = t.getModifiers();
			StringBuilder s = new StringBuilder();
			s.append(Modifier.isPrivate(m) ? "private " : StringUtils.BLANK);
			s.append(Modifier.isProtected(m) ? "protected " : StringUtils.BLANK);
			s.append(Modifier.isPublic(m) ? "public " : StringUtils.BLANK);
			s.append(c.getSimpleName());
			s.append("(");
			Class<?>[] p = t.getParameterTypes();
			for (int i = 0; i < p.length; i++) {
				s.append(p[i].getSimpleName());
				s.append(" arg");
				s.append(i);
				if (i + 1 < p.length) {
					s.append(", ");
				}
			}
			s.append(");");
			out.println(s);
		}
	}

	public static void printFields(final Object o) {
		ReflectUtils.printFields(o.getClass(), System.out);
	}

	public static void printFields(final Object o, final PrintStream out) {
		ReflectUtils.printFields(o.getClass(), out);
	}

	public static void printFields(final Class<?> c, final PrintStream out) {
		Field[] f = c.getDeclaredFields();
		for (Field ff : f) {
			int m = ff.getModifiers();
			StringBuilder s = new StringBuilder();
			s.append(Modifier.isPrivate(m) ? "private " : StringUtils.BLANK);
			s.append(Modifier.isProtected(m) ? "protected " : StringUtils.BLANK);
			s.append(Modifier.isPublic(m) ? "public " : StringUtils.BLANK);
			s.append(Modifier.isStatic(m) ? "static " : StringUtils.BLANK);
			s.append(Modifier.isFinal(m) ? "final " : StringUtils.BLANK);
			s.append(Modifier.isTransient(m) ? "transient " : StringUtils.BLANK);
			s.append(Modifier.isVolatile(m) ? "volatile " : StringUtils.BLANK);
			s.append(ff.getType());
			s.append(" ");
			s.append(ff.getName());
			s.append(";");
			out.println(s);
		}
	}

	public static void printHierarchy(final Object o) {
		ReflectUtils.printHierarchy(o, System.out);
	}

	public static void printHierarchy(final Object o, final PrintStream out) {
		ReflectUtils.printHierarchy(o.getClass(), out);
	}

	public static void printHierarchy(final Class<?> c, final PrintStream out) {
		ReflectUtils.printHierarchy(StringUtils.BLANK, c, out);
	}

	private static void printHierarchy(final String prefix, final Class<?> c, final PrintStream out) {
		Class<?> cc = c.getSuperclass();
		if (cc != Object.class) {
			ReflectUtils.printHierarchy(prefix + "\t", cc, out);
		} else {
			out.println(prefix + "\t" + cc.getName());
		}
		out.println(prefix + c.getName());
	}

	public static void printInterfaces(final Object o) {
		ReflectUtils.printInterfaces(o.getClass(), System.out);
	}

	public static void printInterfaces(final Object o, final PrintStream out) {
		ReflectUtils.printInterfaces(o.getClass(), out);
	}

	public static void printInterfaces(final Class<?> c, final PrintStream out) {
		Class<?>[] i = c.getInterfaces();
		for (Class<?> ii : i) {
			out.println(ii.getName());
		}
	}

	public static void printMethods(final Object o) {
		ReflectUtils.printMethods(o.getClass(), System.out);
	}

	public static void printMethods(final Object o, final PrintStream out) {
		ReflectUtils.printMethods(o.getClass(), out);
	}

	public static void printMethods(final Class<?> c, final PrintStream out) {
		Method[] m = c.getDeclaredMethods();
		for (Method mm : m) {
			StringBuilder s = new StringBuilder();
			s.append(Modifier.isPrivate(mm.getModifiers()) ? "private " : StringUtils.BLANK);
			s.append(Modifier.isProtected(mm.getModifiers()) ? "protected " : StringUtils.BLANK);
			s.append(Modifier.isPublic(mm.getModifiers()) ? "public " : StringUtils.BLANK);
			s.append(Modifier.isStatic(mm.getModifiers()) ? "static " : StringUtils.BLANK);
			s.append(Modifier.isSynchronized(mm.getModifiers()) ? "synchronized " : StringUtils.BLANK);
			s.append(Modifier.isFinal(mm.getModifiers()) ? "final " : StringUtils.BLANK);
			s.append(Modifier.isNative(mm.getModifiers()) ? "native " : StringUtils.BLANK);
			s.append(mm.getReturnType().getSimpleName() + " ");
			s.append(mm.getName());
			s.append("(");
			Class<?>[] p = mm.getParameterTypes();
			for (int i = 0; i < p.length; i++) {
				s.append(p[i].getSimpleName());
				s.append(" arg");
				s.append(i);
				if (i + 1 < p.length) {
					s.append(", ");
				}
			}
			s.append(");");
			out.println(s);
		}
	}

}
