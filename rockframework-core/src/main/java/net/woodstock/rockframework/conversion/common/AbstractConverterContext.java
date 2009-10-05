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
package net.woodstock.rockframework.conversion.common;

import java.util.LinkedList;
import java.util.Queue;

import net.woodstock.rockframework.conversion.ConverterContext;
import net.woodstock.rockframework.sys.SysLogger;

import org.apache.commons.logging.Log;

abstract class AbstractConverterContext implements ConverterContext {

	protected static final String	CONTEXT_SEPARATOR	= ".";

	private ConverterContext		parent;

	private String					name;

	private Class<?>				type;

	private Object					object;

	private Queue<Object>			queue;

	public AbstractConverterContext(ConverterContext parent, String name, Class<?> type) {
		super();
		this.parent = parent;
		this.name = name;
		this.type = type;
		this.queue = new LinkedList<Object>();
	}

	// Getters and Setters
	public ConverterContext getParent() {
		return this.parent;
	}

	public void setParent(ConverterContext parent) {
		this.parent = parent;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getType() {
		return this.type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public Object getObject() {
		return this.object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Queue<Object> getQueue() {
		return this.queue;
	}

	public void setQueue(Queue<Object> stack) {
		this.queue = stack;
	}

	// Stack
	@Override
	public boolean isQueued(Object o) {
		if (this.queue.contains(o)) {
			return true;
		}
		if (this.parent != null) {
			return this.parent.isQueued(o);
		}
		return false;
	}

	// Canonical
	public String getCanonicalName() {
		StringBuilder builder = new StringBuilder();
		if (this.parent != null) {
			builder.append(this.parent.getCanonicalName());
			builder.append(AbstractConverterContext.CONTEXT_SEPARATOR);
		}
		builder.append(this.name);
		return builder.toString();
	}

	// Object
	@Override
	public String toString() {
		return this.getCanonicalName();
	}

	// Logger
	public Log getLogger() {
		return SysLogger.getLogger();
	}

}
