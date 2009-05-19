package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.utils.Base64Utils;

public class CryptTest extends TestCase {

	public void test1() throws Exception {
		// AsyncCrypter.generateKey("D:/private2.key", "D:/public2.key", Algorithm.RSA);
		// AsyncCrypter crypter = AsyncCrypter.newInstance("D:/private.key", "D:/public.key");
		// AsyncCrypter crypter2 = AsyncCrypter.newInstance("D:/private2.key", "D:/public2.key");
		// String teste = crypter2.encrypt("Teste");
		// System.out.println(crypter2.decrypt(teste));
		// System.out.println(crypter.decrypt(teste));

		System.out.println(Base64Utils.fromBase64String("GpuYh3NB"));
	}

}
