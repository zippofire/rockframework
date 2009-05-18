package net.woodstock.rockframework.test;

import java.util.Collection;

import javax.naming.directory.SearchResult;

import junit.framework.TestCase;
import net.woodstock.rockframework.net.ldap.LDAPClient;
import net.woodstock.rockframework.net.ldap.filter.LDAPFilter;
import net.woodstock.rockframework.net.ldap.filter.LDAPRestriction;
import net.woodstock.rockframework.net.ldap.filter.LDAPRestrictionGroup;

public class LDAPClientTest extends TestCase {

	public void testWindows() throws Exception {
		LDAPClient client = new LDAPClient();
		// client.setUser("user.sgd@mc.intranet");
		// client.setPassword("12345678");

		client.setUser("subversion@mc.intranet");
		client.setPassword("subversion");

		client.setUrl("ldap://10.209.64.150:389/dc=mc,dc=intranet");
		client.connect();

		LDAPFilter filter = new LDAPFilter("ou=Estrutura - MC", new String[] { "sAMAccountName", "name",
				"mail" });
		LDAPRestrictionGroup or = LDAPRestrictionGroup.or();
		LDAPRestrictionGroup and = LDAPRestrictionGroup.and();

		and.add(LDAPRestriction.eq("objectClass", "user"));
		and.add(LDAPRestriction.like("sAMAccountName", "laer"));

		or.add(and);

		filter.add(or);

		System.out.println(filter);
		Collection<SearchResult> results = client.search(filter);

		for (SearchResult result : results) {
			System.out.println("Login : " + result.getAttributes().get("sAMAccountName").get(0));
			System.out.println("Nome  : " + result.getAttributes().get("name").get(0));
			System.out.println("Email : " + result.getAttributes().get("mail").get(0));
		}
	}

}
