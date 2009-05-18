package net.woodstock.rockframework.test;

import net.woodstock.rockframework.net.mail.SimpleMail;
import net.woodstock.rockframework.net.mail.SimpleMailSender;

public class MailTest {

	protected static void test1() throws Exception {
		SimpleMail mail = new SimpleMail();
		mail.setFrom("lourival.junior@mc.intranet");
		mail.addTo("lourival.junior@mc.intranet");
		mail.setSubject("Teste");
		mail.setText("Teste");

		SimpleMailSender mailSender = new SimpleMailSender("10.209.64.81");
		mailSender.setDebug(true);
		//mailSender.setUser("");
		//mailSender.setPassword("");

		mailSender.send(mail);
	}

	public static void main(String[] args) {
		try {
			test1();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
