package net.woodstoc.rockframework.test.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class TestApplication extends WebApplication {

	public TestApplication() {
		super();
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return Index.class;
	}

}
