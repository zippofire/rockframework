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
package net.woodstock.rockframework.runtime;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import net.woodstock.rockframework.utils.StringUtils;

public abstract class Command {

	private Command() {
		//
	}

	public static CommandOutput execute(final String[] cmd) throws IOException {
		CommandOutput output = new CommandOutput();
		String line;
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(cmd);

		InputStreamReader errorStream = new InputStreamReader(process.getErrorStream());
		InputStreamReader inputStream = new InputStreamReader(process.getInputStream());
		BufferedReader readerInput = new BufferedReader(inputStream);
		BufferedReader readerError = new BufferedReader(errorStream);

		line = readerInput.readLine();
		while (line != null) {
			if (!StringUtils.isEmpty(line)) {
				output.addOut(line.trim());
			}
			line = readerInput.readLine();
		}

		line = readerError.readLine();
		while (line != null) {
			if (!StringUtils.isEmpty(line)) {
				output.addOut(line.trim());
			}
			line = readerError.readLine();
		}

		readerInput.close();
		readerError.close();
		inputStream.close();
		errorStream.close();

		return output;
	}

	public static CommandOutput execute(final String[] cmd, final String[] subcmd) throws IOException {
		CommandOutput output = new CommandOutput();
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(cmd);

		OutputStreamWriter outputStream = new OutputStreamWriter(process.getOutputStream());
		BufferedWriter writer = new BufferedWriter(outputStream);
		for (String sc : subcmd) {
			if (!StringUtils.isEmpty(sc)) {
				writer.write(sc);
			}
		}
		writer.close();
		outputStream.close();

		InputStreamReader errorStream = new InputStreamReader(process.getErrorStream());
		InputStreamReader inputStream = new InputStreamReader(process.getInputStream());
		BufferedReader readerInput = new BufferedReader(inputStream);
		BufferedReader readerError = new BufferedReader(errorStream);

		String line;
		while ((line = readerInput.readLine()) != null) {
			output.addOut(line.trim());
		}
		while ((line = readerError.readLine()) != null) {
			output.addErr(line.trim());
		}

		readerInput.close();
		readerError.close();
		inputStream.close();
		errorStream.close();

		return output;
	}

}
