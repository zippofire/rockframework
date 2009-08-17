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
package net.woodstock.rockframework.itext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import net.woodstock.rockframework.itext.types.OutputType;
import net.woodstock.rockframework.itext.types.PageSize;
import net.woodstock.rockframework.itext.types.PrintPages;
import net.woodstock.rockframework.utils.StringUtils;

import com.lowagie.text.DocumentException;

public class Document implements Serializable {

	private static final long	serialVersionUID	= -6269639045748812727L;

	private boolean				autoPrint;

	private int					printFistPage;

	private int					printLastPage;

	private PrintPages			printPages;

	private int					printCopies;

	private float				margin;

	private float				marginBottom;

	private float				marginLeft;

	private float				marginRight;

	private float				marginTop;

	private boolean				marginMirror;

	private PageSize			pageSize;

	private String				title;

	private Collection<Page>	pages;

	public Document() {
		super();
		this.autoPrint = false;
		this.margin = Float.MIN_VALUE;
		this.marginBottom = Float.MIN_VALUE;
		this.marginLeft = Float.MIN_VALUE;
		this.marginRight = Float.MIN_VALUE;
		this.marginTop = Float.MIN_VALUE;
		this.pageSize = PageSize.DEFAULT;
		this.marginMirror = false;
		this.pages = new ArrayList<Page>();

	}

	public void addPage(Page page) {
		this.pages.add(page);
	}

	public void removePage(int index) {
		((ArrayList<Page>) this.pages).remove(index);
	}

	public void removePage(Page page) {
		this.pages.remove(page);
	}

	public void write(File file, OutputType type) throws IOException, DocumentException {
		this.write(new FileOutputStream(file), type);
	}

	public void write(OutputStream output, OutputType type) throws DocumentException {
		com.lowagie.text.Document document = new com.lowagie.text.Document();

		if ((this.marginLeft != Float.MIN_VALUE) && (this.marginRight != Float.MIN_VALUE) && (this.marginTop != Float.MIN_VALUE) && (this.marginBottom != Float.MIN_VALUE)) {
			document.setMargins(this.marginLeft, this.marginRight, this.marginTop, this.marginBottom);
		} else if (this.margin != Float.MIN_VALUE) {
			document.setMargins(this.margin, this.margin, this.margin, this.margin);
		}

		WriterFactory.getInstance(type).getWriter(document, output);

		boolean first = true;

		document.setPageSize(this.pageSize.getPageSize());
		document.setMarginMirroring(this.marginMirror);

		if (!StringUtils.isEmpty(this.title)) {
			document.addTitle(this.title);
		}

		document.open();

		for (Page page : this.pages) {
			if (!first) {
				document.newPage();
				first = false;
			}
			for (Object o : page.getItems()) {
				document.add(o.getObject());
			}
		}

		document.close();

	}

	// Getters and Setters
	public boolean isAutoPrint() {
		return this.autoPrint;
	}

	public void setAutoPrint(boolean autoPrint) {
		this.autoPrint = autoPrint;
	}

	public int getPrintFistPage() {
		return this.printFistPage;
	}

	public void setPrintFistPage(int printFistPage) {
		this.printFistPage = printFistPage;
	}

	public int getPrintLastPage() {
		return this.printLastPage;
	}

	public void setPrintLastPage(int printLastPage) {
		this.printLastPage = printLastPage;
	}

	public PrintPages getPrintPages() {
		return this.printPages;
	}

	public void setPrintPages(PrintPages printPages) {
		this.printPages = printPages;
	}

	public int getPrintCopies() {
		return this.printCopies;
	}

	public void setPrintCopies(int printCopies) {
		this.printCopies = printCopies;
	}

	public float getMargin() {
		return this.margin;
	}

	public void setMargin(float margin) {
		this.margin = margin;
	}

	public float getMarginBottom() {
		return this.marginBottom;
	}

	public void setMarginBottom(float marginBottom) {
		this.marginBottom = marginBottom;
	}

	public float getMarginLeft() {
		return this.marginLeft;
	}

	public void setMarginLeft(float marginLeft) {
		this.marginLeft = marginLeft;
	}

	public float getMarginRight() {
		return this.marginRight;
	}

	public void setMarginRight(float marginRight) {
		this.marginRight = marginRight;
	}

	public float getMarginTop() {
		return this.marginTop;
	}

	public void setMarginTop(float marginTop) {
		this.marginTop = marginTop;
	}

	public boolean isMarginMirror() {
		return this.marginMirror;
	}

	public void setMarginMirror(boolean marginMirror) {
		this.marginMirror = marginMirror;
	}

	public PageSize getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(PageSize pageSize) {
		this.pageSize = pageSize;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Collection<Page> getPages() {
		return this.pages;
	}

	public void setPages(Collection<Page> pages) {
		this.pages = pages;
	}
}
