/*
 * This file is part of rockframework.
 * 
 * rockframework is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * rockframework is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>;.
 */
package br.net.woodstock.rockframework.net.ldap;

import java.io.Serializable;
import java.util.Collection;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import br.net.woodstock.rockframework.util.Assert;


public class LDAPClient implements Serializable {

	private static final long		serialVersionUID		= 3389854358379894685L;

	public static final String		AUTHENTICATION_NONE		= "none";

	public static final String		AUTHENTICATION_SIMPLE	= "simple";

	public static final String		AUTHENTICATION_SASL		= "sasl_mech";

	private String					user;

	private String					password;

	private String					url;

	private String					authenticationType		= LDAPClient.AUTHENTICATION_SIMPLE;

	private boolean					connectOnSearch;

	private transient DirContext	context;

	public LDAPClient() {
		super();
	}

	public LDAPClient(final String user, final String password, final String url) {
		super();
		this.user = user;
		this.password = password;
		this.url = url;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(final String user) {
		this.user = user;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getAuthenticationType() {
		return this.authenticationType;
	}

	public void setAuthenticationType(final String authenticationType) {
		this.authenticationType = authenticationType;
	}

	public boolean isConnectOnSearch() {
		return this.connectOnSearch;
	}

	public void setConnectOnSearch(final boolean connectOnSearch) {
		this.connectOnSearch = connectOnSearch;
	}

	public boolean isConnected() {
		return this.context != null;
	}

	public void connect() {
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, LDAPContextFactoryManager.getInstance().getName());
			env.put(Context.PROVIDER_URL, this.url);
			env.put(Context.SECURITY_AUTHENTICATION, this.authenticationType);
			if (!this.authenticationType.equals(LDAPClient.AUTHENTICATION_NONE)) {
				env.put(Context.SECURITY_PRINCIPAL, this.user);
				env.put(Context.SECURITY_CREDENTIALS, this.password);
			}

			this.context = new InitialDirContext(env);
		} catch (NamingException e) {
			throw new LDAPException(e);
		}
	}

	public void disconnect() {
		try {
			this.context.close();
			this.context = null;
		} catch (NamingException e) {
			throw new LDAPException(e);
		}
	}

	public Collection<LDAPSearchResult> search(final LDAPFilter filter) {
		Assert.notNull(filter, "filter");
		try {
			if ((this.connectOnSearch) && (!this.isConnected())) {
				this.connect();
			}
			Assert.notNull(this.context, "context");
			SearchControls controls = new SearchControls();
			controls.setReturningAttributes(filter.getAttributes());
			controls.setCountLimit(filter.getLimit());
			controls.setTimeLimit(0);
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			NamingEnumeration<SearchResult> results = this.context.search(filter.getBaseName(), filter.getFilter(), controls);

			Collection<LDAPSearchResult> c = LDAPHelper.toCollection(results);

			if (this.connectOnSearch) {
				this.disconnect();
			}

			return c;
		} catch (NamingException e) {
			throw new LDAPException(e);
		}
	}
}
