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
		// awk '{print "createMimeMap(\""$1"\", new String[]{\""$2"\", \""$3"\",\""$4"\", \""$5"\"});"}'
		MimeUtils.mimeMap = new TreeMap<String, Collection<String>>();
		MimeUtils.createMimeMap("application/activemessage", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/andrew-inset", new String[] { "ez", "", "", "" });
		MimeUtils.createMimeMap("application/applefile", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/atom+xml", new String[] { "atom", "", "", "" });
		MimeUtils.createMimeMap("application/atomcat+xml", new String[] { "atomcat", "", "", "" });
		MimeUtils.createMimeMap("application/atomserv+xml", new String[] { "atomsrv", "", "", "" });
		MimeUtils.createMimeMap("application/atomicmail", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/batch-SMTP", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/beep+xml", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/cals-1840", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/cap", new String[] { "cap", "pcap", "", "" });
		MimeUtils.createMimeMap("application/commonground", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/cu-seeme", new String[] { "cu", "", "", "" });
		MimeUtils.createMimeMap("application/cybercash", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/davmount+xml", new String[] { "davmount", "", "", "" });
		MimeUtils.createMimeMap("application/dca-rft", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/dec-dx", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/docbook+xml", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/dsptype", new String[] { "tsp", "", "", "" });
		MimeUtils.createMimeMap("application/dvcs", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/edi-consent", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/edi-x12", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/edifact", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/eshop", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/font-tdpfr", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/futuresplash", new String[] { "spl", "", "", "" });
		MimeUtils.createMimeMap("application/ghostview", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/hta", new String[] { "hta", "", "", "" });
		MimeUtils.createMimeMap("application/http", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/hyperstudio", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/iges", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/index", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/index.cmd", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/index.obj", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/index.response", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/index.vnd", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/iotp", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/ipp", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/isup", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/java-archive", new String[] { "jar", "", "", "" });
		MimeUtils.createMimeMap("application/java-serialized-object", new String[] { "ser", "", "", "" });
		MimeUtils.createMimeMap("application/java-vm", new String[] { "class", "", "", "" });
		MimeUtils.createMimeMap("application/m3g", new String[] { "m3g", "", "", "" });
		MimeUtils.createMimeMap("application/mac-binhex40", new String[] { "hqx", "", "", "" });
		MimeUtils.createMimeMap("application/mac-compactpro", new String[] { "cpt", "", "", "" });
		MimeUtils.createMimeMap("application/macwriteii", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/marc", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/mathematica", new String[] { "nb", "nbp", "", "" });
		MimeUtils.createMimeMap("application/ms-tnef", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/msaccess", new String[] { "mdb", "", "", "" });
		MimeUtils.createMimeMap("application/msword", new String[] { "doc", "dot", "", "" });
		MimeUtils.createMimeMap("application/news-message-id", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/news-transmission", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/ocsp-request", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/ocsp-response", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/octet-stream", new String[] { "bin", "", "", "" });
		MimeUtils.createMimeMap("application/oda", new String[] { "oda", "", "", "" });
		MimeUtils.createMimeMap("application/ogg", new String[] { "ogg", "ogm", "", "" });
		MimeUtils.createMimeMap("application/parityfec", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/pdf", new String[] { "pdf", "", "", "" });
		MimeUtils.createMimeMap("application/pgp-encrypted", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/pgp-keys", new String[] { "key", "", "", "" });
		MimeUtils.createMimeMap("application/pgp-signature", new String[] { "pgp", "", "", "" });
		MimeUtils.createMimeMap("application/pics-rules", new String[] { "prf", "", "", "" });
		MimeUtils.createMimeMap("application/pkcs10", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/pkcs7-mime", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/pkcs7-signature", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/pkix-cert", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/pkix-crl", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/pkixcmp", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/postscript", new String[] { "ps", "ai", "eps", "espi" });
		MimeUtils.createMimeMap("application/prs.alvestrand.titrax-sheet", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/prs.cww", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/prs.nprend", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/qsig", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/rar", new String[] { "rar", "", "", "" });
		MimeUtils.createMimeMap("application/rdf+xml", new String[] { "rdf", "", "", "" });
		MimeUtils.createMimeMap("application/remote-printing", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/riscos", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/rss+xml", new String[] { "rss", "", "", "" });
		MimeUtils.createMimeMap("application/rtf", new String[] { "rtf", "", "", "" });
		MimeUtils.createMimeMap("application/sdp", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/set-payment", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/set-payment-initiation", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/set-registration", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/set-registration-initiation", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/sgml", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/sgml-open-catalog", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/sieve", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/slate", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/smil", new String[] { "smi", "smil", "", "" });
		MimeUtils.createMimeMap("application/timestamp-query", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/timestamp-reply", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vemmi", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/whoispp-query", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/whoispp-response", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/wita", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/wordperfect", new String[] { "wpd", "", "", "" });
		MimeUtils.createMimeMap("application/wordperfect5.1", new String[] { "wp5", "", "", "" });
		MimeUtils.createMimeMap("application/x400-bp", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/xhtml+xml", new String[] { "xhtml", "xht", "", "" });
		MimeUtils.createMimeMap("application/xml", new String[] { "xml", "xsl", "", "" });
		MimeUtils.createMimeMap("application/xml-dtd", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/xml-external-parsed-entity", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/xspf+xml", new String[] { "xspf", "", "", "" });
		MimeUtils.createMimeMap("application/zip", new String[] { "zip", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.3M.Post-it-Notes", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.accpac.simply.aso", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.accpac.simply.imp", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.acucobol", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.aether.imp", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.anser-web-certificate-issue-initiation", new String[] { "",
				"", "", "" });
		MimeUtils.createMimeMap("application/vnd.anser-web-funds-transfer-initiation", new String[] { "", "",
				"", "" });
		MimeUtils.createMimeMap("application/vnd.audiograph", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.bmi", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.businessobjects", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.canon-cpdl", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.canon-lips", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.cinderella", new String[] { "cdy", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.claymore", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.commerce-battelle", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.commonspace", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.comsocaller", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.contact.cmsg", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.cosmocaller", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.ctc-posml", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.cups-postscript", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.cups-raster", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.cups-raw", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.cybank", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.dna", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.dpgraph", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.dxr", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.ecdis-update", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.ecowin.chart", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.ecowin.filerequest", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.ecowin.fileupdate", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.ecowin.series", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.ecowin.seriesrequest", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.ecowin.seriesupdate", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.enliven", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.epson.esf", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.epson.msf", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.epson.quickanime", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.epson.salt", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.epson.ssf", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.ericsson.quickcall", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.eudora.data", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.fdf", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.ffsns", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.flographit", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.framemaker", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.fsc.weblaunch", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.fujitsu.oasys", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.fujitsu.oasys2", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.fujitsu.oasys3", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.fujitsu.oasysgp", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.fujitsu.oasysprs", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.fujixerox.ddd", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.fujixerox.docuworks", new String[] { "", "", "", "" });
		MimeUtils
				.createMimeMap("application/vnd.fujixerox.docuworks.binder", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.fut-misnet", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.google-earth.kml+xml", new String[] { "kml", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.google-earth.kmz", new String[] { "kmz", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.grafeq", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.groove-account", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.groove-identity-message", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.groove-injector", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.groove-tool-message", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.groove-tool-template", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.groove-vcard", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.hhe.lesson-player", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.hp-HPGL", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.hp-PCL", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.hp-PCLXL", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.hp-hpid", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.hp-hps", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.httphone", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.hzn-3d-crossword", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.ibm.MiniPay", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.ibm.afplinedata", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.ibm.modcap", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.informix-visionary", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.intercon.formnet", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.intertrust.digibox", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.intertrust.nncp", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.intu.qbo", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.intu.qfx", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.irepository.package+xml", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.is-xpr", new String[] { "", "", "", "" });
		MimeUtils
				.createMimeMap("application/vnd.japannet-directory-service", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.japannet-jpnstore-wakeup", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.japannet-payment-wakeup", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.japannet-registration", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.japannet-registration-wakeup",
				new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.japannet-setstore-wakeup", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.japannet-verification", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.japannet-verification-wakeup",
				new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.koan", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.lotus-1-2-3", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.lotus-approach", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.lotus-freelance", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.lotus-notes", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.lotus-organizer", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.lotus-screencam", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.lotus-wordpro", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.mcd", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.mediastation.cdkey", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.meridian-slingshot", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.mif", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.minisoft-hp3000-save", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.mitsubishi.misty-guard.trustweb", new String[] { "", "", "",
				"" });
		MimeUtils.createMimeMap("application/vnd.mobius.daf", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.mobius.dis", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.mobius.msl", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.mobius.plc", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.mobius.txf", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.motorola.flexsuite", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.motorola.flexsuite.adsi", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.motorola.flexsuite.fis", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.motorola.flexsuite.gotap", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.motorola.flexsuite.kmr", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.motorola.flexsuite.ttc", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.motorola.flexsuite.wem", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.mozilla.xul+xml", new String[] { "xul", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.ms-artgalry", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.ms-asf", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.ms-excel", new String[] { "xls", "xlb", "xlt", "" });
		MimeUtils.createMimeMap("application/vnd.ms-lrm", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.ms-pki.seccat", new String[] { "cat", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.ms-pki.stl", new String[] { "stl", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.ms-powerpoint", new String[] { "ppt", "pps", "", "" });
		MimeUtils.createMimeMap("application/vnd.ms-project", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.ms-tnef", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.ms-works", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.mseq", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.msign", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.music-niff", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.musician", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.netfpx", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.noblenet-directory", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.noblenet-sealer", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.noblenet-web", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.novadigm.EDM", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.novadigm.EDX", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.novadigm.EXT", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.chart",
				new String[] { "odc", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.database", new String[] { "odb", "", "",
				"" });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.formula", new String[] { "odf", "", "",
				"" });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.graphics", new String[] { "odg", "", "",
				"" });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.graphics-template", new String[] { "otg",
				"", "", "" });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.image",
				new String[] { "odi", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.presentation", new String[] { "odp", "",
				"", "" });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.presentation-template", new String[] {
				"otp", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.spreadsheet", new String[] { "ods", "",
				"", "" });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.spreadsheet-template", new String[] {
				"ots", "", "", "" });
		MimeUtils
				.createMimeMap("application/vnd.oasis.opendocument.text", new String[] { "odt", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.text-master", new String[] { "odm", "",
				"", "" });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.text-template", new String[] { "ott", "",
				"", "" });
		MimeUtils.createMimeMap("application/vnd.oasis.opendocument.text-web", new String[] { "oth", "", "",
				"" });
		MimeUtils.createMimeMap("application/vnd.osa.netdeploy", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.palm", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.pg.format", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.pg.osasli", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.powerbuilder6", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.powerbuilder6-s", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.powerbuilder7", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.powerbuilder7-s", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.powerbuilder75", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.powerbuilder75-s", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.previewsystems.box", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.publishare-delta-tree", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.pvi.ptid1", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.pwg-xhtml-print+xml", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.rapid", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.rim.cod", new String[] { "cod", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.s3sms", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.seemail", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.shana.informed.formdata", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.shana.informed.formtemplate",
				new String[] { "", "", "", "" });
		MimeUtils
				.createMimeMap("application/vnd.shana.informed.interchange", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.shana.informed.package", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.smaf", new String[] { "mmf", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.sss-cod", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.sss-dtf", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.sss-ntf", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.stardivision.calc", new String[] { "sdc", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.stardivision.chart", new String[] { "sds", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.stardivision.draw", new String[] { "sda", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.stardivision.impress", new String[] { "sdd", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.stardivision.math", new String[] { "sdf", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.stardivision.writer", new String[] { "sdw", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.stardivision.writer-global", new String[] { "sgl", "", "",
				"" });
		MimeUtils.createMimeMap("application/vnd.street-stream", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.sun.xml.calc", new String[] { "sxc", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.sun.xml.calc.template", new String[] { "stc", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.sun.xml.draw", new String[] { "sxd", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.sun.xml.draw.template", new String[] { "std", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.sun.xml.impress", new String[] { "sxi", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.sun.xml.impress.template",
				new String[] { "sti", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.sun.xml.math", new String[] { "sxm", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.sun.xml.writer", new String[] { "sxw", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.sun.xml.writer.global", new String[] { "sxg", "", "", "" });
		MimeUtils
				.createMimeMap("application/vnd.sun.xml.writer.template", new String[] { "stw", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.svd", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.swiftview-ics", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.symbian.install", new String[] { "sis", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.triscape.mxs", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.trueapp", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.truedoc", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.tve-trigger", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.ufdl", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.uplanet.alert", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.uplanet.alert-wbxml", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.uplanet.bearer-choice", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.uplanet.bearer-choice-wbxml",
				new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.uplanet.cacheop", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.uplanet.cacheop-wbxml", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.uplanet.channel", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.uplanet.channel-wbxml", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.uplanet.list", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.uplanet.list-wbxml", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.uplanet.listcmd", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.uplanet.listcmd-wbxml", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.uplanet.signal", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.vcx", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.vectorworks", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.vidsoft.vidconference", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.visio", new String[] { "vsd", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.vividence.scriptfile", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.wap.sic", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.wap.slc", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.wap.wbxml", new String[] { "wbxml", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.wap.wmlc", new String[] { "wmlc", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.wap.wmlscriptc", new String[] { "wmlsc", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.webturbo", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.wrq-hp3000-labelled", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.wt.stf", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.xara", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.xfdl", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/vnd.yellowriver-custom-menu", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/x-123", new String[] { "wk", "", "", "" });
		MimeUtils.createMimeMap("application/x-7z-compressed", new String[] { "7z", "", "", "" });
		MimeUtils.createMimeMap("application/x-abiword", new String[] { "abw", "", "", "" });
		MimeUtils.createMimeMap("application/x-apple-diskimage", new String[] { "dmg", "", "", "" });
		MimeUtils.createMimeMap("application/x-bcpio", new String[] { "bcpio", "", "", "" });
		MimeUtils.createMimeMap("application/x-bittorrent", new String[] { "torrent", "", "", "" });
		MimeUtils.createMimeMap("application/x-cab", new String[] { "cab", "", "", "" });
		MimeUtils.createMimeMap("application/x-cbr", new String[] { "cbr", "", "", "" });
		MimeUtils.createMimeMap("application/x-cbz", new String[] { "cbz", "", "", "" });
		MimeUtils.createMimeMap("application/x-cdf", new String[] { "cdf", "", "", "" });
		MimeUtils.createMimeMap("application/x-cdlink", new String[] { "vcd", "", "", "" });
		MimeUtils.createMimeMap("application/x-chess-pgn", new String[] { "pgn", "", "", "" });
		MimeUtils.createMimeMap("application/x-core", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/x-cpio", new String[] { "cpio", "", "", "" });
		MimeUtils.createMimeMap("application/x-csh", new String[] { "csh", "", "", "" });
		MimeUtils.createMimeMap("application/x-debian-package", new String[] { "deb", "udeb", "", "" });
		MimeUtils.createMimeMap("application/x-director", new String[] { "dcr", "dir", "dxr", "" });
		MimeUtils.createMimeMap("application/x-dms", new String[] { "dms", "", "", "" });
		MimeUtils.createMimeMap("application/x-doom", new String[] { "wad", "", "", "" });
		MimeUtils.createMimeMap("application/x-dvi", new String[] { "dvi", "", "", "" });
		MimeUtils.createMimeMap("application/x-httpd-eruby", new String[] { "rhtml", "", "", "" });
		MimeUtils.createMimeMap("application/x-executable", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/x-flac", new String[] { "flac", "", "", "" });
		MimeUtils.createMimeMap("application/x-font", new String[] { "pfa", "pfb", "gsf", "pcf" });
		MimeUtils.createMimeMap("application/x-freemind", new String[] { "mm", "", "", "" });
		MimeUtils.createMimeMap("application/x-futuresplash", new String[] { "spl", "", "", "" });
		MimeUtils.createMimeMap("application/x-gnumeric", new String[] { "gnumeric", "", "", "" });
		MimeUtils.createMimeMap("application/x-go-sgf", new String[] { "sgf", "", "", "" });
		MimeUtils.createMimeMap("application/x-graphing-calculator", new String[] { "gcf", "", "", "" });
		MimeUtils.createMimeMap("application/x-gtar", new String[] { "gtar", "tgz", "taz", "" });
		MimeUtils.createMimeMap("application/x-hdf", new String[] { "hdf", "", "", "" });
		MimeUtils.createMimeMap("application/x-httpd-php", new String[] { "phtml", "pht", "php", "" });
		MimeUtils.createMimeMap("application/x-httpd-php-source", new String[] { "phps", "", "", "" });
		MimeUtils.createMimeMap("application/x-httpd-php3", new String[] { "php3", "", "", "" });
		MimeUtils
				.createMimeMap("application/x-httpd-php3-preprocessed", new String[] { "php3p", "", "", "" });
		MimeUtils.createMimeMap("application/x-httpd-php4", new String[] { "php4", "", "", "" });
		MimeUtils.createMimeMap("application/x-ica", new String[] { "ica", "", "", "" });
		MimeUtils.createMimeMap("application/x-internet-signup", new String[] { "ins", "isp", "", "" });
		MimeUtils.createMimeMap("application/x-iphone", new String[] { "iii", "", "", "" });
		MimeUtils.createMimeMap("application/x-iso9660-image", new String[] { "iso", "", "", "" });
		MimeUtils.createMimeMap("application/x-jam", new String[] { "jam", "", "", "" });
		MimeUtils.createMimeMap("application/x-java-applet", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/x-java-bean", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/x-java-jnlp-file", new String[] { "jnlp", "", "", "" });
		MimeUtils.createMimeMap("application/x-javascript", new String[] { "js", "", "", "" });
		MimeUtils.createMimeMap("application/x-jmol", new String[] { "jmz", "", "", "" });
		MimeUtils.createMimeMap("application/x-kchart", new String[] { "chrt", "", "", "" });
		MimeUtils.createMimeMap("application/x-kdelnk", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/x-killustrator", new String[] { "kil", "", "", "" });
		MimeUtils.createMimeMap("application/x-koan", new String[] { "skp", "skd", "skt", "skm" });
		MimeUtils.createMimeMap("application/x-kpresenter", new String[] { "kpr", "kpt", "", "" });
		MimeUtils.createMimeMap("application/x-kspread", new String[] { "ksp", "", "", "" });
		MimeUtils.createMimeMap("application/x-kword", new String[] { "kwd", "kwt", "", "" });
		MimeUtils.createMimeMap("application/x-latex", new String[] { "latex", "", "", "" });
		MimeUtils.createMimeMap("application/x-lha", new String[] { "lha", "", "", "" });
		MimeUtils.createMimeMap("application/x-lyx", new String[] { "lyx", "", "", "" });
		MimeUtils.createMimeMap("application/x-lzh", new String[] { "lzh", "", "", "" });
		MimeUtils.createMimeMap("application/x-lzx", new String[] { "lzx", "", "", "" });
		MimeUtils.createMimeMap("application/x-maker", new String[] { "frm", "maker", "frame", "fm" });
		MimeUtils.createMimeMap("application/x-mif", new String[] { "mif", "", "", "" });
		MimeUtils.createMimeMap("application/x-ms-wmd", new String[] { "wmd", "", "", "" });
		MimeUtils.createMimeMap("application/x-ms-wmz", new String[] { "wmz", "", "", "" });
		MimeUtils.createMimeMap("application/x-msdos-program", new String[] { "com", "exe", "bat", "dll" });
		MimeUtils.createMimeMap("application/x-msi", new String[] { "msi", "", "", "" });
		MimeUtils.createMimeMap("application/x-netcdf", new String[] { "nc", "", "", "" });
		MimeUtils.createMimeMap("application/x-ns-proxy-autoconfig", new String[] { "pac", "dat", "", "" });
		MimeUtils.createMimeMap("application/x-nwc", new String[] { "nwc", "", "", "" });
		MimeUtils.createMimeMap("application/x-object", new String[] { "o", "", "", "" });
		MimeUtils.createMimeMap("application/x-oz-application", new String[] { "oza", "", "", "" });
		MimeUtils.createMimeMap("application/x-pkcs7-certreqresp", new String[] { "p7r", "", "", "" });
		MimeUtils.createMimeMap("application/x-pkcs7-crl", new String[] { "crl", "", "", "" });
		MimeUtils.createMimeMap("application/x-python-code", new String[] { "pyc", "pyo", "", "" });
		MimeUtils.createMimeMap("application/x-quicktimeplayer", new String[] { "qtl", "", "", "" });
		MimeUtils.createMimeMap("application/x-redhat-package-manager", new String[] { "rpm", "", "", "" });
		MimeUtils.createMimeMap("application/x-ruby", new String[] { "rb", "", "", "" });
		MimeUtils.createMimeMap("application/x-rx", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/x-sh", new String[] { "sh", "", "", "" });
		MimeUtils.createMimeMap("application/x-shar", new String[] { "shar", "", "", "" });
		MimeUtils.createMimeMap("application/x-shellscript", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/x-shockwave-flash", new String[] { "swf", "swfl", "", "" });
		MimeUtils.createMimeMap("application/x-stuffit", new String[] { "sit", "sitx", "", "" });
		MimeUtils.createMimeMap("application/x-sv4cpio", new String[] { "sv4cpio", "", "", "" });
		MimeUtils.createMimeMap("application/x-sv4crc", new String[] { "sv4crc", "", "", "" });
		MimeUtils.createMimeMap("application/x-tar", new String[] { "tar", "", "", "" });
		MimeUtils.createMimeMap("application/x-tcl", new String[] { "tcl", "", "", "" });
		MimeUtils.createMimeMap("application/x-tex-gf", new String[] { "gf", "", "", "" });
		MimeUtils.createMimeMap("application/x-tex-pk", new String[] { "pk", "", "", "" });
		MimeUtils.createMimeMap("application/x-texinfo", new String[] { "texinfo", "texi", "", "" });
		MimeUtils.createMimeMap("application/x-trash", new String[] { "~", "%", "bak", "old" });
		MimeUtils.createMimeMap("application/x-troff", new String[] { "t", "tr", "roff", "" });
		MimeUtils.createMimeMap("application/x-troff-man", new String[] { "man", "", "", "" });
		MimeUtils.createMimeMap("application/x-troff-me", new String[] { "me", "", "", "" });
		MimeUtils.createMimeMap("application/x-troff-ms", new String[] { "ms", "", "", "" });
		MimeUtils.createMimeMap("application/x-ustar", new String[] { "ustar", "", "", "" });
		MimeUtils.createMimeMap("application/x-videolan", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("application/x-wais-source", new String[] { "src", "", "", "" });
		MimeUtils.createMimeMap("application/x-wingz", new String[] { "wz", "", "", "" });
		MimeUtils.createMimeMap("application/x-x509-ca-cert", new String[] { "crt", "", "", "" });
		MimeUtils.createMimeMap("application/x-xcf", new String[] { "xcf", "", "", "" });
		MimeUtils.createMimeMap("application/x-xfig", new String[] { "fig", "", "", "" });
		MimeUtils.createMimeMap("application/x-xpinstall", new String[] { "xpi", "", "", "" });
		MimeUtils.createMimeMap("audio/32kadpcm", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/3gpp", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/basic", new String[] { "au", "snd", "", "" });
		MimeUtils.createMimeMap("audio/g.722.1", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/l16", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/midi", new String[] { "mid", "midi", "kar", "" });
		MimeUtils.createMimeMap("audio/mp4a-latm", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/mpa-robust", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/mpeg", new String[] { "mpga", "mpega", "mp2", "mp3" });
		MimeUtils.createMimeMap("audio/mpegurl", new String[] { "m3u", "", "", "" });
		MimeUtils.createMimeMap("audio/parityfec", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/prs.sid", new String[] { "sid", "", "", "" });
		MimeUtils.createMimeMap("audio/telephone-event", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/tone", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/vnd.cisco.nse", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/vnd.cns.anp1", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/vnd.cns.inf1", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/vnd.digital-winds", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/vnd.everad.plj", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/vnd.lucent.voice", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/vnd.nortel.vbk", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/vnd.nuera.ecelp4800", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/vnd.nuera.ecelp7470", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/vnd.nuera.ecelp9600", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/vnd.octel.sbc", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/vnd.qcelp", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/vnd.rhetorex.32kadpcm", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/vnd.vmx.cvsd", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/x-aiff", new String[] { "aif", "aiff", "aifc", "" });
		MimeUtils.createMimeMap("audio/x-gsm", new String[] { "gsm", "", "", "" });
		MimeUtils.createMimeMap("audio/x-mpegurl", new String[] { "m3u", "", "", "" });
		MimeUtils.createMimeMap("audio/x-ms-wma", new String[] { "wma", "", "", "" });
		MimeUtils.createMimeMap("audio/x-ms-wax", new String[] { "wax", "", "", "" });
		MimeUtils.createMimeMap("audio/x-pn-realaudio-plugin", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("audio/x-pn-realaudio", new String[] { "ra", "rm", "ram", "" });
		MimeUtils.createMimeMap("audio/x-realaudio", new String[] { "ra", "", "", "" });
		MimeUtils.createMimeMap("audio/x-scpls", new String[] { "pls", "", "", "" });
		MimeUtils.createMimeMap("audio/x-sd2", new String[] { "sd2", "", "", "" });
		MimeUtils.createMimeMap("audio/x-wav", new String[] { "wav", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-alchemy", new String[] { "alc", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-cache", new String[] { "cac", "cache", "", "" });
		MimeUtils.createMimeMap("chemical/x-cache-csf", new String[] { "csf", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-cactvs-binary", new String[] { "cbin", "cascii", "ctab", "" });
		MimeUtils.createMimeMap("chemical/x-cdx", new String[] { "cdx", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-cerius", new String[] { "cer", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-chem3d", new String[] { "c3d", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-chemdraw", new String[] { "chm", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-cif", new String[] { "cif", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-cmdf", new String[] { "cmdf", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-cml", new String[] { "cml", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-compass", new String[] { "cpa", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-crossfire", new String[] { "bsd", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-csml", new String[] { "csml", "csm", "", "" });
		MimeUtils.createMimeMap("chemical/x-ctx", new String[] { "ctx", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-cxf", new String[] { "cxf", "cef", "", "" });
		MimeUtils.createMimeMap("chemical/x-embl-dl-nucleotide", new String[] { "emb", "embl", "", "" });
		MimeUtils.createMimeMap("chemical/x-galactic-spc", new String[] { "spc", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-gamess-input", new String[] { "inp", "gam", "gamin", "" });
		MimeUtils.createMimeMap("chemical/x-gaussian-checkpoint", new String[] { "fch", "fchk", "", "" });
		MimeUtils.createMimeMap("chemical/x-gaussian-cube", new String[] { "cub", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-gaussian-input", new String[] { "gau", "gjc", "gjf", "" });
		MimeUtils.createMimeMap("chemical/x-gaussian-log", new String[] { "gal", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-gcg8-sequence", new String[] { "gcg", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-genbank", new String[] { "gen", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-hin", new String[] { "hin", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-isostar", new String[] { "istr", "ist", "", "" });
		MimeUtils.createMimeMap("chemical/x-jcamp-dx", new String[] { "jdx", "dx", "", "" });
		MimeUtils.createMimeMap("chemical/x-kinemage", new String[] { "kin", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-macmolecule", new String[] { "mcm", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-macromodel-input", new String[] { "mmd", "mmod", "", "" });
		MimeUtils.createMimeMap("chemical/x-mdl-molfile", new String[] { "mol", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-mdl-rdfile", new String[] { "rd", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-mdl-rxnfile", new String[] { "rxn", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-mdl-sdfile", new String[] { "sd", "sdf", "", "" });
		MimeUtils.createMimeMap("chemical/x-mdl-tgf", new String[] { "tgf", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-mmcif", new String[] { "mcif", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-mol2", new String[] { "mol2", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-molconn-Z", new String[] { "b", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-mopac-graph", new String[] { "gpt", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-mopac-input", new String[] { "mop", "mopcrt", "mpc", "zmt" });
		MimeUtils.createMimeMap("chemical/x-mopac-out", new String[] { "moo", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-mopac-vib", new String[] { "mvb", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-ncbi-asn1", new String[] { "asn", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-ncbi-asn1-ascii", new String[] { "prt", "ent", "", "" });
		MimeUtils.createMimeMap("chemical/x-ncbi-asn1-binary", new String[] { "val", "aso", "", "" });
		MimeUtils.createMimeMap("chemical/x-ncbi-asn1-spec", new String[] { "asn", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-pdb", new String[] { "pdb", "ent", "", "" });
		MimeUtils.createMimeMap("chemical/x-rosdal", new String[] { "ros", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-swissprot", new String[] { "sw", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-vamas-iso14976", new String[] { "vms", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-vmd", new String[] { "vmd", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-xtel", new String[] { "xtel", "", "", "" });
		MimeUtils.createMimeMap("chemical/x-xyz", new String[] { "xyz", "", "", "" });
		MimeUtils.createMimeMap("image/cgm", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("image/g3fax", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("image/gif", new String[] { "gif", "", "", "" });
		MimeUtils.createMimeMap("image/ief", new String[] { "ief", "", "", "" });
		MimeUtils.createMimeMap("image/jpeg", new String[] { "jpeg", "jpg", "jpe", "" });
		MimeUtils.createMimeMap("image/naplps", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("image/pcx", new String[] { "pcx", "", "", "" });
		MimeUtils.createMimeMap("image/png", new String[] { "png", "", "", "" });
		MimeUtils.createMimeMap("image/prs.btif", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("image/prs.pti", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("image/svg+xml", new String[] { "svg", "svgz", "", "" });
		MimeUtils.createMimeMap("image/tiff", new String[] { "tiff", "tif", "", "" });
		MimeUtils.createMimeMap("image/vnd.cns.inf2", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("image/vnd.djvu", new String[] { "djvu", "djv", "", "" });
		MimeUtils.createMimeMap("image/vnd.dwg", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("image/vnd.dxf", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("image/vnd.fastbidsheet", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("image/vnd.fpx", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("image/vnd.fst", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("image/vnd.fujixerox.edmics-mmr", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("image/vnd.fujixerox.edmics-rlc", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("image/vnd.mix", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("image/vnd.net-fpx", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("image/vnd.svf", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("image/vnd.wap.wbmp", new String[] { "wbmp", "", "", "" });
		MimeUtils.createMimeMap("image/vnd.xiff", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("image/x-cmu-raster", new String[] { "ras", "", "", "" });
		MimeUtils.createMimeMap("image/x-coreldraw", new String[] { "cdr", "", "", "" });
		MimeUtils.createMimeMap("image/x-coreldrawpattern", new String[] { "pat", "", "", "" });
		MimeUtils.createMimeMap("image/x-coreldrawtemplate", new String[] { "cdt", "", "", "" });
		MimeUtils.createMimeMap("image/x-corelphotopaint", new String[] { "cpt", "", "", "" });
		MimeUtils.createMimeMap("image/x-icon", new String[] { "ico", "", "", "" });
		MimeUtils.createMimeMap("image/x-jg", new String[] { "art", "", "", "" });
		MimeUtils.createMimeMap("image/x-jng", new String[] { "jng", "", "", "" });
		MimeUtils.createMimeMap("image/x-ms-bmp", new String[] { "bmp", "", "", "" });
		MimeUtils.createMimeMap("image/x-photoshop", new String[] { "psd", "", "", "" });
		MimeUtils.createMimeMap("image/x-portable-anymap", new String[] { "pnm", "", "", "" });
		MimeUtils.createMimeMap("image/x-portable-bitmap", new String[] { "pbm", "", "", "" });
		MimeUtils.createMimeMap("image/x-portable-graymap", new String[] { "pgm", "", "", "" });
		MimeUtils.createMimeMap("image/x-portable-pixmap", new String[] { "ppm", "", "", "" });
		MimeUtils.createMimeMap("image/x-rgb", new String[] { "rgb", "", "", "" });
		MimeUtils.createMimeMap("image/x-xbitmap", new String[] { "xbm", "", "", "" });
		MimeUtils.createMimeMap("image/x-xpixmap", new String[] { "xpm", "", "", "" });
		MimeUtils.createMimeMap("image/x-xwindowdump", new String[] { "xwd", "", "", "" });
		MimeUtils.createMimeMap("inode/chardevice", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("inode/blockdevice", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("inode/directory-locked", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("inode/directory", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("inode/fifo", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("inode/socket", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("message/delivery-status", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("message/disposition-notification", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("message/external-body", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("message/http", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("message/s-http", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("message/news", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("message/partial", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("message/rfc822", new String[] { "eml", "", "", "" });
		MimeUtils.createMimeMap("model/iges", new String[] { "igs", "iges", "", "" });
		MimeUtils.createMimeMap("model/mesh", new String[] { "msh", "mesh", "silo", "" });
		MimeUtils.createMimeMap("model/vnd.dwf", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("model/vnd.flatland.3dml", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("model/vnd.gdl", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("model/vnd.gs-gdl", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("model/vnd.gtw", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("model/vnd.mts", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("model/vnd.vtu", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("model/vrml", new String[] { "wrl", "vrml", "", "" });
		MimeUtils.createMimeMap("multipart/alternative", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("multipart/appledouble", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("multipart/byteranges", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("multipart/digest", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("multipart/encrypted", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("multipart/form-data", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("multipart/header-set", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("multipart/mixed", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("multipart/parallel", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("multipart/related", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("multipart/report", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("multipart/signed", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("multipart/voice-message", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/calendar", new String[] { "ics", "icz", "", "" });
		MimeUtils.createMimeMap("text/css", new String[] { "css", "", "", "" });
		MimeUtils.createMimeMap("text/csv", new String[] { "csv", "", "", "" });
		MimeUtils.createMimeMap("text/directory", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/english", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/enriched", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/h323", new String[] { "323", "", "", "" });
		MimeUtils.createMimeMap("text/html", new String[] { "html", "htm", "shtml", "" });
		MimeUtils.createMimeMap("text/iuls", new String[] { "uls", "", "", "" });
		MimeUtils.createMimeMap("text/mathml", new String[] { "mml", "", "", "" });
		MimeUtils.createMimeMap("text/parityfec", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/plain", new String[] { "asc", "txt", "text", "pot" });
		MimeUtils.createMimeMap("text/prs.lines.tag", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/rfc822-headers", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/richtext", new String[] { "rtx", "", "", "" });
		MimeUtils.createMimeMap("text/rtf", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/scriptlet", new String[] { "sct", "wsc", "", "" });
		MimeUtils.createMimeMap("text/t140", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/texmacs", new String[] { "tm", "ts", "", "" });
		MimeUtils.createMimeMap("text/tab-separated-values", new String[] { "tsv", "", "", "" });
		MimeUtils.createMimeMap("text/uri-list", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/vnd.abc", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/vnd.curl", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/vnd.DMClientScript", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/vnd.flatland.3dml", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/vnd.fly", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/vnd.fmi.flexstor", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/vnd.in3d.3dml", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/vnd.in3d.spot", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/vnd.IPTC.NewsML", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/vnd.IPTC.NITF", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/vnd.latex-z", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/vnd.motorola.reflex", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/vnd.ms-mediapackage", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/vnd.sun.j2me.app-descriptor", new String[] { "jad", "", "", "" });
		MimeUtils.createMimeMap("text/vnd.wap.si", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/vnd.wap.sl", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/vnd.wap.wml", new String[] { "wml", "", "", "" });
		MimeUtils.createMimeMap("text/vnd.wap.wmlscript", new String[] { "wmls", "", "", "" });
		MimeUtils.createMimeMap("text/x-bibtex", new String[] { "bib", "", "", "" });
		MimeUtils.createMimeMap("text/x-boo", new String[] { "boo", "", "", "" });
		MimeUtils.createMimeMap("text/x-c++hdr", new String[] { "h++", "hpp", "hxx", "hh" });
		MimeUtils.createMimeMap("text/x-c++src", new String[] { "c++", "cpp", "cxx", "cc" });
		MimeUtils.createMimeMap("text/x-chdr", new String[] { "h", "", "", "" });
		MimeUtils.createMimeMap("text/x-component", new String[] { "htc", "", "", "" });
		MimeUtils.createMimeMap("text/x-crontab", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/x-csh", new String[] { "csh", "", "", "" });
		MimeUtils.createMimeMap("text/x-csrc", new String[] { "c", "", "", "" });
		MimeUtils.createMimeMap("text/x-dsrc", new String[] { "d", "", "", "" });
		MimeUtils.createMimeMap("text/x-diff", new String[] { "diff", "patch", "", "" });
		MimeUtils.createMimeMap("text/x-haskell", new String[] { "hs", "", "", "" });
		MimeUtils.createMimeMap("text/x-java", new String[] { "java", "", "", "" });
		MimeUtils.createMimeMap("text/x-literate-haskell", new String[] { "lhs", "", "", "" });
		MimeUtils.createMimeMap("text/x-makefile", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/x-moc", new String[] { "moc", "", "", "" });
		MimeUtils.createMimeMap("text/x-pascal", new String[] { "p", "pas", "", "" });
		MimeUtils.createMimeMap("text/x-pcs-gcd", new String[] { "gcd", "", "", "" });
		MimeUtils.createMimeMap("text/x-perl", new String[] { "pl", "pm", "", "" });
		MimeUtils.createMimeMap("text/x-python", new String[] { "py", "", "", "" });
		MimeUtils.createMimeMap("text/x-server-parsed-html", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("text/x-setext", new String[] { "etx", "", "", "" });
		MimeUtils.createMimeMap("text/x-sh", new String[] { "sh", "", "", "" });
		MimeUtils.createMimeMap("text/x-tcl", new String[] { "tcl", "tk", "", "" });
		MimeUtils.createMimeMap("text/x-tex", new String[] { "tex", "ltx", "sty", "cls" });
		MimeUtils.createMimeMap("text/x-vcalendar", new String[] { "vcs", "", "", "" });
		MimeUtils.createMimeMap("text/x-vcard", new String[] { "vcf", "", "", "" });
		MimeUtils.createMimeMap("video/3gpp", new String[] { "3gp", "", "", "" });
		MimeUtils.createMimeMap("video/dl", new String[] { "dl", "", "", "" });
		MimeUtils.createMimeMap("video/dv", new String[] { "dif", "dv", "", "" });
		MimeUtils.createMimeMap("video/fli", new String[] { "fli", "", "", "" });
		MimeUtils.createMimeMap("video/gl", new String[] { "gl", "", "", "" });
		MimeUtils.createMimeMap("video/mpeg", new String[] { "mpeg", "mpg", "mpe", "" });
		MimeUtils.createMimeMap("video/mp4", new String[] { "mp4", "", "", "" });
		MimeUtils.createMimeMap("video/quicktime", new String[] { "qt", "mov", "", "" });
		MimeUtils.createMimeMap("video/mp4v-es", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("video/parityfec", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("video/pointer", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("video/vnd.fvt", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("video/vnd.motorola.video", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("video/vnd.motorola.videop", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("video/vnd.mpegurl", new String[] { "mxu", "", "", "" });
		MimeUtils.createMimeMap("video/vnd.mts", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("video/vnd.nokia.interleaved-multimedia", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("video/vnd.vivo", new String[] { "", "", "", "" });
		MimeUtils.createMimeMap("video/x-flv", new String[] { "flv", "", "", "" });
		MimeUtils.createMimeMap("video/x-la-asf", new String[] { "lsf", "lsx", "", "" });
		MimeUtils.createMimeMap("video/x-mng", new String[] { "mng", "", "", "" });
		MimeUtils.createMimeMap("video/x-ms-asf", new String[] { "asf", "asx", "", "" });
		MimeUtils.createMimeMap("video/x-ms-wm", new String[] { "wm", "", "", "" });
		MimeUtils.createMimeMap("video/x-ms-wmv", new String[] { "wmv", "", "", "" });
		MimeUtils.createMimeMap("video/x-ms-wmx", new String[] { "wmx", "", "", "" });
		MimeUtils.createMimeMap("video/x-ms-wvx", new String[] { "wvx", "", "", "" });
		MimeUtils.createMimeMap("video/x-msvideo", new String[] { "avi", "", "", "" });
		MimeUtils.createMimeMap("video/x-sgi-movie", new String[] { "movie", "", "", "" });
		MimeUtils.createMimeMap("x-conference/x-cooltalk", new String[] { "ice", "", "", "" });
		MimeUtils.createMimeMap("x-epoc/x-sisx-app", new String[] { "sisx", "", "", "" });
		MimeUtils.createMimeMap("x-world/x-vrml", new String[] { "vrm", "vrml", "wrl", "" });
	}

}
