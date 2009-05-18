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

import java.io.FileWriter;

public class CharsetTest {

	public static void main(String[] args) {
		try {
			String nomeUtf = new String("J�nior".getBytes(), "utf-8");
			String nomeIso = new String("J�nior".getBytes(), "iso-8859-1");

			FileWriter writerUtf = new FileWriter("c:/temp/writerUtf.txt");
			FileWriter writerIso = new FileWriter("c:/temp/writerIso.txt");

			writerUtf.write(nomeUtf);
			writerIso.write(nomeIso);

			writerUtf.close();
			writerIso.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
