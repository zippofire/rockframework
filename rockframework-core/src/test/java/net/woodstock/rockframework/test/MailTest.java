package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.net.mail.SimpleMail;
import net.woodstock.rockframework.net.mail.SimpleMailSender;

public class MailTest extends TestCase {

	public void test1() throws Exception {
		SimpleMail mail = new SimpleMail();
		mail.setFrom("SisOuvidoria <ouvidoria@mc.gov.br>");
		mail.addTo("ls_junior@uol.com.br");
		mail.setSubject("Teste");
		mail.setText("Teste");

		SimpleMailSender mailSender = new SimpleMailSender("10.209.64.105");
		mailSender.setDebug(false);
		mailSender.setUser("ouvidoria");
		mailSender.setPassword("12345678");

		mailSender.send(mail);
	}

}
