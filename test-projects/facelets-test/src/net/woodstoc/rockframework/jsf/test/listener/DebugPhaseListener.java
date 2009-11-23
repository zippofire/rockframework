package net.woodstoc.rockframework.jsf.test.listener;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class DebugPhaseListener implements PhaseListener {

	private static final long	serialVersionUID	= 1L;

	@Override
	public void afterPhase(PhaseEvent event) {
		System.out.println("After: " + event.getPhaseId());
		System.out.println("After: " + event.getSource().getClass());
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		System.out.println("Before: " + event.getPhaseId());
		System.out.println("Before: " + event.getSource().getClass());
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
