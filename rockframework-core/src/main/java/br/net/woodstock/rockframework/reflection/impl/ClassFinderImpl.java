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
package br.net.woodstock.rockframework.reflection.impl;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.net.woodstock.rockframework.reflection.ClassFilter;
import br.net.woodstock.rockframework.reflection.ClassFinder;
import br.net.woodstock.rockframework.reflection.ReflectionException;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.util.ZipReader;
import br.net.woodstock.rockframework.utils.ClassLoaderUtils;


@SuppressWarnings("rawtypes")
public class ClassFinderImpl implements ClassFinder {

	private static final String	CLASS_EXTENSION	= ".class";

	private static final String	JAR_PREFIX		= "jar:";

	private String				baseName;

	private ClassFilter			filter;

	private List<Class>			classes;

	public ClassFinderImpl(final String baseName) {
		this(baseName, null);
	}

	public ClassFinderImpl(final String baseName, final ClassFilter filter) {
		super();
		Assert.notEmpty(baseName, "baseName");
		this.baseName = baseName.replaceAll("\\.", "/");
		this.filter = filter;
		this.classes = new ArrayList<Class>();

		try {
			this.init();
		} catch (Exception e) {
			throw new ReflectionException(e);
		}
	}

	private void init() throws IOException, URISyntaxException {
		Collection<URL> urls = ClassLoaderUtils.getResources(this.baseName);
		if (urls != null) {
			for (URL url : urls) {
				if (this.isJarFile(url)) {
					this.addClassFromJar(url);
				} else {
					File dir = new File(url.toURI());
					String urlString = url.getFile();
					String parent = urlString.substring(0, urlString.lastIndexOf(this.baseName));
					if (dir.isDirectory()) {
						this.addClassFromDirectory(dir, parent);
					}
				}
			}
		}
	}

	private boolean isJarFile(final URL url) {
		if (url.toString().startsWith(ClassFinderImpl.JAR_PREFIX)) {
			return true;
		}
		return false;
	}

	private void addClassFromDirectory(final File dir, final String parent) {
		for (File file : dir.listFiles()) {
			if (file.isFile()) {
				String classFile = file.toURI().getPath().replace(parent, "");
				if (this.isValidClass(classFile)) {
					Class clazz = this.getClassFromFile(classFile);
					if ((clazz != null) && (this.isAcceptable(clazz))) {
						this.classes.add(clazz);
					}
				}
			} else if (file.isDirectory()) {
				this.addClassFromDirectory(file, parent);
			}
		}
	}

	private void addClassFromJar(final URL url) throws IOException, URISyntaxException {
		URI uri = ClassLoaderUtils.getURI(url);
		ZipReader reader = new ZipReader(uri.getPath());
		Collection<String> files = reader.getFiles();
		if (files != null) {
			for (String file : files) {
				if (this.isValidClass(file)) {
					Class clazz = this.getClassFromFile(file);
					if ((clazz != null) && (this.isAcceptable(clazz))) {
						this.classes.add(clazz);
					}
				}
			}
		}
	}

	private boolean isValidClass(final String file) {
		if (file.startsWith(this.baseName)) {
			if (file.endsWith(ClassFinderImpl.CLASS_EXTENSION)) {
				return true;
			}
		}
		return false;
	}

	private Class getClassFromFile(final String file) {
		String className = file.replaceAll("/", ".").replace(".class", "");
		try {
			Class c = Class.forName(className);
			return c;
		} catch (ClassNotFoundException e) {
			return null;
		} catch (NoClassDefFoundError e) {
			return null;
		}
	}

	private boolean isAcceptable(final Class clazz) {
		if (this.filter != null) {
			return this.filter.accept(clazz);
		}
		return true;
	}

	@Override
	public Class[] getClasses() {
		return this.classes.toArray(new Class[this.classes.size()]);
	}

}
