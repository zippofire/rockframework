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
package br.net.woodstock.rockframework.web.struts2.util;

import org.apache.struts2.views.jasperreports.JasperReportConstants;

public interface JasperableAction<T> {

	String	FORMAT_CSV	= JasperReportConstants.FORMAT_CSV;

	String	FORMAT_HTML	= JasperReportConstants.FORMAT_HTML;

	String	FORMAT_PDF	= JasperReportConstants.FORMAT_PDF;

	String	FORMAT_XLS	= JasperReportConstants.FORMAT_XLS;

	String	FORMAT_XML	= JasperReportConstants.FORMAT_XML;

	String getLocation();

	T getDataSource();

	String getFormat();

	String getName();

	String getContentDisposition();

}
