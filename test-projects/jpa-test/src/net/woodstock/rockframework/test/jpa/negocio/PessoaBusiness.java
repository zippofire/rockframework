package net.woodstock.rockframework.test.jpa.negocio;

import net.woodstock.rockframework.domain.business.GenericBusiness;
import net.woodstock.rockframework.test.jpa.Pessoa;

public interface PessoaBusiness extends GenericBusiness {

	void validarInserir(Pessoa pessoa);
	
}
