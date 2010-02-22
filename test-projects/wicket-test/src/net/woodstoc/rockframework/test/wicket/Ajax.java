package net.woodstoc.rockframework.test.wicket;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

public class Ajax extends WebPage {

	public Ajax() {
		super();
		Model model = new Model() {

			private int	counter	= 0;

			@Override
			public Serializable getObject() {
				return new Integer(this.counter++);
			}
		};

		final Label label = new Label("counter", model);
		label.setOutputMarkupId(true);

		add(new AjaxFallbackLink("link") {

			public void onClick(AjaxRequestTarget target) {
				if (target != null) {
					target.addComponent(label);
				}
			}
		});
		add(label);
	}

}
