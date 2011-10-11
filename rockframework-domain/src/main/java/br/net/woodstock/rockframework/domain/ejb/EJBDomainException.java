package br.net.woodstock.rockframework.domain.ejb;

import javax.ejb.ApplicationException;

import br.net.woodstock.rockframework.domain.DomainException;

@ApplicationException(rollback = true)
public class EJBDomainException extends Exception {

	private static final long	serialVersionUID	= 5195171618156270530L;

	public EJBDomainException(final DomainException cause) {
		super(cause);
	}

}
