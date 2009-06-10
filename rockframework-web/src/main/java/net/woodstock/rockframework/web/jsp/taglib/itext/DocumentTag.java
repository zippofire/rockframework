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
package net.woodstock.rockframework.web.jsp.taglib.itext;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import net.woodstock.rockframework.itext.Document;
import net.woodstock.rockframework.itext.Page;
import net.woodstock.rockframework.itext.types.OutputType;
import net.woodstock.rockframework.itext.types.PageSize;
import net.woodstock.rockframework.itext.types.PrintPages;
import net.woodstock.rockframework.utils.StringUtils;
import net.woodstock.rockframework.web.jsp.taglib.creator.BodyContent;
import net.woodstock.rockframework.web.jsp.taglib.creator.TLD;
import net.woodstock.rockframework.web.jsp.taglib.creator.TLDAttribute;

import com.lowagie.text.DocumentException;

@TLD(name = "document", content = BodyContent.SCRIPTLESS)
public class DocumentTag extends ITextTag {

	private static final String	MIME_TYPE	= "application/pdf";

	@TLDAttribute(required = false, rtexprvalue = true)
	private String				autoPrint;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String				printFistPage;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String				printLastPage;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String				printPages;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String				printCopies;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String				margin;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String				marginBottom;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String				marginLeft;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String				marginRight;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String				marginTop;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String				marginMirror;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String				pageSize;

	private Document			document;

	@Override
	protected void doTagInternal() throws JspException, IOException {
		this.document = new Document();

		if (!StringUtils.isEmpty(this.autoPrint)) {
			this.document.setAutoPrint(Boolean.parseBoolean(this.autoPrint));
			if (!StringUtils.isEmpty(this.printFistPage)) {
				this.document.setPrintFistPage(Integer.parseInt(this.printFistPage));
			}
			if (!StringUtils.isEmpty(this.printLastPage)) {
				this.document.setPrintLastPage(Integer.parseInt(this.printLastPage));
			}
			if (!StringUtils.isEmpty(this.printPages)) {
				this.document.setPrintPages(PrintPages.valueOf(this.printPages));
			}
			if (!StringUtils.isEmpty(this.printCopies)) {
				this.document.setPrintCopies(Integer.parseInt(this.printPages));
			}
		}

		if (!StringUtils.isEmpty(this.margin)) {
			this.document.setMargin(Float.parseFloat(this.autoPrint));
		}

		if (!StringUtils.isEmpty(this.marginBottom)) {
			this.document.setMarginBottom(Float.parseFloat(this.autoPrint));
		}

		if (!StringUtils.isEmpty(this.marginLeft)) {
			this.document.setMarginLeft(Float.parseFloat(this.autoPrint));
		}

		if (!StringUtils.isEmpty(this.marginRight)) {
			this.document.setMarginRight(Float.parseFloat(this.autoPrint));
		}

		if (!StringUtils.isEmpty(this.marginTop)) {
			this.document.setMarginTop(Float.parseFloat(this.autoPrint));
		}

		if (!StringUtils.isEmpty(this.marginMirror)) {
			this.document.setMarginMirror(Boolean.parseBoolean(this.marginMirror));
		}

		if (!StringUtils.isEmpty(this.pageSize)) {
			this.document.setPageSize(PageSize.valueOf(this.pageSize));
		}

		this.getJspBody().invoke(null);

		try {
			PageContext context = this.getPageContext();
			context.getResponse().setContentType(DocumentTag.MIME_TYPE);
			this.document.write(context.getResponse().getOutputStream(), OutputType.PDF);
		} catch (DocumentException e) {
			throw new JspException(e);
		}
	}

	public void addPage(Page page) {
		this.document.addPage(page);
	}

	// Getters and Setters
	public String getAutoPrint() {
		return this.autoPrint;
	}

	public void setAutoPrint(String autoPrint) {
		this.autoPrint = autoPrint;
	}

	public String getPrintFistPage() {
		return this.printFistPage;
	}

	public void setPrintFistPage(String printFistPage) {
		this.printFistPage = printFistPage;
	}

	public String getPrintLastPage() {
		return this.printLastPage;
	}

	public void setPrintLastPage(String printLastPage) {
		this.printLastPage = printLastPage;
	}

	public String getPrintPages() {
		return this.printPages;
	}

	public void setPrintPages(String printPages) {
		this.printPages = printPages;
	}

	public String getPrintCopies() {
		return this.printCopies;
	}

	public void setPrintCopies(String printCopies) {
		this.printCopies = printCopies;
	}

	public String getMargin() {
		return this.margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	public String getMarginBottom() {
		return this.marginBottom;
	}

	public void setMarginBottom(String marginBottom) {
		this.marginBottom = marginBottom;
	}

	public String getMarginLeft() {
		return this.marginLeft;
	}

	public void setMarginLeft(String marginLeft) {
		this.marginLeft = marginLeft;
	}

	public String getMarginRight() {
		return this.marginRight;
	}

	public void setMarginRight(String marginRight) {
		this.marginRight = marginRight;
	}

	public String getMarginTop() {
		return this.marginTop;
	}

	public void setMarginTop(String marginTop) {
		this.marginTop = marginTop;
	}

	public String getMarginMirror() {
		return this.marginMirror;
	}

	public void setMarginMirror(String marginMirror) {
		this.marginMirror = marginMirror;
	}

	public String getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public Document getDocument() {
		return this.document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

}
