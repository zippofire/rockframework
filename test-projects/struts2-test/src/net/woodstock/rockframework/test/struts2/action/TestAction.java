package net.woodstock.rockframework.test.struts2.action;

import java.util.Map.Entry;

import net.woodstock.rockframework.test.struts2.entity.Foo;
import net.woodstock.rockframework.web.struts2.Action;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.inject.Inject;

@ParentPackage(value = "default")
@org.apache.struts2.convention.annotation.Action(value = "test")
@Results(value = { @Result(name = "success", location = "/test/test.jsp") })
@net.woodstock.rockframework.web.struts2.spring.Action
@Scope(value = "prototype")
public class TestAction extends Action {

	private static final long	serialVersionUID	= 1L;

	private Foo					foo;

	public TestAction() {
		super();
		throw new RuntimeException("Aquiiii");
	}

	@Override
	public String execute() throws Exception {
		return net.woodstock.rockframework.web.struts2.Constants.SUCCESS;
	}

	public Foo getFoo() {
		return this.foo;
	}

	public void setFoo(Foo foo) {
		this.foo = foo;
	}

	public String getMessage() {
		StringBuilder builder = new StringBuilder();

		ConfigurationManager manager = ActionContext.getContext().getContainer().getInstance(ConfigurationManager.class);
		Configuration configuration = manager.getConfiguration();
		for (String name : configuration.getPackageConfigNames()) {
			PackageConfig config = configuration.getPackageConfig(name);
			for (Entry<String, ActionConfig> action : config.getActionConfigs().entrySet()) {
				builder.append(action.getValue().getName() + "\n");
			}
		}
		return builder.toString();
	}

	class X {

		@Inject
		private ConfigurationManager	manager;

		public ConfigurationManager getManager() {
			return this.manager;
		}

		public void setManager(ConfigurationManager manager) {
			this.manager = manager;
		}

	}

}
