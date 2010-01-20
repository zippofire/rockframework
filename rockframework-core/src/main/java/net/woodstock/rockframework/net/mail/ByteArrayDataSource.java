package net.woodstock.rockframework.net.mail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import javax.activation.DataSource;

public class ByteArrayDataSource implements DataSource {

	private static final String	NAME	= "ds";

	private String				contentType;

	private InputStream			inputStream;

	public ByteArrayDataSource(final String contentType, final InputStream inputStream) {
		super();
		this.contentType = contentType;
		this.inputStream = inputStream;
	}

	public ByteArrayDataSource(final String contentType, final byte[] bytes) {
		super();
		this.contentType = contentType;
		this.inputStream = new ByteArrayInputStream(bytes);
	}

	public ByteArrayDataSource(final String contentType, final String data) {
		super();
		this.contentType = contentType;
		this.inputStream = new ByteArrayInputStream(data.getBytes());
	}

	public ByteArrayDataSource(final String contentType, final String data, final Charset charset) {
		super();
		this.contentType = contentType;
		this.inputStream = new ByteArrayInputStream(data.getBytes(charset));
	}

	@Override
	public String getContentType() {
		return this.contentType;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return this.inputStream;
	}

	@Override
	public String getName() {
		return ByteArrayDataSource.NAME;
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		throw new UnsupportedOperationException();
	}

}
