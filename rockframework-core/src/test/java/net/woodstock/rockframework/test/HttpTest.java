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

import java.io.IOException;

import net.woodstock.rockframework.net.http.HttpClient;
import net.woodstock.rockframework.util.Entry;

import org.apache.commons.httpclient.HttpException;

public class HttpTest {

	public HttpTest() throws HttpException, IOException {
		super();
		HttpClient http = new HttpClient();
		System.out
				.println(http.openXml("http://svn.woodstock.net/index.php", (Entry<String, String>[]) null));
	}

	public static void main(String[] args) {
		try {
			new HttpTest();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
