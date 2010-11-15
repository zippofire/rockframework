package net.woodstock.rockframework.test.security;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import junit.framework.TestCase;
import net.woodstock.rockframework.security.crypt.Crypter;
import net.woodstock.rockframework.security.crypt.KeyPairType;
import net.woodstock.rockframework.security.crypt.impl.AsyncCrypter;
import net.woodstock.rockframework.security.crypt.impl.AsyncCrypterReader;
import net.woodstock.rockframework.security.crypt.impl.AsyncCrypterWriter;
import net.woodstock.rockframework.security.crypt.impl.Base64Crypter;
import net.woodstock.rockframework.security.util.EncryptedProperties;

public class EncryptedPropertiesTest extends TestCase {

	public void xtest1() throws Exception {
		AsyncCrypter crypter = new AsyncCrypter(KeyPairType.RSA);
		Crypter crypter64 = new Base64Crypter(crypter);
		EncryptedProperties properties = new EncryptedProperties(crypter64);
		properties.setProperty("nome", "Lourival Sabino");

		properties.store(new FileOutputStream("C:/Temp/test.properties"), "Testando");
		AsyncCrypterWriter writer = new AsyncCrypterWriter(crypter, new FileOutputStream("C:/Temp/private.key"), new FileOutputStream("C:/Temp/public.key"));
		writer.write();
	}

	public void test2() throws Exception {
		AsyncCrypterReader reader = new AsyncCrypterReader(new FileInputStream("C:/Temp/private.key"), new FileInputStream("C:/Temp/public.key"), KeyPairType.RSA);
		AsyncCrypter crypter = reader.read();
		Crypter crypter64 = new Base64Crypter(crypter);

		EncryptedProperties properties = new EncryptedProperties(crypter64);
		properties.load(new FileInputStream("C:/Temp/test.properties"));

		System.out.println(properties.getProperty("nome"));
	}

}
