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
package net.woodstock.rockframework.office.pdf;

import java.io.IOException;
import java.io.InputStream;

import net.woodstock.rockframework.config.CoreLog;

public abstract class PDFManager {

	private static final String	ITEXTPDF	= "com.itextpdf.text.Document";

	private static final String	LOWAGIE		= "com.lowagie.text.Document";

	private static PDFManager	instance	= PDFManager.getAvailableManager();

	public abstract InputStream cut(final InputStream source, final int start, final int end) throws IOException;

	public abstract InputStream merge(final InputStream[] sources) throws IOException;

	public abstract InputStream[] split(final InputStream source, final int size) throws IOException;

	public static PDFManager getInstance() {
		return PDFManager.instance;
	}

	private static PDFManager getAvailableManager() {
		try {
			Class.forName(PDFManager.LOWAGIE);
			PDFManager manager = new LowagiePDFManager();
			CoreLog.getInstance().getLog().info("Using lowagie for PDF");
			return manager;
		} catch (ClassNotFoundException e) {
			try {
				Class.forName(PDFManager.ITEXTPDF);
				PDFManager manager = new ITextPDFManager();
				CoreLog.getInstance().getLog().info("Using itextpdf for PDF");
				return manager;
			} catch (ClassNotFoundException ee) {
				throw new UnsupportedOperationException("No PDF found");
			}
		}
	}

}
