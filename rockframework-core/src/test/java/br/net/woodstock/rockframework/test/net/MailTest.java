package br.net.woodstock.rockframework.test.net;

import java.io.FileInputStream;

import br.net.woodstock.rockframework.net.mail.ByteArrayAttachment;
import br.net.woodstock.rockframework.net.mail.Disposition;
import br.net.woodstock.rockframework.net.mail.InputStreamAttachment;
import br.net.woodstock.rockframework.net.mail.RunnableMailSender;
import br.net.woodstock.rockframework.net.mail.SimpleMail;
import br.net.woodstock.rockframework.net.mail.SimpleMailSender;

import junit.framework.TestCase;

public class MailTest extends TestCase {

	public void test1() throws Exception {
		SimpleMail mail = new SimpleMail();
		mail.setFrom("SCE <sissce@mc.gov.br>");
		mail.addTo("lourival.junior@mc.gov.br");
		mail.setHtml(true);
		mail.setSubject("Teste Runnable");
		mail.setText("<html><body>Foto do sarney</body></html>");

		FileInputStream inputStream = new FileInputStream("C:/Temp/split.pdf");

		byte[] bytes = new byte[inputStream.available()];
		inputStream.read(bytes);

		inputStream.close();

		mail.addAttach(new ByteArrayAttachment("split.pdf", "application/pdf", bytes));

		RunnableMailSender mailSender = new RunnableMailSender("10.209.64.105");
		mailSender.setDebug(false);

		mailSender.send(mail);
	}

	public void test11() throws Exception {
		SimpleMail mail = new SimpleMail();
		mail.setFrom("SCE <sissce@mc.gov.br>");
		mail.addTo("lourival.junior@mc.gov.br");
		mail.setHtml(true);
		mail.setSubject("Teste Simple");
		mail.setText("<html><body>Foto do sarney</body></html>");

		FileInputStream inputStream = new FileInputStream("C:/Temp/split.pdf");

		byte[] bytes = new byte[inputStream.available()];
		inputStream.read(bytes);

		inputStream.close();

		mail.addAttach(new ByteArrayAttachment("split.pdf", "application/pdf", bytes));

		SimpleMailSender mailSender = new SimpleMailSender("10.209.64.105");
		mailSender.setDebug(false);

		mailSender.send(mail);
	}

	public void xtest2() throws Exception {
		SimpleMail mail = new SimpleMail();
		mail.setFrom("SCE <sissce@mc.gov.br>");
		mail.addTo("lourival.junior@mc.gov.br");
		mail.setHtml(true);
		mail.setSubject("Teste");
		mail.setText("<html><body>Foto do sarney: <img src=\"cid:sarney.jpg\" /></body></html>");

		FileInputStream inputStream = new FileInputStream("C:/Temp/sarney.jpg");

		mail.addAttach(new InputStreamAttachment("sarney.jpg", "image/jpeg", inputStream, Disposition.INLINE));

		inputStream = new FileInputStream("C:/Temp/split.pdf");

		byte[] bytes = new byte[inputStream.available()];
		inputStream.read(bytes);

		inputStream.close();

		mail.addAttach(new ByteArrayAttachment("split.pdf", "application/pdf", bytes));

		RunnableMailSender mailSender = new RunnableMailSender("10.209.64.105");
		mailSender.setDebug(false);

		mailSender.send(mail);
		inputStream.close();
	}
}
