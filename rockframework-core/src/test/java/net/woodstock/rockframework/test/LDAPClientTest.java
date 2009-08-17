package net.woodstock.rockframework.test;

import java.io.FileInputStream;
import java.util.Collection;
import java.util.Scanner;
import java.util.Map.Entry;

import junit.framework.TestCase;
import net.woodstock.rockframework.net.ldap.LDAPClient;
import net.woodstock.rockframework.net.ldap.LDAPSearchResult;
import net.woodstock.rockframework.net.ldap.filter.LDAPFilter;
import net.woodstock.rockframework.net.ldap.filter.LDAPRestriction;

public class LDAPClientTest extends TestCase {

	public void xtest1() throws Exception {
		LDAPClient client = new LDAPClient();

		client.setUser("subversion@mc.intranet");
		client.setPassword("subversion");

		client.setUrl("ldap://10.209.64.150:389/dc=mc,dc=intranet");
		client.connect();

		LDAPFilter filter = new LDAPFilter("ou=Estrutura - MC", new String[] { "sAMAccountName", "name", "mail" });
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

	public void xtest2() throws Exception {
		LDAPClient client = new LDAPClient();

		client.setUser("subversion@mc.intranet");
		client.setPassword("subversion");

		client.setUrl("ldap://10.209.64.150:389/dc=mc,dc=intranet");
		client.setConnectOnSearch(false);
		client.connect();

		FileInputStream inputStream = new FileInputStream("D:/usuarios_sgap.txt");
		Scanner scanner = new Scanner(inputStream);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine().trim();
			LDAPFilter filter = new LDAPFilter("ou=Estrutura - MC", new String[] { "name", "mail" });

			filter.add(LDAPRestriction.eq("sAMAccountName", line));
			Collection<LDAPSearchResult> results = client.search(filter);
			for (LDAPSearchResult result : results) {
				System.out.printf("%-20s %-50s %-50s\n", line, result.getAttribute("name"), result.getAttribute("mail"));
			}
		}

		client.disconnect();
		scanner.close();
		inputStream.close();
	}

	public void test3() throws Exception {
		LDAPClient client = new LDAPClient();

		client.setUser("subversion@mc.intranet");
		client.setPassword("subversion");

		client.setUrl("ldap://10.209.64.150:389/dc=mc,dc=intranet");
		client.setConnectOnSearch(false);
		client.connect();

		LDAPFilter filter = new LDAPFilter("ou=Estrutura - MC", new String[] { "sAMAccountName", "name", "mail" });
		// filter.add(LDAPRestriction.eq("name", "KELLY"));
		filter.add(LDAPRestriction.like("sAMAccountName", "aldenne"));
		Collection<LDAPSearchResult> results = client.search(filter);
		for (LDAPSearchResult result : results) {
			for (Entry<String, String> entry : result.getAttributes()) {
				System.out.println(entry.getKey() + " => " + entry.getValue());
			}
		}

		client.disconnect();
	}

}
