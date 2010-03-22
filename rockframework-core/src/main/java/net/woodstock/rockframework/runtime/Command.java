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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import net.woodstock.rockframework.config.CoreMessage;
import net.woodstock.rockframework.utils.StringUtils;

public class Command implements Serializable {

	private static final long	serialVersionUID	= 3461506172311705253L;

	private String				command;

	private List<String>		subCommands;

	public Command(final String command) {
		this(command, null);
	}

	public Command(final String command, final List<String> subCommands) {
		super();
		this.command = command;
		this.subCommands = subCommands;
	}

	public String getCommand() {
		return this.command;
	}

	public void setCommand(final String command) {
		this.command = command;
	}

	public List<String> getSubCommands() {
		return this.subCommands;
	}

	public void setSubCommands(final List<String> subCommands) {
		this.subCommands = subCommands;
	}

	public Output execute() throws IOException {
		if (StringUtils.isEmpty(this.command)) {
			throw new IllegalStateException(CoreMessage.getInstance().getMessage(CoreMessage.MESSAGE_NOT_EMPTY, "Command"));
		}
		Output output = new Output();
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(this.command);

		if ((this.subCommands != null) && (this.subCommands.size() > 0)) {
			OutputStreamWriter writer = new OutputStreamWriter(process.getOutputStream());
			for (String sc : this.subCommands) {
				if (!StringUtils.isEmpty(sc)) {
					writer.write(sc);
				}
			}
			writer.close();
		}

		InputStreamReader errorStream = new InputStreamReader(process.getErrorStream());
		InputStreamReader inputStream = new InputStreamReader(process.getInputStream());
		BufferedReader readerInput = new BufferedReader(inputStream);
		BufferedReader readerError = new BufferedReader(errorStream);

		String line = readerInput.readLine();
		while (line != null) {
			if (!StringUtils.isEmpty(line)) {
				output.addOut(line.trim());
			}
			line = readerInput.readLine();
		}

		line = readerError.readLine();
		while (line != null) {
			if (!StringUtils.isEmpty(line)) {
				output.addErr(line.trim());
			}
			line = readerError.readLine();
		}

		readerInput.close();
		readerError.close();
		inputStream.close();
		errorStream.close();

		return output;
	}

	public class Output implements Serializable {

		private static final long	serialVersionUID	= -2039121423679516050L;

		private List<String>		out;

		private List<String>		err;

		public Output() {
			this.out = new LinkedList<String>();
			this.err = new LinkedList<String>();
		}

		public List<String> getOut() {
			return this.out;
		}

		public List<String> getErr() {
			return this.err;
		}

		void addOut(final String s) {
			this.out.add(s);
		}

		void addErr(final String s) {
			this.err.add(s);
		}

	}

}
