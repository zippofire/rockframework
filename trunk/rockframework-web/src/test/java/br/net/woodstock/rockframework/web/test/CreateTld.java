package br.net.woodstock.rockframework.web.test;

import br.net.woodstock.rockframework.web.jsp.taglib.AbstractTag;
import br.net.woodstock.rockframework.web.jsp.taglib.creator.Attribute;
import br.net.woodstock.rockframework.web.jsp.taglib.creator.BodyContent;
import br.net.woodstock.rockframework.web.jsp.taglib.creator.TLDBuilder;
import br.net.woodstock.rockframework.web.jsp.taglib.creator.Tag;
import junit.framework.TestCase;

public class CreateTld extends TestCase {

	public void testTLD1() throws Exception {
		System.out.println(TLDBuilder.getInstance().create(MyTag.class));
	}

	@Tag(name = "myTag", description = "Simple Tag", content = BodyContent.SCRIPTLESS)
	public static class MyTag extends AbstractTag {

		@Attribute(type = Integer.class, required = true)
		private Integer	id;

		@Attribute
		private String	name;

		public MyTag() {
			super();
		}

		public Integer getId() {
			return this.id;
		}

		public void setId(final Integer id) {
			this.id = id;
		}

		public String getName() {
			return this.name;
		}

		public void setName(final String name) {
			this.name = name;
		}

		@Attribute(rtexprvalue = true)
		public void setObject(final Object object) {
			System.out.println("Object: " + object);
		}

	}

}
