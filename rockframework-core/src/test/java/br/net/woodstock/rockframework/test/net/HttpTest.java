package br.net.woodstock.rockframework.test.net;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import br.net.woodstock.rockframework.net.http.HttpClient;

import junit.framework.TestCase;

public class HttpTest extends TestCase {

	public void xtestGet() throws Exception {
		HttpClient client = new HttpClient();
		byte[] bytes = client.doGet("http://woodstock.no-ip.info/util-php/pesquisar-cep.php?cep=72152020");
		System.out.println(new String(bytes, Charset.forName("UTF-8")));
	}

	public void testPost() throws Exception {
		HttpClient client = new HttpClient();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param1", "value1");
		params.put("param2", "value2");
		params.put("param3", "value3");
		params.put("param4", "value4");
		params.put("param5", "value5");
		byte[] bytes = client.doPost("http://woodstock.no-ip.info/util-php/post-test.php?x=1", params);
		System.out.println(new String(bytes, Charset.forName("UTF-8")));
	}

}
