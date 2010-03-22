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
package net.woodstock.rockframework.config;

public final class CoreMessage extends AbstractMessage {

	public static final String	MESSAGE_NOT_EMPTY	= "error.notEmpty";

	public static final String	MESSAGE_NOT_FOUND	= "error.notFound";

	public static final String	MESSAGE_NOT_NULL	= "error.notNull";

	private static final String	CORE_MESSAGES		= "rockframework-core-messages";

	private static CoreMessage	instance			= new CoreMessage();

	private CoreMessage() {
		super(CoreMessage.CORE_MESSAGES);
	}

	public static CoreMessage getInstance() {
		return CoreMessage.instance;
	}

}
