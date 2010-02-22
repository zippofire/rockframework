package net.woodstock.rockframework.web.struts2.factory;

import net.woodstock.rockframework.web.config.WebLog;

import org.apache.commons.logging.Log;

import com.opensymphony.xwork2.ObjectFactory;

public class AbstractObjectFactory extends ObjectFactory {

	private static final long	serialVersionUID	= -9220498994278571801L;

	protected Log getLog() {
		return WebLog.getInstance().getLog();
	}

}
