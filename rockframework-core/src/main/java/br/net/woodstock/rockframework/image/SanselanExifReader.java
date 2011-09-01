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
package br.net.woodstock.rockframework.image;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.common.RationalNumber;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.constants.TiffFieldTypeConstants;

import br.net.woodstock.rockframework.utils.ArrayUtils;
import br.net.woodstock.rockframework.utils.IOUtils;
import br.net.woodstock.rockframework.utils.NumberUtils;

public final class SanselanExifReader implements ExifReader {

	private static ExifReader	instance	= new SanselanExifReader();

	private SanselanExifReader() {
		super();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, String> getHeaders(final InputStream inputStream) throws IOException {
		try {
			IImageMetadata metadata = Sanselan.getMetadata(IOUtils.toByteArray(inputStream));
			JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
			if (jpegMetadata != null) {
				TiffImageMetadata exif = jpegMetadata.getExif();
				if (exif != null) {
					Map<String, String> headers = new HashMap<String, String>();
					List<TiffField> fields = exif.getAllFields();

					for (TiffField field : fields) {
						String key = field.getTagName();
						String value = this.getFieldValue(field);
						headers.put(key, value);
					}

					return headers;
				}
			}
			return new HashMap<String, String>();
		} catch (ImageReadException e) {
			throw new IOException(e);
		}
	}

	private String getFieldValue(final TiffField field) throws ImageReadException {
		Object value = field.getValue();
		if (value != null) {
			if (value instanceof Number) {
				return value.toString();
			}
			if (value instanceof RationalNumber) {
				RationalNumber rn = (RationalNumber) value;
				return rn.toDisplayString();
			}
			if (value instanceof String) {
				if (field.getFieldTypeName().equals(TiffFieldTypeConstants.FIELD_TYPE_UNDEFINED.name)) {
					byte[] bytes = ((String) value).getBytes();
					return ArrayUtils.toString(bytes);
				}
				return (String) value;
			}

			if (value.getClass().isArray()) {
				int len = Array.getLength(value);
				int[] array = new int[len];
				for (int i = 0; i < len; i++) {
					Byte b = (Byte) Array.get(value, i);
					array[i] = NumberUtils.unsignedByteToInt(b.byteValue());
				}
				return ArrayUtils.toString(array);
			}
			throw new IllegalArgumentException("Classe nao tratada: " + value.getClass().getCanonicalName());
		}

		return null;
	}

	public static ExifReader getInstance() {
		return instance;
	}

}
