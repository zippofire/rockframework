package br.net.woodstock.rockframework.test.security;

import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;

import junit.framework.TestCase;

public class ProviderTest extends TestCase {

	public void test1() throws Exception {
		Provider[] providers = Security.getProviders();
		for (Provider provider : providers) {
			System.out.println(provider.getName());
			for (Service service : provider.getServices()) {
				System.out.println("\t" + service.getType());
			}
		}
	}

}
