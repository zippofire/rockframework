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
package net.woodstock.rockframework.web.struts;

import net.woodstock.rockframework.sys.SysLogger;

import org.apache.commons.logging.Log;
import org.apache.struts.action.DynaActionForm;

public abstract class BaseDynaForm extends DynaActionForm {

	private static final long	serialVersionUID	= -5299608278184306255L;

	protected Log getLogger() {
		return SysLogger.getLogger();
	}

	public Byte getByte(String name) {
		Object o = this.get(name);
		if ((o != null) && (!(o instanceof Byte))) {
			o = new Byte(o.toString());
		}
		return (Byte) o;
	}

	public Character getChar(String name) {
		Object o = this.get(name);
		if ((o != null) && (!(o instanceof Character))) {
			o = new Character(o.toString().charAt(0));
		}
		return (Character) o;
	}

	public Double getDouble(String name) {
		Object o = this.get(name);
		if ((o != null) && (!(o instanceof Double))) {
			o = new Double(o.toString());
		}
		return (Double) o;
	}

	public Float getFloat(String name) {
		Object o = this.get(name);
		if ((o != null) && (!(o instanceof Float))) {
			o = new Float(o.toString());
		}
		return (Float) o;
	}

	public Integer getInt(String name) {
		Object o = this.get(name);
		if ((o != null) && (!(o instanceof Integer))) {
			o = new Integer(o.toString());
		}
		return (Integer) o;
	}

	public Long getLong(String name) {
		Object o = this.get(name);
		if ((o != null) && (!(o instanceof Long))) {
			o = new Long(o.toString());
		}
		return (Long) o;
	}

	public Short getShort(String name) {
		Object o = this.get(name);
		if ((o != null) && (!(o instanceof Short))) {
			o = new Short(o.toString());
		}
		return (Short) o;
	}

}
