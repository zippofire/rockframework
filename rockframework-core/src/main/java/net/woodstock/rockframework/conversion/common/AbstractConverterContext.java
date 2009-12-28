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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Pattern;

import net.woodstock.rockframework.conversion.ConverterContext;
import net.woodstock.rockframework.sys.SysLogger;
import net.woodstock.rockframework.utils.StringUtils;

import org.apache.commons.logging.Log;

abstract class AbstractConverterContext implements ConverterContext {

	protected static final String	CONTEXT_SEPARATOR	= ".";

	private ConverterContext		parent;

	private String					name;

	private Class<?>				type;

	private Object					object;

	private Queue<Object>			queue;

	private Collection<String>		ignored;

	public AbstractConverterContext(final ConverterContext parent, final String name, final Class<?> type) {
		super();
		if (StringUtils.isEmpty(name)) {
			throw new IllegalArgumentException("Name must be not null");
		}
		if (StringUtils.isEmpty(name)) {
			throw new IllegalArgumentException("Type must be not null");
		}

		this.parent = parent;
		this.name = name;
		this.type = type;
		this.queue = new LinkedList<Object>();
		this.ignored = new ArrayList<String>();
	}

	// Getters and Setters
	public ConverterContext getParent() {
		return this.parent;
	}

	public void setParent(final ConverterContext parent) {
		this.parent = parent;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Class<?> getType() {
		return this.type;
	}

	public void setType(final Class<?> type) {
		this.type = type;
	}

	public Object getObject() {
		return this.object;
	}

	public void setObject(final Object object) {
		this.object = object;
	}

	public Queue<Object> getQueue() {
		return this.queue;
	}

	public void setQueue(final Queue<Object> stack) {
		this.queue = stack;
	}

	public Collection<String> getIgnored() {
		return this.ignored;
	}

	public void setIgnored(final Collection<String> ignored) {
		this.ignored = ignored;
	}

	// Stack
	@Override
	public boolean isQueued(final Object o) {
		if (this.queue.contains(o)) {
			return true;
		}
		if (this.parent != null) {
			return this.parent.isQueued(o);
		}
		return false;
	}

	// Ignore
	@Override
	public boolean isIgnored() {
		String canonicalName = this.getCanonicalName();
		Collection<String> ignoreds = this.getIgnoredRecursive();
		for (String ignored : ignoreds) {
			if (Pattern.matches(ignored, canonicalName)) {
				// this.getLogger().info("Context Ignored: " + canonicalName);
				return true;
			}
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
		if (!StringUtils.isEmpty(this.name)) {
			builder.append(this.name);
		}
		return builder.toString();
	}

	private Collection<String> getIgnoredRecursive() {
		Collection<String> collection = new ArrayList<String>();
		collection.addAll(this.ignored);
		if (this.parent != null) {
			AbstractConverterContext context = (AbstractConverterContext) this.parent;
			collection.addAll(context.getIgnoredRecursive());
		}
		return collection;
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
