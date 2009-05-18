package net.woodstock.rockframework.test;

import net.woodstock.rockframework.utils.Base64Utils;

public class CryptTest {

	public static void main(String[] args) {
		try {
			// AsyncCrypter.generateKey("D:/private2.key", "D:/public2.key", Algorithm.RSA);
			// AsyncCrypter crypter = AsyncCrypter.newInstance("D:/private.key", "D:/public.key");
			// AsyncCrypter crypter2 = AsyncCrypter.newInstance("D:/private2.key", "D:/public2.key");
			// String teste = crypter2.encrypt("Teste");
			// System.out.println(crypter2.decrypt(teste));
			// System.out.println(crypter.decrypt(teste));

			System.out.println(Base64Utils.fromBase64String("GpuYh3NB"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
