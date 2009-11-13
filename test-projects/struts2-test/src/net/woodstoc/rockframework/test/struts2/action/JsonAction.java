package net.woodstoc.rockframework.test.struts2.action;

import java.util.ArrayList;
import java.util.Collection;

import net.woodstoc.rockframework.test.struts2.entity.Bar;
import net.woodstoc.rockframework.test.struts2.entity.Baz;
import net.woodstoc.rockframework.test.struts2.entity.Foo;
import net.woodstock.rockframework.util.Date;
import net.woodstock.rockframework.web.struts2.action.BaseAction;

public class JsonAction extends BaseAction {

	private static final long	serialVersionUID	= 1L;

	private Collection<Foo>		foos;

	public JsonAction() {
		super();
		this.foos = new ArrayList<Foo>();
		for (int f = 0; f < 100; f++) {
			Foo foo = new Foo();
			foo.setId(new Integer(f));
			foo.setName("Foo " + f);

			Date date = new Date();
			date.addDays(f);
			foo.setDate(date);

			foo.setBars(new ArrayList<Bar>());
			for (int b = 0; b < 100; b++) {
				Bar bar = new Bar();
				bar.setId(new Integer(b));
				bar.setValue("Bar " + b);
				bar.setFoo(foo);
				foo.getBars().add(bar);

				Baz baz = new Baz();
				baz.setId(new Integer(b));
				baz.setValue("Baz " + b);
				baz.setBar(bar);

				bar.setBaz(baz);
			}
			this.foos.add(foo);
		}
	}

	public Collection<Foo> getFoos() {
		return this.foos;
	}

	public void setFoos(Collection<Foo> foos) {
		this.foos = foos;
	}

}
