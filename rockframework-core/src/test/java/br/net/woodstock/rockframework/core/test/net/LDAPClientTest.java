package br.net.woodstock.rockframework.core.test.net;

import java.util.Collection;
import java.util.Map.Entry;

import br.net.woodstock.rockframework.net.ldap.LDAPClient;
import br.net.woodstock.rockframework.net.ldap.LDAPFilter;
import br.net.woodstock.rockframework.net.ldap.LDAPSearchResult;
import br.net.woodstock.rockframework.net.ldap.restriction.LDAPRestrictions;

import junit.framework.TestCase;

public class LDAPClientTest extends TestCase {

	public void test1() throws Exception {
		LDAPClient client = new LDAPClient();

		client.setUser("lourival.junior@tse.jus.br");
		client.setPassword("xxxxxx");

		client.setUrl("ldap://10.28.1.30:389/dc=tse,dc=jus,dc=br");
		client.connect();

		LDAPFilter filter = new LDAPFilter("ou=Users", new String[] { "sAMAccountName", "name", "mail", "password" });

		filter.add(LDAPRestrictions.like("sAMAccountName", "aldenne"));
		Collection<LDAPSearchResult> results = client.search(filter);
		for (LDAPSearchResult result : results) {
			for (Entry<String, String> entry : result.getAttributes()) {
				System.out.println(entry.getKey() + " => " + entry.getValue());
			}
		}

		client.disconnect();
	}

}
