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
package net.woodstock.rockframework.web.struts2.action;

import java.io.InputStream;

public interface DownloadableAction {

	String	DOWNLOAD_CONTENT_TYPE			= "application/force-download";

	String	HTML_CONTENT_TYPE				= "text/html";

	String	PDF_CONTENT_TYPE				= "application/pdf";

	String	TEXT_CONTENT_TYPE				= "text/plain";

	String	XML_CONTENT_TYPE				= "text/xml";

	String	INLINE_CONTENT_DISPOSITION		= "inline";

	String	ATTACHMENT_CONTENT_DISPOSITION	= "attachment";

	InputStream getInputStream();

	String getContentType();

	String getContentDisposition();

}
