package net.woodstoc.rockframework.struts2.test;

import java.util.ArrayList;
import java.util.List;

import net.woodstoc.rockframework.entity.ListItem;
import net.woodstock.rockframework.web.struts2.action.BaseAction;

public class ListAction extends BaseAction {

	private static final long	serialVersionUID	= 1L;

	private List<ListItem>		items;

	public ListAction() {
		super();
		this.items = new ArrayList<ListItem>();
	}

	@Override
	public String execute() throws Exception {
		for (int i = 0; i < 10; i++) {
			ListItem item = new ListItem();
			item.setId(new Integer(i));
			item.setName("List Item " + i);
			item.setStatus(new Boolean(i % 2 == 0));
			this.items.add(item);
		}
		return BaseAction.SUCCESS;
	}

	public String update() throws Exception {
		for (ListItem item : this.items) {
			System.out.println("ID: " + item.getId() + " Name: " + item.getName() + " Status: " + item.getStatus());
		}

		return BaseAction.SUCCESS;
	}

	public List<ListItem> getItems() {
		return this.items;
	}

	public void setItems(List<ListItem> items) {
		this.items = items;
	}

	// Test
	public String check(ListItem item) {
		return "Item [" + item.getName() + "]";
	}

}
