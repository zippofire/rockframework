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

	public static URL getResource(String name) {
		return ClassLoaderUtils.getResource(ClassLoaderUtils.class.getClassLoader(), name);
	}

	public static URL getResource(ClassLoader loader, String name) {
		return loader.getResource(name);
	}

	public static InputStream getResourceAsStream(String name) throws URISyntaxException, IOException {
		return ClassLoaderUtils.getResourceAsStream(Thread.currentThread().getContextClassLoader(), name);
	}

	public static InputStream getResourceAsStream(ClassLoader loader, String name) throws URISyntaxException,
			IOException {
		URL url = loader.getResource(name);
		if (url != null) {
			String s = url.toString();
			boolean isJar = s.startsWith(ClassLoaderUtils.JAR_PREFIX);
			URI uri = ClassLoaderUtils.getURI(s);
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
		return null;
	}

	public static URI getURI(URL url) throws URISyntaxException {
		return ClassLoaderUtils.getURI(url.toString());
	}

	public static URI getURI(String url) throws URISyntaxException {
		URI uri = null;
		if (url.startsWith(ClassLoaderUtils.JAR_PREFIX)) {
			int start = url.indexOf(ClassLoaderUtils.FILE_PREFIX);
			int end = url.indexOf(ClassLoaderUtils.JAR_SEPARATOR);
			String s = url.substring(start, end);
			uri = new URI(s);
		} else {
			uri = new URI(url);
		}
		return uri;
	}

}
