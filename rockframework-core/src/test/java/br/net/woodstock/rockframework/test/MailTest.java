package br.net.woodstock.rockframework.test;

import br.net.woodstock.rockframework.net.mail.RunnableMailSender;
import br.net.woodstock.rockframework.net.mail.SimpleMail;
import junit.framework.TestCase;

public class MailTest extends TestCase {

	public void test1() throws Exception {
		SimpleMail mail = new SimpleMail();
		mail.setFrom("SCE <sissce@mc.gov.br>");
		mail.addTo("ls_junior@uol.com.br");
		mail.setSubject("Teste");
		mail.setText("Teste");

		RunnableMailSender mailSender = new RunnableMailSender("10.209.64.105");
		mailSender.setDebug(false);
		mailSender.setUser("xxxxx");
		mailSender.setPassword("xxxxx");

		mailSender.send(mail);
	}

}
