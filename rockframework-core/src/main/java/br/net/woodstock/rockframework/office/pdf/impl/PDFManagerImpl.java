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
package br.net.woodstock.rockframework.office.pdf.impl;

import br.net.woodstock.rockframework.config.CoreConfig;
import br.net.woodstock.rockframework.config.CoreLog;
import br.net.woodstock.rockframework.office.pdf.PDFManager;

public abstract class PDFManagerImpl implements PDFManager {

	private static final String	ITEXTPDF					= "com.itextpdf.text.Document";

	private static final String	LOWAGIE						= "com.lowagie.text.Document";

	private static final String	PDFBOX						= "org.apache.pdfbox.pdmodel.PDDocument";

	private static final String	DEFAULT_LIB_PDF_PROPERTY	= "default.lib.pdf";

	private static final String	DEFAULT_LIB_PDF				= CoreConfig.getInstance().getValue(PDFManagerImpl.DEFAULT_LIB_PDF_PROPERTY);

	private static final String	ITEXTPDF_LIB				= "ITEXTPDF";

	private static final String	LOWAGIE_LIB					= "LOWAGIE";

	private static final String	PDFBOX_LIB					= "PDFBOX";

	private static PDFManager	instance					= PDFManagerImpl.getPreferedManager();

	public static PDFManager getInstance() {
		return PDFManagerImpl.instance;
	}

	private static PDFManager getPreferedManager() {
		if ((PDFManagerImpl.ITEXTPDF_LIB.equals(PDFManagerImpl.DEFAULT_LIB_PDF)) && (PDFManagerImpl.isITextPDFAvailable())) {
			CoreLog.getInstance().getLog().info("Using itextpdf for PDF");
			return new ITextManager();
		}
		if ((PDFManagerImpl.LOWAGIE_LIB.equals(PDFManagerImpl.DEFAULT_LIB_PDF)) && (PDFManagerImpl.isLowagieAvailable())) {
			CoreLog.getInstance().getLog().info("Using lowagie for PDF");
			return new LowagieManager();
		}
		if ((PDFManagerImpl.PDFBOX_LIB.equals(PDFManagerImpl.DEFAULT_LIB_PDF)) && (PDFManagerImpl.isPDFBoxAvailable())) {
			CoreLog.getInstance().getLog().info("Using pdfbox for PDF");
			return new PDFBoxManager();
		}

		return PDFManagerImpl.getAvailableManager();
	}

	private static PDFManager getAvailableManager() {
		if (PDFManagerImpl.isLowagieAvailable()) {
			CoreLog.getInstance().getLog().info("Using itextpdf for PDF");
			return new ITextManager();
		}

		if (PDFManagerImpl.isITextPDFAvailable()) {
			CoreLog.getInstance().getLog().info("Using lowagie for PDF");
			return new LowagieManager();
		}

		if (PDFManagerImpl.isPDFBoxAvailable()) {
			CoreLog.getInstance().getLog().info("Using pdfbox for PDF");
			return new PDFBoxManager();
		}

		throw new UnsupportedOperationException("No PDF library found");
	}

	private static boolean isITextPDFAvailable() {
		try {
			Class.forName(PDFManagerImpl.ITEXTPDF);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	private static boolean isLowagieAvailable() {
		try {
			Class.forName(PDFManagerImpl.LOWAGIE);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	private static boolean isPDFBoxAvailable() {
		try {
			Class.forName(PDFManagerImpl.PDFBOX);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

}
