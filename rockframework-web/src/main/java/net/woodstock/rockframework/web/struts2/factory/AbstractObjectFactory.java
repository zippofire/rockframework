package net.woodstock.rockframework.web.struts2.factory;

import net.woodstock.rockframework.logging.SysLogger;

import org.apache.commons.logging.Log;

import com.opensymphony.xwork2.ObjectFactory;

public class AbstractObjectFactory extends ObjectFactory {

	private static final long	serialVersionUID	= -9220498994278571801L;

	protected Log getLogger() {
		return SysLogger.getLogger();
	}

}
