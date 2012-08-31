/*
 * This file is part of rockframework.
 * 
 * rockframework is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * rockframework is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>;.
 */
package br.net.woodstock.rockframework.security.cert.ext.icpbrasil;

import java.security.cert.X509Certificate;

public class CertificadoInvalidoICPBrasil extends CertificadoICPBrasil {

	CertificadoInvalidoICPBrasil(final X509Certificate certificate) {
		super(certificate);
		this.setTipoFormato(TipoFormato.DESCONHECIDO);
		this.setTipoPessoa(TipoPessoa.DESCONHECIDO);
	}

}
