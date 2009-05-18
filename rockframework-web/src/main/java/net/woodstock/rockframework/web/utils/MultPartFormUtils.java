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
package net.woodstock.rockframework.web.utils;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

public abstract class MultPartFormUtils {

	private static FileItemFactory		factory;

	private static ServletFileUpload	upload;

	private MultPartFormUtils() {
		//
	}

	public static void write(MultPartForm form, String field, String dir, String name) throws Exception {
		form.write(field, dir, name);
	}

	public static void writeAll(MultPartForm form, String dir) throws Exception {
		form.writeAll(dir);
	}

	public static void write(MultPartForm form, String field, String dir) throws Exception {
		form.write(field, dir);
	}

	public static String getParameter(HttpServletRequest request, String parameter)
			throws FileUploadException {
		return MultPartFormUtils.getParameter(request, parameter, null);
	}

	public static String getParameter(HttpServletRequest request, String parameter, String nvl)
			throws FileUploadException {
		String value = null;
		RequestContext requestContext = new ServletRequestContext(request);

		if (FileUploadBase.isMultipartContent(requestContext)) {
			List<?> items = MultPartFormUtils.upload.parseRequest(requestContext);
			Iterator<?> i = items.iterator();
			while (i.hasNext()) {
				FileItem item = (FileItem) i.next();
				if ((item.isFormField()) && (item.getFieldName().equals(parameter))) {
					value = item.getString();
					break;
				}
			}
		} else {
			value = request.getParameter(parameter);
		}
		if (value == null) {
			value = nvl;
		}
		return value;
	}

	static {
		MultPartFormUtils.factory = new DiskFileItemFactory();
		MultPartFormUtils.upload = new ServletFileUpload(MultPartFormUtils.factory);
	}

}
