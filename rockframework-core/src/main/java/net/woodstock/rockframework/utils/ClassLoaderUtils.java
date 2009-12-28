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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public abstract class ClassLoaderUtils {

	public static final String	FILE_PREFIX		= "file:";

	public static final String	FTP_PREFIX		= "ftp:";

	public static final String	HTTP_PREFIX		= "http:";

	public static final String	JAR_PREFIX		= "jar:";

	public static final char	JAR_SEPARATOR	= '!';

	private ClassLoaderUtils() {
		//
	}

	public static URL getResource(final String name) {
		return ClassLoaderUtils.getResource(Thread.currentThread().getContextClassLoader(), name);
	}

	public static URL getResource(final ClassLoader classLoader, final String name) {
		return classLoader.getResource(name);
	}

	public static Collection<URL> getResources(final String name) throws IOException {
		return ClassLoaderUtils.getResources(Thread.currentThread().getContextClassLoader(), name);
	}

	public static Collection<URL> getResources(final ClassLoader classLoader, final String name) throws IOException {
		Enumeration<URL> urls = classLoader.getResources(name);
		return CollectionUtils.toCollection(urls);
	}

	public static InputStream getResourceAsStream(final String name) throws URISyntaxException, IOException {
		return ClassLoaderUtils.getResourceAsStream(Thread.currentThread().getContextClassLoader(), name);
	}

	public static InputStream getResourceAsStream(final ClassLoader classLoader, final String name) throws URISyntaxException, IOException {
		URL url = classLoader.getResource(name);
		if (url != null) {
			InputStream inputStream = ClassLoaderUtils.getInputStream(url, name);
			return inputStream;
		}
		return null;
	}

	public static Collection<InputStream> getResourcesAsStream(final String name) throws URISyntaxException, IOException {
		return ClassLoaderUtils.getResourcesAsStream(Thread.currentThread().getContextClassLoader(), name);
	}

	public static Collection<InputStream> getResourcesAsStream(final ClassLoader classLoader, final String name) throws URISyntaxException, IOException {
		Collection<URL> urls = ClassLoaderUtils.getResources(classLoader, name);
		Collection<InputStream> collection = new LinkedList<InputStream>();
		if (urls != null) {
			for (URL url : urls) {
				InputStream inputStream = ClassLoaderUtils.getInputStream(url, name);
				if (inputStream != null) {
					collection.add(inputStream);
				}
			}
		}
		return collection;
	}

	public static URI getURI(final URL url) throws URISyntaxException {
		String urlString = url.toString();
		URI uri = null;
		if (urlString.startsWith(ClassLoaderUtils.JAR_PREFIX)) {
			int start = urlString.indexOf(ClassLoaderUtils.FILE_PREFIX);
			int end = urlString.indexOf(ClassLoaderUtils.JAR_SEPARATOR);
			String s = urlString.substring(start, end);
			uri = new URI(s);
		} else {
			uri = new URI(urlString);
		}
		return uri;
	}

	public static InputStream getInputStream(final URL url, final String name) throws URISyntaxException, IOException {
		String urlString = url.toString();
		boolean isJar = urlString.startsWith(ClassLoaderUtils.JAR_PREFIX);
		URI uri = ClassLoaderUtils.getURI(url);
		if (isJar) {
			JarFile file = new JarFile(new File(uri));
			JarEntry entry = file.getJarEntry(name);
			InputStream input = file.getInputStream(entry);
			return input;
		}
		File file = new File(uri);
		InputStream input = new FileInputStream(file);
		return input;
	}

}
