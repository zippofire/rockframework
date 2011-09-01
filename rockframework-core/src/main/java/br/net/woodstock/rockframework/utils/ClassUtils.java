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
package br.net.woodstock.rockframework.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

public abstract class ClassUtils {

	private static Map<Class<?>, Class<?>>	wrappers;

	static {
		ClassUtils.wrappers = new HashMap<Class<?>, Class<?>>();
		ClassUtils.wrappers.put(boolean.class, Boolean.class);
		ClassUtils.wrappers.put(byte.class, Byte.class);
		ClassUtils.wrappers.put(char.class, Character.class);
		ClassUtils.wrappers.put(double.class, Double.class);
		ClassUtils.wrappers.put(float.class, Float.class);
		ClassUtils.wrappers.put(int.class, Integer.class);
		ClassUtils.wrappers.put(long.class, Long.class);
		ClassUtils.wrappers.put(short.class, Short.class);
	}

	private ClassUtils() {
		//
	}

	public static boolean isClass(final Object o, final Class<?>... classes) {
		return ClassUtils.isClass(o.getClass(), classes);
	}

	public static boolean isClass(final Class<?> c, final Class<?>... classes) {
		for (Class<?> clazz : classes) {
			if (c == clazz) {
				return true;
			}
		}
		return false;
	}

	public static boolean isAssignableFrom(final Object o, final Class<?> c) {
		return c.isAssignableFrom(o.getClass());
	}

	public static boolean isAssignableFrom(final Class<?> c, final Class<?> cc) {
		return cc.isAssignableFrom(c);
	}

	public static boolean isPrimitiveClass(final String name) {
		for (Entry<Class<?>, Class<?>> entry : ClassUtils.wrappers.entrySet()) {
			if (name.equals(entry.getKey().getCanonicalName())) {
				return false;
			}
		}
		return true;
	}

	public static Class<?> getClass(final String name) throws ClassNotFoundException {
		return ClassUtils.getPrimitiveClass(name);
	}

	public static Class<?> getPrimitiveClass(final String name) throws ClassNotFoundException {
		for (Entry<Class<?>, Class<?>> entry : ClassUtils.wrappers.entrySet()) {
			if (name.equals(entry.getKey().getCanonicalName())) {
				return entry.getKey();
			}
		}
		return Class.forName(name);
	}

	public static Class<?> getPrimitiveClass(final Class<?> c) {
		for (Entry<Class<?>, Class<?>> entry : ClassUtils.wrappers.entrySet()) {
			if (c.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return c;
	}

	public static Class<?> getWrapperClass(final Class<?> c) {
		for (Entry<Class<?>, Class<?>> entry : ClassUtils.wrappers.entrySet()) {
			if (c.equals(entry.getKey())) {
				return entry.getValue();
			}
		}
		return c;
	}

	public static Method getMethod(final Class<?> c, final String methodName, final Object... params) throws NoSuchMethodException {
		Class<?>[] paramsTypes = null;
		if (params != null) {
			paramsTypes = new Class<?>[params.length];
			for (int i = 0; i < params.length; i++) {
				if (params[i] != null) {
					paramsTypes[i] = params[i].getClass();
				} else {
					paramsTypes[i] = null;
				}
			}
		} else {
			paramsTypes = new Class<?>[] {};
		}
		return ClassUtils.getMethod(c, methodName, paramsTypes);
	}

	public static Method getMethod(final Class<?> c, final String methodName, final Class<?>... paramTypes) throws NoSuchMethodException {
		Class<?> tmp = c;
		while (tmp != null) {
			try {
				Method m = tmp.getDeclaredMethod(methodName, paramTypes);
				return m;
			} catch (NoSuchMethodException e) {
				tmp = tmp.getSuperclass();
			}
		}
		tmp = c;
		while (tmp != null) {
			Method[] methods = tmp.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals(methodName)) {
					Class<?>[] methodParamsTypes = m.getParameterTypes();
					if (methodParamsTypes.length != paramTypes.length) {
						continue;
					}
					for (int i = 0; i < methodParamsTypes.length; i++) {
						if (!methodParamsTypes[i].isAssignableFrom(paramTypes[i])) {
							continue;
						}
					}
					return m;
				}
			}
			tmp = tmp.getSuperclass();
		}
		throw new NoSuchMethodException(methodName);
	}

	public static boolean isGeneric(final Class<?> clazz) {
		Type type = clazz.getGenericSuperclass();
		// Superclass
		if (type instanceof ParameterizedType) {
			return true;
		}
		// Interface
		for (Type t : clazz.getGenericInterfaces()) {
			if (t instanceof ParameterizedType) {
				return true;
			}
		}
		return false;
	}

	public static Collection<Class<?>> getGenericTypes(final Class<?> clazz) throws ClassNotFoundException {
		Collection<Class<?>> types = new LinkedList<Class<?>>();
		// Superclass
		Type type = clazz.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) type;
			for (Type t : pt.getActualTypeArguments()) {
				String className = t.toString();
				className = className.substring(className.indexOf(' ') + 1);
				Class<?> c = Class.forName(className);
				types.add(c);
			}
		}

		// Interfaces
		for (Type t : clazz.getGenericInterfaces()) {
			if (t instanceof ParameterizedType) {
				ParameterizedType pt = (ParameterizedType) t;
				for (Type tt : pt.getActualTypeArguments()) {
					String className = tt.toString();
					className = className.substring(className.indexOf(' ') + 1);
					Class<?> c = Class.forName(className);
					types.add(c);
				}
			}
		}
		return types;
	}

	public static Collection<Class<?>> getGenericType(final Field f) throws ClassNotFoundException {
		Collection<Class<?>> types = new LinkedList<Class<?>>();

		Type t = f.getGenericType();

		if (t instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) t;

			for (Type tt : pt.getActualTypeArguments()) {
				if (tt instanceof Class) {
					types.add((Class<?>) tt);
				} else if (tt instanceof ParameterizedType) {
					String className = tt.toString().substring(0, tt.toString().indexOf('<'));
					types.add(Class.forName(className));
				}
			}
		}

		return types;
	}

	public static Collection<Class<?>> getClasses(final String packageName) throws IOException, ClassNotFoundException {
		Collection<Class<?>> classes = new LinkedList<Class<?>>();
		URL url = ClassUtils.class.getClassLoader().getResource(packageName.replaceAll("\\.", "/"));
		String fileName = url.getFile();
		Pattern jarPattern = Pattern.compile("\\.[ejsw]ar!");
		Pattern classPattern = Pattern.compile(packageName.replaceAll("\\.", "\\\\.") + "\\." + "[a-zA-Z1-9_$]+\\.class");
		if (jarPattern.matcher(fileName).find()) {
			fileName = fileName.substring(0, fileName.indexOf('!'));
			fileName = fileName.replace("file:/", "");
			fileName = fileName.replaceAll("%20", " ");
			File file = new File(fileName);
			JarFile jar = new JarFile(file);
			Enumeration<JarEntry> entries = jar.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				String entryName = entry.getName().replaceAll("/", ".");
				if (entryName.endsWith(".class")) {
					if (classPattern.matcher(entryName).find()) {
						String className = entryName.substring(0, entryName.indexOf(".class"));
						Class<?> clazz = Class.forName(className);
						classes.add(clazz);
					}
				}
			}
		} else {
			fileName = fileName.replace("file:/", "");
			fileName = fileName.replaceAll("%20", " ");
			File directory = new File(fileName);
			if (directory.isDirectory()) {
				File[] files = directory.listFiles();
				for (File file : files) {
					if ((file.isFile()) && (file.getName().endsWith(".class"))) {
						String className = packageName + "." + file.getName().substring(0, file.getName().indexOf(".class"));
						Class<?> clazz = Class.forName(className);
						classes.add(clazz);
					}
				}
			}
		}
		return classes;
	}
}
