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
package net.woodstock.rockframework.net.mail;

abstract class AttachmentBean implements Attachment {

	private String	name;

	private String	contentType;

	private String	contentAsString;

	@Override
	public String getName() {
		return this.name;
	}

	void setName(final String name) {
		this.name = name;
	}

	@Override
	public String getContentType() {
		return this.contentType;
	}

	void setContentType(final String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String getContentAsString() {
		return this.contentAsString;
	}

	void setContentAsString(final String contentAsString) {
		this.contentAsString = contentAsString;
	}

}
