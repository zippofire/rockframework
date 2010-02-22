package net.woodstoc.rockframework.test.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class Index extends WebPage {

	public Index() {
		super();
		this.add(new Label("msg", "Teste do Wicket"));

		this.add(new BookmarkablePageLink("link", Ajax.class));
	}

}
