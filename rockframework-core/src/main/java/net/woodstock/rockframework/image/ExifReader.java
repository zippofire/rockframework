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
package net.woodstock.rockframework.image;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataFormat;
import javax.imageio.stream.ImageInputStream;

import net.woodstock.rockframework.collection.ImmutableMap;
import net.woodstock.rockframework.utils.ConditionUtils;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ExifReader {

	private Map<String, String>	params;

	public ExifReader(final InputStream inputStream) throws IOException {
		super();

		Map<String, String> map = new HashMap<String, String>();
		Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(ImageType.TIFF.getInternalName());
		ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
		if (readers.hasNext()) {
			ImageReader reader = readers.next();
			reader.setInput(imageInputStream);
			IIOMetadata metadata = reader.getImageMetadata(0);
			String[] formatNames = metadata.getMetadataFormatNames();

			if (ConditionUtils.isNotEmpty(formatNames)) {
				for (String formatName : formatNames) {
					IIOMetadataFormat metadataFormat = metadata.getMetadataFormat(formatName);
					if (metadataFormat != null) {
						Node node = metadata.getAsTree(formatName);
						this.configureMap(node, map);
						this.displayMetadata(node, 0);
					}
				}
			}

			String[] extraFormatNames = metadata.getExtraMetadataFormatNames();
			if (ConditionUtils.isNotEmpty(extraFormatNames)) {
				for (String formatName : extraFormatNames) {
					IIOMetadataFormat metadataFormat = metadata.getMetadataFormat(formatName);
					if (metadataFormat != null) {
						Node node = metadata.getAsTree(formatName);
						this.configureMap(node, map);
						this.displayMetadata(node, 0);
					}
				}
			}
		}
		this.params = ImmutableMap.toImmutable(map);
	}

	private void configureMap(final Node node, final Map<String, String> map) {
		map.put(node.getNodeName(), node.getNodeValue());
		if (node.hasChildNodes()) {
			NodeList nodeList = node.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node n = nodeList.item(i);
				this.configureMap(n, map);
			}
		}
	}

	void indent(int level) {
		for (int i = 0; i < level; i++) {
			System.out.print("  ");
		}
	}

	void displayMetadata(Node node, int level) {
		indent(level); // emit open tag
		System.out.print("<" + node.getNodeName());
		NamedNodeMap map = node.getAttributes();
		if (map != null) { // print attribute values
			int length = map.getLength();
			for (int i = 0; i < length; i++) {
				Node attr = map.item(i);
				System.out.print(" " + attr.getNodeName() + "=\"" + attr.getNodeValue() + "\"");
			}
		}

		Node child = node.getFirstChild();
		if (child != null) {
			System.out.println(">"); // close current tag
			while (child != null) { // emit child tags recursively
				displayMetadata(child, level + 1);
				child = child.getNextSibling();
			}
			indent(level); // emit close tag
			System.out.println("</" + node.getNodeName() + ">");
		} else {
			System.out.println("/>");
		}
	}

	public Map<String, String> getParams() {
		return this.params;
	}

}
