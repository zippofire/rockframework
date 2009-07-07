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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

public class MultPartForm {

	private static FileItemFactory		factory;

	private static ServletFileUpload	upload;

	private Map<String, String>			parameters;

	private Map<String, FileItem>		files;

	private boolean						multpart;

	public MultPartForm(HttpServletRequest request) throws FileUploadException {
		this.parameters = new HashMap<String, String>();
		this.files = new HashMap<String, FileItem>();

		RequestContext requestContext = new ServletRequestContext(request);

		if (FileUploadBase.isMultipartContent(requestContext)) {
			List<?> items = MultPartForm.upload.parseRequest(requestContext);
			Iterator<?> i = items.iterator();
			while (i.hasNext()) {
				FileItem item = (FileItem) i.next();
				if (item.isFormField()) {
					this.parameters.put(item.getFieldName(), item.getString());
				} else {
					this.files.put(item.getFieldName(), item);
				}
			}
		} else {
			Enumeration<?> e = request.getParameterNames();
			while (e.hasMoreElements()) {
				String name = (String) e.nextElement();
				this.parameters.put(name, request.getParameter(name));
			}
		}
	}

	public Map<String, FileItem> getFiles() {
		return this.files;
	}

	public Map<String, String> getParameters() {
		return this.parameters;
	}

	public boolean isMultpart() {
		return this.multpart;
	}

	public String getParameter(String parameter) {
		return this.getParameter(parameter, null);
	}

	public String getParameter(String parameter, String nvl) {
		if (this.parameters.containsKey(parameter)) {
			return this.parameters.get(parameter);
		}
		return nvl;
	}

	public FileItem getFileItem(String file) {
		if (this.files.containsKey(file)) {
			return this.files.get(file);
		}
		return null;
	}

	public boolean hasParameter(String parameter) {
		if (this.getParameter(parameter) != null) {
			return true;
		}
		return false;
	}

	public void writeAll(String dir) throws Exception {
		Object[] keys = this.files.keySet().toArray();
		for (Object k : keys) {
			FileItem item = this.files.get(k);
			String name = item.getName();
			item.write(new File(dir + File.separator + name));
		}
	}

	public void write(String field, String dir) throws Exception {
		if (this.files.containsKey(field)) {
			FileItem item = this.files.get(field);
			String name = item.getName();
			item.write(new File(dir + File.separator + name));
		}
	}

	public void write(String field, String dir, String name) throws Exception {
		if (this.files.containsKey(field)) {
			FileItem item = this.files.get(field);
			item.write(new File(dir + File.separator + name));
		}
	}

	public static String getParameter(HttpServletRequest request, String parameter)
			throws FileUploadException {
		String value = null;
		RequestContext requestContext = new ServletRequestContext(request);

		if (FileUploadBase.isMultipartContent(requestContext)) {
			List<?> items = MultPartForm.upload.parseRequest(requestContext);
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
		return value;
	}

	public static InputStream getFile(HttpServletRequest request, String parameter)
			throws FileUploadException, IOException {
		InputStream value = null;
		RequestContext requestContext = new ServletRequestContext(request);

		if (FileUploadBase.isMultipartContent(requestContext)) {
			List<?> items = MultPartForm.upload.parseRequest(requestContext);
			Iterator<?> i = items.iterator();
			while (i.hasNext()) {
				FileItem item = (FileItem) i.next();
				if ((!item.isFormField()) && (item.getFieldName().equals(parameter))) {
					value = item.getInputStream();
					break;
				}
			}
		}
		return value;
	}

	static {
		MultPartForm.factory = new DiskFileItemFactory();
		MultPartForm.upload = new ServletFileUpload(MultPartForm.factory);
	}

}
