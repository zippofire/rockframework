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
package net.woodstock.rockframework.test;

import java.io.File;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import junit.framework.TestCase;

public class PackageTest extends TestCase {

	public void test1() throws Exception {
		String packageName = "javax.servlet";
		File file = new File(Thread.currentThread().getContextClassLoader().getResource(packageName.replaceAll("\\.", "/")).getFile());

		System.out.println(file.getAbsolutePath());
		if ((file.isFile()) || (file.getAbsolutePath().indexOf(".jar!") != -1)) {
			JarFile jar = new JarFile(file);
			Enumeration<JarEntry> entries = jar.entries();
			while (entries.hasMoreElements()) {
				System.out.println(entries.nextElement().getName());
			}
		} else {
			for (String s : file.list()) {
				System.out.println(s);
			}
		}
	}

}
