package br.net.woodstock.rockframework.net.mail;

public enum StoreType {

	IMAP(Constants.IMAP_STORE), POP3(Constants.POP3_STORE);

	private String	store;

	private StoreType(final String store) {
		this.store = store;
	}

	public String getStore() {
		return this.store;
	}

}
