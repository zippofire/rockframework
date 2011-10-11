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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;

import org.w3c.dom.NodeList;

import br.net.woodstock.rockframework.util.Assert;

public final class JAIExifReader implements ExifReader {

	private static final String	JPEG_METADATA_FORMAT	= "javax_imageio_jpeg_image_1.0";

	private static final String	TIFF_ROOT_NODE			= "com_sun_media_imageio_plugins_tiff_image_1.0";

	private static final int	SEEK_BYTES				= 6;

	private static final String	TIFF_TAG				= "TIFFIFD";

	private static final String	TIFF_FIELD				= "TIFFField";

	private static final String	TIFF_UNDEFINED			= "TIFFUndefined";

	private static final String	MARKER_SEQUENCE			= "markerSequence";

	private static final String	MARKER_TAG				= "MarkerTag";

	private static final String	MARKER_TAG_VALUE		= "225";

	private static final String	NAME_ATTRIBUTE			= "name";

	private static final String	VALUE_ATTRIBUTE			= "value";

	private static final String	VALUE_SEPARATOR			= ",";

	private static final String	UNKNOWN_TAG				= "unknown";

	private static ExifReader	instance				= new JAIExifReader();

	private JAIExifReader() {
		super();
	}

	public static ExifReader getInstance() {
		return JAIExifReader.instance;
	}

	@Override
	public Map<String, String> getHeaders(final InputStream inputStream) throws IOException {
		Assert.notNull(inputStream, "inputStream");
		ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
		Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);

		if (readers.hasNext()) {
			ImageReader reader = null;

			while (readers.hasNext()) {
				ImageReader tmp = readers.next();
				if (JAIExifReader.JPEG_METADATA_FORMAT.equals(tmp.getOriginatingProvider().getNativeImageMetadataFormatName())) {
					reader = tmp;
					break;
				}
			}

			if (reader != null) {
				reader.setInput(imageInputStream, true, false);
				byte[] bytes = this.getEXIFBytes(reader.getImageMetadata(0));
				if (bytes != null) {
					IIOMetadata metadata = this.getEXIFMetadata(bytes);

					if (metadata != null) {
						System.out.println(metadata.getClass().getCanonicalName());
						return this.parseMetadata(metadata);
					}
				}

				reader.dispose();
				imageInputStream.close();
			}

		}
		return new HashMap<String, String>();
	}

	private byte[] getEXIFBytes(final IIOMetadata meta) {
		IIOMetadataNode root = (IIOMetadataNode) meta.getAsTree(JAIExifReader.JPEG_METADATA_FORMAT);

		IIOMetadataNode node = (IIOMetadataNode) root.getElementsByTagName(JAIExifReader.MARKER_SEQUENCE).item(0);

		NodeList unkowns = node.getElementsByTagName(JAIExifReader.UNKNOWN_TAG);
		for (int i = 0; i < unkowns.getLength(); i++) {
			IIOMetadataNode marker = (IIOMetadataNode) unkowns.item(i);
			String attribute = marker.getAttribute(JAIExifReader.MARKER_TAG);
			if (JAIExifReader.MARKER_TAG_VALUE.equals(attribute)) {
				return (byte[]) marker.getUserObject();
			}
		}
		return null;
	}

	private IIOMetadata getEXIFMetadata(final byte[] bytes) throws IOException {
		Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(ImageType.TIFF.name().toLowerCase());
		if (readers.hasNext()) {
			ImageReader reader = readers.next();
			ImageInputStream wrapper = new MemoryCacheImageInputStream(new ByteArrayInputStream(bytes, JAIExifReader.SEEK_BYTES, bytes.length - JAIExifReader.SEEK_BYTES));
			reader.setInput(wrapper, true, false);

			IIOMetadata metadata = reader.getImageMetadata(0);

			reader.dispose();

			return metadata;
		}

		return null;
	}

	private Map<String, String> parseMetadata(final IIOMetadata metadata) {
		IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(JAIExifReader.TIFF_ROOT_NODE);
		Map<String, String> map = new HashMap<String, String>();

		NodeList tiffNodes = root.getElementsByTagName(JAIExifReader.TIFF_TAG);
		for (int nodeIndex = 0; nodeIndex < tiffNodes.getLength(); nodeIndex++) {
			IIOMetadataNode tiffNode = (IIOMetadataNode) tiffNodes.item(nodeIndex);
			NodeList tiffFields = tiffNode.getElementsByTagName(JAIExifReader.TIFF_FIELD);
			for (int fieldIndex = 0; fieldIndex < tiffFields.getLength(); fieldIndex++) {
				IIOMetadataNode tiffField = (IIOMetadataNode) tiffFields.item(fieldIndex);
				String name = tiffField.getAttribute(JAIExifReader.NAME_ATTRIBUTE);
				StringBuilder builder = new StringBuilder();
				IIOMetadataNode fieldValues = (IIOMetadataNode) tiffField.getFirstChild();

				if (JAIExifReader.TIFF_UNDEFINED.equals(fieldValues.getNodeName())) {
					builder.append(fieldValues.getAttribute(JAIExifReader.VALUE_ATTRIBUTE));
				} else {
					NodeList tiffNumbers = fieldValues.getChildNodes();
					boolean first = true;
					for (int k = 0; k < tiffNumbers.getLength(); k++) {
						IIOMetadataNode valueNode = (IIOMetadataNode) tiffNumbers.item(k);
						String attribute = valueNode.getAttribute(JAIExifReader.VALUE_ATTRIBUTE);
						if (!first) {
							builder.append(JAIExifReader.VALUE_SEPARATOR);
						}
						builder.append(attribute);
						if (first) {
							first = false;
						}
					}
				}
				map.put(name, builder.toString());
			}
		}

		return map;
	}

}
