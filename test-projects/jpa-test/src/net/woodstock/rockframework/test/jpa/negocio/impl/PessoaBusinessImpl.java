package net.woodstock.rockframework.test.jpa.negocio.impl;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.business.ValidationException;
import net.woodstock.rockframework.domain.business.ValidationResult;
import net.woodstock.rockframework.domain.business.impl.AbstractLocalBusiness;
import net.woodstock.rockframework.test.jpa.Pessoa;
import net.woodstock.rockframework.test.jpa.negocio.PessoaBusiness;

public class PessoaBusinessImpl extends AbstractLocalBusiness implements PessoaBusiness {

	@Override
	public void validarInserir(Pessoa pessoa) {
		// if(pessoa.getId() != null) {
		// throw new ValidationException("O ID deve ser nulo para gravar");
		// }
		// if(pessoa.getNome() == null) {
		// throw new ValidationException("O nome deve ser nao nulo para gravar");
		// }
		// if(pessoa.getNome().length() > 50) {
		// throw new ValidationException("O nome deve menor que 50");
		// }
		ValidationResult result = super.validateSave(pessoa);
		if (result.isError()) {
			throw new ValidationException(result.getMessage());
		}
	}

}
