package net.woodstock.rockapi.ejb.impl;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import net.woodstock.rockapi.ejb.Calculator;

import org.jboss.annotation.security.SecurityDomain;

@Stateless(name = "Calculator")
@SecurityDomain("EJB")
public class CalculatorBean implements Calculator {

	@Override
	@RolesAllowed("admin")
	public Integer sum(Integer i1, Integer i2) {
		return new Integer(i1.intValue() + i2.intValue());
	}

}
