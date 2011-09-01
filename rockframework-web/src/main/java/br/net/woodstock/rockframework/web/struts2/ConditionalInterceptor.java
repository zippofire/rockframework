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
package br.net.woodstock.rockframework.web.struts2;

import java.util.HashMap;
import java.util.Map;

import br.net.woodstock.rockframework.util.Assert;


public abstract class ConditionalInterceptor<R> extends Interceptor {

	private static final long	serialVersionUID	= -4699336436483961264L;

	private Map<R, Boolean>		rules;

	private Map<R, Object>		values;

	public ConditionalInterceptor() {
		super();
		this.rules = new HashMap<R, Boolean>();
		this.values = new HashMap<R, Object>();
	}

	protected void addRule(final R rule, final boolean value) {
		Assert.notNull(rule, "rule");
		this.rules.put(rule, Boolean.valueOf(value));
	}

	protected void addRuleValue(final R rule, final Object value) {
		Assert.notNull(rule, "rule");
		this.values.put(rule, value);
	}

	protected boolean containsRule(final R rule) {
		Assert.notNull(rule, "rule");
		return this.rules.containsKey(rule);
	}

	protected boolean getRule(final R rule) {
		Assert.notNull(rule, "rule");
		return this.rules.get(rule).booleanValue();
	}

	protected Object getRuleValue(final R rule) {
		Assert.notNull(rule, "rule");
		return this.values.get(rule);
	}

}
