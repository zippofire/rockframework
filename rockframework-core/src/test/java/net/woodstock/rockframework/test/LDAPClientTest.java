package net.woodstock.rockframework.test;

import java.util.Collection;

import junit.framework.TestCase;
import net.woodstock.rockframework.net.ldap.LDAPClient;
import net.woodstock.rockframework.net.ldap.LDAPSearchResult;
import net.woodstock.rockframework.net.ldap.filter.LDAPFilter;

public class LDAPClientTest extends TestCase {

	public void test1() throws Exception {
		LDAPClient client = new LDAPClient();

		client.setUser("subversion@mc.intranet");
		client.setPassword("subversion");

		client.setUrl("ldap://10.209.64.150:389/dc=mc,dc=intranet");
		client.connect();

		LDAPFilter filter = new LDAPFilter("ou=Estrutura - MC", new String[] { "sAMAccountName", "name",
				"mail" });
		// LDAPRestrictionGroup or = LDAPRestrictionGroup.or();
		// LDAPRestrictionGroup and = LDAPRestrictionGroup.and();

		// and.add(LDAPRestriction.eq("objectClass", "user"));
		// and.add(LDAPRestriction.like("sAMAccountName", "laer"));

		// or.add(and);

		// filter.add(or);

		System.out.println(filter);
		Collection<LDAPSearchResult> results = client.search(filter);

		for (LDAPSearchResult result : results) {
			System.out.println("Login : " + result.getAttribute("sAMAccountName"));
			System.out.println("Nome  : " + result.getAttribute("name"));
			System.out.println("Email : " + result.getAttribute("mail"));
		}
	}

}
