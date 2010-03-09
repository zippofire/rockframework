package net.woodstock.rockframework.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CachedHttpServletResponse extends HttpServletResponseWrapper {

	private CachedServletOutputStream	outputStream;

	private PrintWriter					writer;

	public CachedHttpServletResponse(final HttpServletResponse response) {
		super(response);
		this.outputStream = new CachedServletOutputStream();
		this.writer = new PrintWriter(this.outputStream);
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return this.outputStream;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return this.writer;
	}

	// Cache
	public InputStream getCache() {
		return this.outputStream.getCache();
	}

}
