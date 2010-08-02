package net.woodstock.rockframework.web.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.web.jsp.taglib.AbstractTag;
import net.woodstock.rockframework.web.jsp.taglib.creator.Attribute;
import net.woodstock.rockframework.web.jsp.taglib.creator.BodyContent;
import net.woodstock.rockframework.web.jsp.taglib.creator.TLDBuilder;
import net.woodstock.rockframework.web.jsp.taglib.creator.Tag;

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

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Attribute(rtexprvalue = true)
		public void setObject(Object object) {
			System.out.println("Object: " + object);
		}

	}

}
