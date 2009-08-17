package net.woodstock.rockframework.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.net.mail.SimpleMail;
import net.woodstock.rockframework.net.mail.SimpleMailSender;

public class MailTest extends TestCase {

	public void test1() throws Exception {
		SimpleMail mail = new SimpleMail();
		mail.setFrom("helio.costa@mc.gov.br");
		mail.addTo("wesley.neves@mc.gov.br");
		// mail.addTo("wesleyneves81@gmail.com");
		mail.setSubject("Problema");
		mail.setText("VC esta com problema!!!");

		SimpleMailSender mailSender = new SimpleMailSender("10.209.64.33");
		mailSender.setDebug(true);
		// mailSender.setUser("sistema.gesac");
		// mailSender.setPassword("gesac123");

		mailSender.send(mail);
	}

}
