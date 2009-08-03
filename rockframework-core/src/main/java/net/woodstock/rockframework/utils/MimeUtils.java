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
package net.woodstock.rockframework.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public abstract class MimeUtils {

	private static Map<String, Collection<String>>	mimeMap;

	private MimeUtils() {
		//
	}

	public static String getMimeType(String extension) {
		if (MimeUtils.mimeMap == null) {
			MimeUtils.init();
		}
		for (Entry<String, Collection<String>> e : MimeUtils.mimeMap.entrySet()) {
			if (e.getValue().contains(extension)) {
				return e.getKey();
			}
		}
		return null;
	}

	public static String getExtension(String mimeType) {
		if (MimeUtils.mimeMap == null) {
			MimeUtils.init();
		}
		for (Entry<String, Collection<String>> e : MimeUtils.mimeMap.entrySet()) {
			if (e.getKey().equalsIgnoreCase(mimeType)) {
				return e.getValue().iterator().next();
			}
		}
		return null;
	}

	private static void createMimeMap(String name, String[] extensions) {
		Collection<String> c = new HashSet<String>();
		for (String s : extensions) {
			if (!StringUtils.isEmpty(s)) {
				c.add(s);
			}
		}
		MimeUtils.mimeMap.put(name, c);
	}

	private static void init() {
		// grep -v ^# /etc/mime.types | \
		// grep -v ^$ | \
		// awk '{print "createMimeMap(\StringUtils.BLANK$1"\", new String[]{\StringUtils.BLANK$2"\", \StringUtils.BLANK$3"\",\StringUtils.BLANK$4"\", \StringUtils.BLANK$5"\"});"}'
		MimeUtils.mimeMap = new TreeMap<String, Collection<String>>();
		MimeUtils.createMimeMap("application/activemessage", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/andrew-inset", new String[] { "ez", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/applefile", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/atom+xml", new String[] { "atom", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/atomcat+xml", new String[] { "atomcat", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/atomserv+xml", new String[] { "atomsrv", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/atomicmail", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/batch-SMTP", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/beep+xml", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/cals-1840", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/cap", new String[] { "cap", "pcap", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/commonground", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/cu-seeme", new String[] { "cu", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/cybercash", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/davmount+xml", new String[] { "davmount", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/dca-rft", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/dec-dx", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/docbook+xml", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/dsptype", new String[] { "tsp", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/dvcs", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/edi-consent", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/edi-x12", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/edifact", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/eshop", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/font-tdpfr", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/futuresplash", new String[] { "spl", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/ghostview", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/hta", new String[] { "hta", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/http", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/hyperstudio", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/iges", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/index", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/index.cmd", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/index.obj", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/index.response", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/index.vnd", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/iotp", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/ipp", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/isup", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/java-archive", new String[] { "jar", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/java-serialized-object", new String[] { "ser", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/java-vm", new String[] { "class", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/m3g", new String[] { "m3g", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/mac-binhex40", new String[] { "hqx", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/mac-compactpro", new String[] { "cpt", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/macwriteii", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/marc", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/mathematica", new String[] { "nb", "nbp", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/ms-tnef", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/msaccess", new String[] { "mdb", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/msword", new String[] { "doc", "dot", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/news-message-id", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/news-transmission", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/ocsp-request", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/ocsp-response", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/octet-stream", new String[] { "bin", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/oda", new String[] { "oda", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/ogg", new String[] { "ogg", "ogm", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/parityfec", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/pdf", new String[] { "pdf", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/pgp-encrypted", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/pgp-keys", new String[] { "key", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/pgp-signature", new String[] { "pgp", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/pics-rules", new String[] { "prf", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/pkcs10", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/pkcs7-mime", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/pkcs7-signature", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/pkix-cert", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/pkix-crl", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/pkixcmp", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/postscript", new String[] { "ps", "ai", "eps", "espi" });
		MimeUtils.createMimeMap("application/prs.alvestrand.titrax-sheet", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/prs.cww", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/prs.nprend", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/qsig", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/rar", new String[] { "rar", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/rdf+xml", new String[] { "rdf", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/remote-printing", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/riscos", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/rss+xml", new String[] { "rss", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/rtf", new String[] { "rtf", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/sdp", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/set-payment", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/set-payment-initiation", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/set-registration", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/set-registration-initiation", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/sgml", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/sgml-open-catalog", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/sieve", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/slate", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/smil", new String[] { "smi", "smil", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/timestamp-query", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/timestamp-reply", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vemmi", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/whoispp-query", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/whoispp-response", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/wita", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/wordperfect", new String[] { "wpd", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/wordperfect5.1", new String[] { "wp5", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x400-bp", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/xhtml+xml", new String[] { "xhtml", "xht", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/xml", new String[] { "xml", "xsl", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/xml-dtd", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/xml-external-parsed-entity", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/xspf+xml", new String[] { "xspf", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/zip", new String[] { "zip", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.3M.Post-it-Notes", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.accpac.simply.aso", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.accpac.simply.imp", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.acucobol", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.aether.imp", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.anser-web-certificate-issue-initiation", new String[] { StringUtils.BLANK,
				StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.anser-web-funds-transfer-initiation", new String[] { StringUtils.BLANK, StringUtils.BLANK,
				StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.audiograph", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.bmi", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.businessobjects", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.canon-cpdl", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.canon-lips", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.cinderella", new String[] { "cdy", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.claymore", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.commerce-battelle", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.commonspace", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.comsocaller", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.contact.cmsg", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.cosmocaller", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ctc-posml", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.cups-postscript", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.cups-raster", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.cups-raw", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.cybank", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.dna", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.dpgraph", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.dxr", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ecdis-update", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ecowin.chart", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ecowin.filerequest", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ecowin.fileupdate", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ecowin.series", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ecowin.seriesrequest", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ecowin.seriesupdate", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.enliven", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.epson.esf", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.epson.msf", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.epson.quickanime", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.epson.salt", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.epson.ssf", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ericsson.quickcall", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.eudora.data", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.fdf", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ffsns", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.flographit", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.framemaker", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.fsc.weblaunch", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.fujitsu.oasys", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.fujitsu.oasys2", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.fujitsu.oasys3", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.fujitsu.oasysgp", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.fujitsu.oasysprs", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.fujixerox.ddd", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.fujixerox.docuworks", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils
				.createMimeMap("application/vnd.fujixerox.docuworks.binder", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.fut-misnet", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.google-earth.kml+xml", new String[] { "kml", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.google-earth.kmz", new String[] { "kmz", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.grafeq", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.groove-account", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.groove-identity-message", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.groove-injector", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.groove-tool-message", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.groove-tool-template", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.groove-vcard", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.hhe.lesson-player", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.hp-HPGL", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.hp-PCL", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.hp-PCLXL", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.hp-hpid", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.hp-hps", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.httphone", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.hzn-3d-crossword", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ibm.MiniPay", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ibm.afplinedata", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ibm.modcap", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.informix-visionary", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.intercon.formnet", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.intertrust.digibox", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.intertrust.nncp", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.intu.qbo", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.intu.qfx", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.irepository.package+xml", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.is-xpr", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils
				.createMimeMap("application/vnd.japannet-directory-service", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.japannet-jpnstore-wakeup", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.japannet-payment-wakeup", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.japannet-registration", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.japannet-registration-wakeup",
				new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.japannet-setstore-wakeup", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.japannet-verification", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.japannet-verification-wakeup",
				new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.koan", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.lotus-1-2-3", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.lotus-approach", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.lotus-freelance", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.lotus-notes", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.lotus-organizer", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.lotus-screencam", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.lotus-wordpro", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.mcd", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.mediastation.cdkey", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.meridian-slingshot", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.mif", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.minisoft-hp3000-save", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.mitsubishi.misty-guard.trustweb", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK,
				StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.mobius.daf", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.mobius.dis", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.mobius.msl", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.mobius.plc", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.mobius.txf", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.motorola.flexsuite", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.motorola.flexsuite.adsi", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.motorola.flexsuite.fis", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.motorola.flexsuite.gotap", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.motorola.flexsuite.kmr", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.motorola.flexsuite.ttc", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.motorola.flexsuite.wem", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.mozilla.xul+xml", new String[] { "xul", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ms-artgalry", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ms-asf", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ms-excel", new String[] { "xls", "xlb", "xlt", StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ms-lrm", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ms-pki.seccat", new String[] { "cat", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ms-pki.stl", new String[] { "stl", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ms-powerpoint", new String[] { "ppt", "pps", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ms-project", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ms-tnef", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ms-works", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.mseq", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.msign", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.music-niff", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.musician", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.netfpx", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.noblenet-directory", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.noblenet-sealer", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.noblenet-web", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.novadigm.EDM", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.novadigm.EDX", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.novadigm.EXT", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.chart",
				new String[] { "odc", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.database", new String[] { "odb", StringUtils.BLANK, StringUtils.BLANK,
				StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.formula", new String[] { "odf", StringUtils.BLANK, StringUtils.BLANK,
				StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.graphics", new String[] { "odg", StringUtils.BLANK, StringUtils.BLANK,
				StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.graphics-template", new String[] { "otg",
				StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.image",
				new String[] { "odi", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.presentation", new String[] { "odp", StringUtils.BLANK,
				StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.presentation-template", new String[] {
				"otp", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.spreadsheet", new String[] { "ods", StringUtils.BLANK,
				StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.spreadsheet-template", new String[] {
				"ots", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils
				.createMimeMap("application/vnd.oasis.opendocument.text", new String[] { "odt", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.text-master", new String[] { "odm", StringUtils.BLANK,
				StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.text-template", new String[] { "ott", StringUtils.BLANK,
				StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.text-web", new String[] { "oth", StringUtils.BLANK, StringUtils.BLANK,
				StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.osa.netdeploy", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.palm", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.pg.format", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.pg.osasli", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.powerbuilder6", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.powerbuilder6-s", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.powerbuilder7", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.powerbuilder7-s", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.powerbuilder75", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.powerbuilder75-s", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.previewsystems.box", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.publishare-delta-tree", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.pvi.ptid1", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.pwg-xhtml-print+xml", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.rapid", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.rim.cod", new String[] { "cod", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.s3sms", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.seemail", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.shana.informed.formdata", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.shana.informed.formtemplate",
				new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils
				.createMimeMap("application/vnd.shana.informed.interchange", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.shana.informed.package", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.smaf", new String[] { "mmf", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.sss-cod", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.sss-dtf", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.sss-ntf", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.stardivision.calc", new String[] { "sdc", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.stardivision.chart", new String[] { "sds", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.stardivision.draw", new String[] { "sda", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.stardivision.impress", new String[] { "sdd", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.stardivision.math", new String[] { "sdf", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.stardivision.writer", new String[] { "sdw", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.stardivision.writer-global", new String[] { "sgl", StringUtils.BLANK, StringUtils.BLANK,
				StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.street-stream", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.sun.xml.calc", new String[] { "sxc", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.sun.xml.calc.template", new String[] { "stc", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.sun.xml.draw", new String[] { "sxd", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.sun.xml.draw.template", new String[] { "std", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.sun.xml.impress", new String[] { "sxi", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.sun.xml.impress.template",
				new String[] { "sti", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.sun.xml.math", new String[] { "sxm", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.sun.xml.writer", new String[] { "sxw", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.sun.xml.writer.global", new String[] { "sxg", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils
				.createMimeMap("application/vnd.sun.xml.writer.template", new String[] { "stw", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.svd", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.swiftview-ics", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.symbian.install", new String[] { "sis", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.triscape.mxs", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.trueapp", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.truedoc", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.tve-trigger", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.ufdl", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.uplanet.alert", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.uplanet.alert-wbxml", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.uplanet.bearer-choice", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.uplanet.bearer-choice-wbxml",
				new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.uplanet.cacheop", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.uplanet.cacheop-wbxml", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.uplanet.channel", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.uplanet.channel-wbxml", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.uplanet.list", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.uplanet.list-wbxml", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.uplanet.listcmd", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.uplanet.listcmd-wbxml", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.uplanet.signal", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.vcx", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.vectorworks", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.vidsoft.vidconference", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.visio", new String[] { "vsd", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.vividence.scriptfile", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.wap.sic", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.wap.slc", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.wap.wbxml", new String[] { "wbxml", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.wap.wmlc", new String[] { "wmlc", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.wap.wmlscriptc", new String[] { "wmlsc", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.webturbo", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.wrq-hp3000-labelled", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.wt.stf", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.xara", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.xfdl", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/vnd.yellowriver-custom-menu", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-123", new String[] { "wk", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-7z-compressed", new String[] { "7z", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-abiword", new String[] { "abw", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-apple-diskimage", new String[] { "dmg", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-bcpio", new String[] { "bcpio", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-bittorrent", new String[] { "torrent", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-cab", new String[] { "cab", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-cbr", new String[] { "cbr", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-cbz", new String[] { "cbz", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-cdf", new String[] { "cdf", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-cdlink", new String[] { "vcd", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-chess-pgn", new String[] { "pgn", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-core", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-cpio", new String[] { "cpio", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-csh", new String[] { "csh", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-debian-package", new String[] { "deb", "udeb", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-director", new String[] { "dcr", "dir", "dxr", StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-dms", new String[] { "dms", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-doom", new String[] { "wad", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-dvi", new String[] { "dvi", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-httpd-eruby", new String[] { "rhtml", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-executable", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-flac", new String[] { "flac", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-font", new String[] { "pfa", "pfb", "gsf", "pcf" });
		MimeUtils.createMimeMap("application/x-freemind", new String[] { "mm", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-futuresplash", new String[] { "spl", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-gnumeric", new String[] { "gnumeric", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-go-sgf", new String[] { "sgf", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-graphing-calculator", new String[] { "gcf", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-gtar", new String[] { "gtar", "tgz", "taz", StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-hdf", new String[] { "hdf", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-httpd-php", new String[] { "phtml", "pht", "php", StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-httpd-php-source", new String[] { "phps", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-httpd-php3", new String[] { "php3", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils
				.createMimeMap("application/x-httpd-php3-preprocessed", new String[] { "php3p", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-httpd-php4", new String[] { "php4", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-ica", new String[] { "ica", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-internet-signup", new String[] { "ins", "isp", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-iphone", new String[] { "iii", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-iso9660-image", new String[] { "iso", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-jam", new String[] { "jam", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-java-applet", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-java-bean", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-java-jnlp-file", new String[] { "jnlp", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-javascript", new String[] { "js", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-jmol", new String[] { "jmz", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-kchart", new String[] { "chrt", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-kdelnk", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-killustrator", new String[] { "kil", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-koan", new String[] { "skp", "skd", "skt", "skm" });
		MimeUtils.createMimeMap("application/x-kpresenter", new String[] { "kpr", "kpt", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-kspread", new String[] { "ksp", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-kword", new String[] { "kwd", "kwt", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-latex", new String[] { "latex", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-lha", new String[] { "lha", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-lyx", new String[] { "lyx", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-lzh", new String[] { "lzh", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-lzx", new String[] { "lzx", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-maker", new String[] { "frm", "maker", "frame", "fm" });
		MimeUtils.createMimeMap("application/x-mif", new String[] { "mif", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-ms-wmd", new String[] { "wmd", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-ms-wmz", new String[] { "wmz", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-msdos-program", new String[] { "com", "exe", "bat", "dll" });
		MimeUtils.createMimeMap("application/x-msi", new String[] { "msi", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-netcdf", new String[] { "nc", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-ns-proxy-autoconfig", new String[] { "pac", "dat", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-nwc", new String[] { "nwc", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-object", new String[] { "o", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-oz-application", new String[] { "oza", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-pkcs7-certreqresp", new String[] { "p7r", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-pkcs7-crl", new String[] { "crl", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-python-code", new String[] { "pyc", "pyo", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-quicktimeplayer", new String[] { "qtl", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-redhat-package-manager", new String[] { "rpm", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-ruby", new String[] { "rb", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-rx", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-sh", new String[] { "sh", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-shar", new String[] { "shar", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-shellscript", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-shockwave-flash", new String[] { "swf", "swfl", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-stuffit", new String[] { "sit", "sitx", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-sv4cpio", new String[] { "sv4cpio", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-sv4crc", new String[] { "sv4crc", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-tar", new String[] { "tar", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-tcl", new String[] { "tcl", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-tex-gf", new String[] { "gf", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-tex-pk", new String[] { "pk", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-texinfo", new String[] { "texinfo", "texi", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-trash", new String[] { "~", "%", "bak", "old" });
		MimeUtils.createMimeMap("application/x-troff", new String[] { "t", "tr", "roff", StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-troff-man", new String[] { "man", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-troff-me", new String[] { "me", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-troff-ms", new String[] { "ms", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-ustar", new String[] { "ustar", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-videolan", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-wais-source", new String[] { "src", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-wingz", new String[] { "wz", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-x509-ca-cert", new String[] { "crt", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-xcf", new String[] { "xcf", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-xfig", new String[] { "fig", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("application/x-xpinstall", new String[] { "xpi", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/32kadpcm", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/3gpp", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/basic", new String[] { "au", "snd", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/g.722.1", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/l16", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/midi", new String[] { "mid", "midi", "kar", StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/mp4a-latm", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/mpa-robust", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/mpeg", new String[] { "mpga", "mpega", "mp2", "mp3" });
		MimeUtils.createMimeMap("audio/mpegurl", new String[] { "m3u", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/parityfec", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/prs.sid", new String[] { "sid", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/telephone-event", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/tone", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/vnd.cisco.nse", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/vnd.cns.anp1", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/vnd.cns.inf1", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/vnd.digital-winds", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/vnd.everad.plj", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/vnd.lucent.voice", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/vnd.nortel.vbk", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/vnd.nuera.ecelp4800", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/vnd.nuera.ecelp7470", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/vnd.nuera.ecelp9600", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/vnd.octel.sbc", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/vnd.qcelp", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/vnd.rhetorex.32kadpcm", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/vnd.vmx.cvsd", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/x-aiff", new String[] { "aif", "aiff", "aifc", StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/x-gsm", new String[] { "gsm", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/x-mpegurl", new String[] { "m3u", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/x-ms-wma", new String[] { "wma", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/x-ms-wax", new String[] { "wax", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/x-pn-realaudio-plugin", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/x-pn-realaudio", new String[] { "ra", "rm", "ram", StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/x-realaudio", new String[] { "ra", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/x-scpls", new String[] { "pls", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/x-sd2", new String[] { "sd2", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("audio/x-wav", new String[] { "wav", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-alchemy", new String[] { "alc", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-cache", new String[] { "cac", "cache", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-cache-csf", new String[] { "csf", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-cactvs-binary", new String[] { "cbin", "cascii", "ctab", StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-cdx", new String[] { "cdx", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-cerius", new String[] { "cer", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-chem3d", new String[] { "c3d", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-chemdraw", new String[] { "chm", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-cif", new String[] { "cif", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-cmdf", new String[] { "cmdf", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-cml", new String[] { "cml", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-compass", new String[] { "cpa", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-crossfire", new String[] { "bsd", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-csml", new String[] { "csml", "csm", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-ctx", new String[] { "ctx", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-cxf", new String[] { "cxf", "cef", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-embl-dl-nucleotide", new String[] { "emb", "embl", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-galactic-spc", new String[] { "spc", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-gamess-input", new String[] { "inp", "gam", "gamin", StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-gaussian-checkpoint", new String[] { "fch", "fchk", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-gaussian-cube", new String[] { "cub", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-gaussian-input", new String[] { "gau", "gjc", "gjf", StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-gaussian-log", new String[] { "gal", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-gcg8-sequence", new String[] { "gcg", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-genbank", new String[] { "gen", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-hin", new String[] { "hin", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-isostar", new String[] { "istr", "ist", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-jcamp-dx", new String[] { "jdx", "dx", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-kinemage", new String[] { "kin", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-macmolecule", new String[] { "mcm", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-macromodel-input", new String[] { "mmd", "mmod", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-mdl-molfile", new String[] { "mol", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-mdl-rdfile", new String[] { "rd", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-mdl-rxnfile", new String[] { "rxn", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-mdl-sdfile", new String[] { "sd", "sdf", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-mdl-tgf", new String[] { "tgf", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-mmcif", new String[] { "mcif", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-mol2", new String[] { "mol2", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-molconn-Z", new String[] { "b", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-mopac-graph", new String[] { "gpt", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-mopac-input", new String[] { "mop", "mopcrt", "mpc", "zmt" });
		MimeUtils.createMimeMap("chemical/x-mopac-out", new String[] { "moo", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-mopac-vib", new String[] { "mvb", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-ncbi-asn1", new String[] { "asn", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-ncbi-asn1-ascii", new String[] { "prt", "ent", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-ncbi-asn1-binary", new String[] { "val", "aso", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-ncbi-asn1-spec", new String[] { "asn", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-pdb", new String[] { "pdb", "ent", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-rosdal", new String[] { "ros", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-swissprot", new String[] { "sw", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-vamas-iso14976", new String[] { "vms", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-vmd", new String[] { "vmd", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-xtel", new String[] { "xtel", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("chemical/x-xyz", new String[] { "xyz", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/cgm", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/g3fax", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/gif", new String[] { "gif", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/ief", new String[] { "ief", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/jpeg", new String[] { "jpeg", "jpg", "jpe", StringUtils.BLANK });
		MimeUtils.createMimeMap("image/naplps", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/pcx", new String[] { "pcx", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/png", new String[] { "png", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/prs.btif", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/prs.pti", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/svg+xml", new String[] { "svg", "svgz", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/tiff", new String[] { "tiff", "tif", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/vnd.cns.inf2", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/vnd.djvu", new String[] { "djvu", "djv", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/vnd.dwg", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/vnd.dxf", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/vnd.fastbidsheet", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/vnd.fpx", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/vnd.fst", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/vnd.fujixerox.edmics-mmr", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/vnd.fujixerox.edmics-rlc", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/vnd.mix", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/vnd.net-fpx", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/vnd.svf", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/vnd.wap.wbmp", new String[] { "wbmp", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/vnd.xiff", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/x-cmu-raster", new String[] { "ras", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/x-coreldraw", new String[] { "cdr", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/x-coreldrawpattern", new String[] { "pat", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/x-coreldrawtemplate", new String[] { "cdt", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/x-corelphotopaint", new String[] { "cpt", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/x-icon", new String[] { "ico", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/x-jg", new String[] { "art", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/x-jng", new String[] { "jng", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/x-ms-bmp", new String[] { "bmp", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/x-photoshop", new String[] { "psd", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/x-portable-anymap", new String[] { "pnm", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/x-portable-bitmap", new String[] { "pbm", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/x-portable-graymap", new String[] { "pgm", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/x-portable-pixmap", new String[] { "ppm", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/x-rgb", new String[] { "rgb", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/x-xbitmap", new String[] { "xbm", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/x-xpixmap", new String[] { "xpm", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("image/x-xwindowdump", new String[] { "xwd", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("inode/chardevice", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("inode/blockdevice", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("inode/directory-locked", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("inode/directory", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("inode/fifo", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("inode/socket", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("message/delivery-status", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("message/disposition-notification", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("message/external-body", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("message/http", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("message/s-http", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("message/news", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("message/partial", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("message/rfc822", new String[] { "eml", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("model/iges", new String[] { "igs", "iges", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("model/mesh", new String[] { "msh", "mesh", "silo", StringUtils.BLANK });
		MimeUtils.createMimeMap("model/vnd.dwf", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("model/vnd.flatland.3dml", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("model/vnd.gdl", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("model/vnd.gs-gdl", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("model/vnd.gtw", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("model/vnd.mts", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("model/vnd.vtu", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("model/vrml", new String[] { "wrl", "vrml", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("multipart/alternative", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("multipart/appledouble", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("multipart/byteranges", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("multipart/digest", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("multipart/encrypted", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("multipart/form-data", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("multipart/header-set", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("multipart/mixed", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("multipart/parallel", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("multipart/related", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("multipart/report", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("multipart/signed", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("multipart/voice-message", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/calendar", new String[] { "ics", "icz", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/css", new String[] { "css", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/csv", new String[] { "csv", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/directory", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/english", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/enriched", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/h323", new String[] { "323", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/html", new String[] { "html", "htm", "shtml", StringUtils.BLANK });
		MimeUtils.createMimeMap("text/iuls", new String[] { "uls", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/mathml", new String[] { "mml", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/parityfec", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/plain", new String[] { "asc", "txt", "text", "pot" });
		MimeUtils.createMimeMap("text/prs.lines.tag", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/rfc822-headers", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/richtext", new String[] { "rtx", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/rtf", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/scriptlet", new String[] { "sct", "wsc", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/t140", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/texmacs", new String[] { "tm", "ts", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/tab-separated-values", new String[] { "tsv", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/uri-list", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/vnd.abc", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/vnd.curl", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/vnd.DMClientScript", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/vnd.flatland.3dml", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/vnd.fly", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/vnd.fmi.flexstor", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/vnd.in3d.3dml", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/vnd.in3d.spot", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/vnd.IPTC.NewsML", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/vnd.IPTC.NITF", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/vnd.latex-z", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/vnd.motorola.reflex", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/vnd.ms-mediapackage", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/vnd.sun.j2me.app-descriptor", new String[] { "jad", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/vnd.wap.si", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/vnd.wap.sl", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/vnd.wap.wml", new String[] { "wml", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/vnd.wap.wmlscript", new String[] { "wmls", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-bibtex", new String[] { "bib", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-boo", new String[] { "boo", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-c++hdr", new String[] { "h++", "hpp", "hxx", "hh" });
		MimeUtils.createMimeMap("text/x-c++src", new String[] { "c++", "cpp", "cxx", "cc" });
		MimeUtils.createMimeMap("text/x-chdr", new String[] { "h", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-component", new String[] { "htc", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-crontab", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-csh", new String[] { "csh", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-csrc", new String[] { "c", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-dsrc", new String[] { "d", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-diff", new String[] { "diff", "patch", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-haskell", new String[] { "hs", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-java", new String[] { "java", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-literate-haskell", new String[] { "lhs", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-makefile", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-moc", new String[] { "moc", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-pascal", new String[] { "p", "pas", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-pcs-gcd", new String[] { "gcd", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-perl", new String[] { "pl", "pm", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-python", new String[] { "py", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-server-parsed-html", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-setext", new String[] { "etx", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-sh", new String[] { "sh", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-tcl", new String[] { "tcl", "tk", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-tex", new String[] { "tex", "ltx", "sty", "cls" });
		MimeUtils.createMimeMap("text/x-vcalendar", new String[] { "vcs", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("text/x-vcard", new String[] { "vcf", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/3gpp", new String[] { "3gp", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/dl", new String[] { "dl", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/dv", new String[] { "dif", "dv", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/fli", new String[] { "fli", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/gl", new String[] { "gl", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/mpeg", new String[] { "mpeg", "mpg", "mpe", StringUtils.BLANK });
		MimeUtils.createMimeMap("video/mp4", new String[] { "mp4", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/quicktime", new String[] { "qt", "mov", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/mp4v-es", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/parityfec", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/pointer", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/vnd.fvt", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/vnd.motorola.video", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/vnd.motorola.videop", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/vnd.mpegurl", new String[] { "mxu", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/vnd.mts", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/vnd.nokia.interleaved-multimedia", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/vnd.vivo", new String[] { StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/x-flv", new String[] { "flv", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/x-la-asf", new String[] { "lsf", "lsx", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/x-mng", new String[] { "mng", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/x-ms-asf", new String[] { "asf", "asx", StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/x-ms-wm", new String[] { "wm", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/x-ms-wmv", new String[] { "wmv", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/x-ms-wmx", new String[] { "wmx", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/x-ms-wvx", new String[] { "wvx", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/x-msvideo", new String[] { "avi", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("video/x-sgi-movie", new String[] { "movie", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("x-conference/x-cooltalk", new String[] { "ice", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("x-epoc/x-sisx-app", new String[] { "sisx", StringUtils.BLANK, StringUtils.BLANK, StringUtils.BLANK });
		MimeUtils.createMimeMap("x-world/x-vrml", new String[] { "vrm", "vrml", "wrl", StringUtils.BLANK });
	}

}
