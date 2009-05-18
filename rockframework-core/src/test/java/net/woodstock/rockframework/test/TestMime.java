package net.woodstock.rockframework.test;

import net.woodstock.rockframework.utils.MimeUtils;

public class TestMime {

	public static void main(String[] args) {
		System.out.println(MimeUtils.getMimeType("pdf"));
		String name = "/opt/dados/documentos/25.rtf";
		System.out.println(name.substring(name.lastIndexOf('.') + 1));

	}

}
