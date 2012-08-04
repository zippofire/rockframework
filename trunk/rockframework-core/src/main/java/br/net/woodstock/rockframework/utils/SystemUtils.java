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

public abstract class SystemUtils {

	public static final String	FILE_ENCODING_PROPERTY		= "file.encoding";

	public static final String	FILE_SEPARATOR_PROPERTY		= "file.separator";

	public static final String	JAVA_CLASS_PATH_PROPERTY	= "java.class.path";

	public static final String	JAVA_CLASS_VERSION_PROPERTY	= "java.class.version";

	public static final String	JAVA_COMPILER_PROPERTY		= "java.compiler";

	public static final String	JAVA_EXT_DIRS_PROPERTY		= "java.ext.dirs";

	public static final String	JAVA_HOME_PROPERTY			= "java.home";

	public static final String	JAVA_IO_TMPDIR_PROPERTY		= "java.io.tmpdir";

	public static final String	JAVA_LIBRARY_PATH_PROPERTY	= "java.library.path";

	public static final String	JAVA_VENDOR_PROPERTY		= "java.vendor";

	public static final String	JAVA_VENDOR_URL_PROPERTY	= "java.vendor.url";

	public static final String	JAVA_VERSION_PROPERTY		= "java.version";

	public static final String	JAVA_VM_NAME_PROPERTY		= "java.vm.name";

	public static final String	JAVA_VM_VENDOR_PROPERTY		= "java.vm.vendor";

	public static final String	JAVA_VM_VERSION_PROPERTY	= "java.vm.version";

	public static final String	LINE_SEPARATOR_PROPERTY		= "line.separator";

	public static final String	OS_ARCH_PROPERTY			= "os.arch";

	public static final String	OS_NAME_PROPERTY			= "os.name";

	public static final String	OS_VERSION_PROPERTY			= "os.version";

	public static final String	PATH_SEPARADOR_PROPERTY		= "path.separator";

	public static final String	USER_COUNTRY_PROPERTY		= "user.country";

	public static final String	USER_DIR_PROPERTY			= "user.dir";

	public static final String	USER_HOME_PROPERTY			= "user.home";

	public static final String	USER_LANGUAGE_PROPERTY		= "user.language";

	public static final String	USER_NAME_PROPERTY			= "user.name";

	public static final String	USER_TIMEZONE_PROPERTY		= "user.timezone";

	public static final String	USER_VARIANT_PROPERTY		= "user.variant";

	private SystemUtils() {
		//
	}

	public static String getEnvironment(final String name) {
		return System.getenv(name);
	}

	public static String getProperty(final String name) {
		return System.getProperty(name);
	}

	public static void setProperty(final String name, final String value) {
		System.setProperty(name, value);
	}

}
