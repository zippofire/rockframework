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
package net.woodstock.rockframework.itext.types;

import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.Barcode39;
import com.lowagie.text.pdf.BarcodeCodabar;
import com.lowagie.text.pdf.BarcodeEAN;
import com.lowagie.text.pdf.BarcodeInter25;
import com.lowagie.text.pdf.BarcodePostnet;

public enum Barcode {

	BARCODE_128, BARCODE_128RAW, BARCODE_128UCC, BARCODE_39, BARCODE_CODABAR, BARCODE_EAN, BARCODE_INTERLEAVED, BARCODE_PLANET, BARCODE_POSTNET, DEFAULT;

	public com.lowagie.text.pdf.Barcode getBarcode() {
		com.lowagie.text.pdf.Barcode barcode = null;
		if (this.name().equals("BARCODE_128")) {
			barcode = new Barcode128();
		} else if (this.name().equals("BARCODE_128RAW")) {
			barcode = new Barcode128();
			barcode.setCodeType(com.lowagie.text.pdf.Barcode.CODE128_RAW);
		} else if (this.name().equals("BARCODE_128UCC")) {
			barcode = new Barcode128();
			barcode.setCodeType(com.lowagie.text.pdf.Barcode.CODE128_UCC);
		} else if (this.name().equals("BARCODE_39")) {
			barcode = new Barcode39();
		} else if (this.name().equals("BARCODE_CODABAR")) {
			barcode = new BarcodeCodabar();
		} else if (this.name().equals("BARCODE_EAN")) {
			barcode = new BarcodeEAN();
			barcode.setCodeType(com.lowagie.text.pdf.Barcode.EAN13);
		} else if (this.name().equals("BARCODE_INTERLEAVED")) {
			barcode = new BarcodeInter25();
		} else if (this.name().equals("BARCODE_PLANET")) {
			barcode = new BarcodePostnet();
			barcode.setCodeType(com.lowagie.text.pdf.Barcode.PLANET);
		} else if (this.name().equals("BARCODE_POSTNET")) {
			barcode = new BarcodePostnet();
		} else {
			barcode = new Barcode128();
		}
		return barcode;
	}

}
