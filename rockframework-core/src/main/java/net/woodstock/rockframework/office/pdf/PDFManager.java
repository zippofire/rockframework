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

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import net.woodstock.rockframework.config.CoreLog;

public abstract class PDFManager {

	private static final String	ITEXTPDF	= "com.itextpdf.text.Document";

	private static final String	LOWAGIE		= "com.lowagie.text.Document";

	private static final String	PDFBOX		= "org.apache.pdfbox.pdmodel.PDDocument";

	private static PDFManager	instance	= PDFManager.getAvailableManager();

	public abstract InputStream cut(InputStream source, int start, int end) throws IOException;

	public abstract InputStream merge(InputStream[] sources) throws IOException;

	public abstract InputStream[] split(InputStream source, int size) throws IOException;

	public abstract String getText(InputStream source) throws IOException;

	public abstract BufferedImage[] toImage(InputStream source) throws IOException;

	public static PDFManager getInstance() {
		return PDFManager.instance;
	}

	private static PDFManager getAvailableManager() {
		if (PDFManager.isITextPDFAvailable()) {
			CoreLog.getInstance().getLog().info("Using lowagie for PDF");
			return new LowagiePDFManager();
		}

		if (PDFManager.isPDFBoxAvailable()) {
			CoreLog.getInstance().getLog().info("Using pdfbox for PDF");
			return new PDFBoxPDFManager();
		}

		if (PDFManager.isLowagieAvailable()) {
			CoreLog.getInstance().getLog().info("Using itextpdf for PDF");
			return new ITextPDFManager();
		}

		throw new UnsupportedOperationException("No PDF library found");
	}

	private static boolean isITextPDFAvailable() {
		try {
			Class.forName(PDFManager.ITEXTPDF);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	private static boolean isLowagieAvailable() {
		try {
			Class.forName(PDFManager.LOWAGIE);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	private static boolean isPDFBoxAvailable() {
		try {
			Class.forName(PDFManager.PDFBOX);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

}
