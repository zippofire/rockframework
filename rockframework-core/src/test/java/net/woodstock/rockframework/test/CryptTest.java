package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.security.crypt.Crypter;
import net.woodstock.rockframework.security.crypt.impl.AsyncCrypter;
import net.woodstock.rockframework.security.crypt.impl.SyncCrypter;

public class CryptTest extends TestCase {

	public void test1() throws Exception {
		System.out.println("Test 1");
		Crypter crypter = SyncCrypter.newInstance(null, null);
		String encrypt = crypter.encrypt("Lourival");
		String decript = crypter.decrypt(encrypt);
		System.out.println(encrypt);
		System.out.println(decript);
	}

	public void test2() throws Exception {
		System.out.println("Test 2");		
		Crypter crypter = AsyncCrypter.newInstance(null, null);
		String encrypt = crypter.encrypt("Lourival");
		String decript = crypter.decrypt(encrypt);
		System.out.println(encrypt);
		System.out.println(decript);
	}

}
