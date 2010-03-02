package net.woodstoc.rockframework.test.struts;

import net.woodstock.rockframework.web.struts.BaseAction;
import net.woodstock.rockframework.web.struts.Constants;

import org.apache.struts.action.ActionForward;

public class PeopleAction extends BaseAction<PeopleForm> {

	@Override
	protected ActionForward execute() throws Exception {
		PeopleForm form = this.getForm();
		System.out.println(form.getPeople().getId());
		System.out.println(form.getPeople().getName());
		System.out.println(form.getPeople().getStatus());
		return this.getMapping().findForward(Constants.SUCCESS);
	}
	

}
