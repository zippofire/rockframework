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
package br.net.woodstock.rockframework.runtime;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.Collection;
import java.util.Scanner;

import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.ConditionUtils;
import br.net.woodstock.rockframework.utils.SystemUtils;


public class Command implements Serializable {

	private static final long	serialVersionUID	= 3461506172311705253L;

	private static final String	LINE_SEPARATOR		= SystemUtils.getProperty(SystemUtils.LINE_SEPARATOR_PROPERTY);

	private String				command;

	private Collection<String>	subCommands;

	public Command(final String command) {
		this(command, null);
	}

	public Command(final String command, final Collection<String> subCommands) {
		super();
		Assert.notEmpty(command, "command");
		this.command = command;
		this.subCommands = subCommands;
	}

	public Output execute() throws IOException {
		Assert.notEmpty(this.command, "command");

		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(this.command);

		if (ConditionUtils.isNotEmpty(this.subCommands)) {
			Writer writer = new OutputStreamWriter(process.getOutputStream());
			for (String sc : this.subCommands) {
				if (ConditionUtils.isNotEmpty(sc)) {
					writer.write(sc);
				}
			}
			writer.close();
		}

		StringBuilder inputBuilder = new StringBuilder();
		StringBuilder errorBuilder = new StringBuilder();

		Scanner inputStream = new Scanner(process.getInputStream());
		Scanner errorStream = new Scanner(process.getErrorStream());

		while (inputStream.hasNextLine()) {
			String line = inputStream.nextLine();
			if (ConditionUtils.isNotEmpty(line)) {
				inputBuilder.append(line.trim());
				inputBuilder.append(Command.LINE_SEPARATOR);
			}
		}

		while (errorStream.hasNextLine()) {
			String line = errorStream.nextLine();
			if (ConditionUtils.isNotEmpty(line)) {
				errorBuilder.append(line.trim());
				errorBuilder.append(Command.LINE_SEPARATOR);
			}
		}

		inputStream.close();
		errorStream.close();

		Output output = new OutputImpl(inputBuilder.toString(), errorBuilder.toString());
		return output;
	}

}
