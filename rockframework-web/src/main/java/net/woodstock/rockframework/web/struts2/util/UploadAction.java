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
package net.woodstock.rockframework.web.struts2.util;

import java.io.File;

import net.woodstock.rockframework.web.struts2.Action;

public abstract class UploadAction extends Action implements UploadableAction {

	private static final long	serialVersionUID	= -4881795896025336102L;

	private File				upload;

	private String				uploadContentType;

	private String				uploadFileName;

	@Override
	public File getUpload() {
		return this.upload;
	}

	public void setUpload(final File upload) {
		this.upload = upload;
	}

	@Override
	public String getUploadContentType() {
		return this.uploadContentType;
	}

	public void setUploadContentType(final String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	@Override
	public String getUploadFileName() {
		return this.uploadFileName;
	}

	public void setUploadFileName(final String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

}
