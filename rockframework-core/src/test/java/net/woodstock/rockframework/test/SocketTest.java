package net.woodstock.rockframework.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import junit.framework.TestCase;
import net.woodstock.rockframework.socket.SimpleServerSocket;

public class SocketTest extends TestCase {

	public static void main(final String[] args) {
		try {
			new SocketX().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static class SocketX extends SimpleServerSocket {

		public SocketX() throws IOException {
			super(8080);
		}

		@Override
		public void handle(final Socket s) throws Exception {
			InputStream input = s.getInputStream();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			int i = -1;
			while ((i = input.read()) != -1) {
				output.write(i);
			}
			String str = output.toString();
			System.out.println(str);
		}

	}
}
