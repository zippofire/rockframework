package net.woodstock.rockframework.test.net;

import java.io.FileInputStream;

import junit.framework.TestCase;
import net.woodstock.rockframework.net.mail.ByteArrayAttachment;
import net.woodstock.rockframework.net.mail.InputStreamAttachment;
import net.woodstock.rockframework.net.mail.SimpleMail;
import net.woodstock.rockframework.net.mail.SimpleMailSender;

public class MailTest extends TestCase {

	public void xtest1() throws Exception {
		SimpleMail mail = new SimpleMail();
		mail.setFrom("SCE <sissce@mc.gov.br>");
		mail.addTo("lourival.junior@mc.gov.br");
		mail.setSubject("Teste");
		mail.setText("Teste");

		FileInputStream inputStream = new FileInputStream("C:/Temp/split.pdf");

		byte[] bytes = new byte[inputStream.available()];
		inputStream.read(bytes);

		inputStream.close();

		mail.addAttach(new ByteArrayAttachment("split.pdf", "application/pdf", bytes));

		SimpleMailSender mailSender = new SimpleMailSender("10.209.64.105");
		mailSender.setDebug(false);

		mailSender.send(mail);
	}

	public void test2() throws Exception {
		SimpleMail mail = new SimpleMail();
		mail.setFrom("SCE <sissce@mc.gov.br>");
		mail.addTo("lourival.junior@mc.gov.br");
		mail.setSubject("Teste");
		mail.setText("Teste");

		FileInputStream inputStream = new FileInputStream("C:/Temp/rodape_sspo.jpg");

		mail.addAttach(new InputStreamAttachment("rodape_sspo.jpg", "image/jpeg", inputStream));

		SimpleMailSender mailSender = new SimpleMailSender("10.209.64.105");
		mailSender.setDebug(false);

		mailSender.send(mail);
		inputStream.close();
	}
}
