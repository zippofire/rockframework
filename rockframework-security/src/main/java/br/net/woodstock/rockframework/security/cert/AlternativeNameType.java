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
package br.net.woodstock.rockframework.security.cert;

public enum AlternativeNameType {
	OTHER_NAME(0), /**/
	RFC_822_NAME(1), /**/
	DNS_NAME(2), /**/
	X400ADDRESS(3), /**/
	DIRECTORY_NAME(4), /**/
	EDI_PART_NAME(5), /**/
	UNIFORM_RESOURCE_IDENTIFIER(6), /**/
	IP_ADDRESS(7), /**/
	REGISTERED_ID(8); /**/

	private int	type;

	private AlternativeNameType(final int type) {
		this.type = type;
	}

	public int getType() {
		return this.type;
	}
}
