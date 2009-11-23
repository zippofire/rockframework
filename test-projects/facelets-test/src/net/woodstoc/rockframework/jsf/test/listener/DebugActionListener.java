package net.woodstoc.rockframework.jsf.test.listener;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

public class DebugActionListener implements ActionListener {

	@Override
	public void processAction(ActionEvent event) throws AbortProcessingException {
		System.out.println("Source   : " + event.getSource());
		System.out.println("Component: " + event.getComponent());
	}

}
