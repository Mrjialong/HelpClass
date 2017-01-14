package classhelp.guofeng.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {

	private Email email;

	public SendEmail(Email email) {
		this.email = email;
	}

	// �����ʼ�
	public boolean sendMail() {
		Properties prop;
		Session session;
		MimeMessage msg;

		try {
			prop = new Properties(); // �洢���Ӳ���
			prop.put("mail.smtp.host", email.getHost());
			prop.put("mail.smtp.auth", "true");

			session = Session.getDefaultInstance(prop, null); // ���һ���ʼ���Session
			msg = new MimeMessage(session); // �ʼ���Ϣ

			// ����ʼ���ַ�Ƿ�Ϸ�
			if (email.getFromAddr() == null || email.getFromAddr().equals("")) {
				throw new Exception("������ַ����");
			}

			if (email.getAddresses() == null) {
				throw new Exception("Ŀ���ַ����");
			}

			InternetAddress[] sendTo = new InternetAddress[email.getAddresses().size()];
			for (int i = 0; i < email.getAddresses().size(); i++) {
				System.out.println("���͵�:" + email.getAddresses().get(i));
				sendTo[i] = new InternetAddress(email.getAddresses().get(i));
			}

			// ����Դ��ַ
			msg.setFrom(new InternetAddress(email.getFromAddr()));
			// ����Ŀ�ĵ�ַ
			// msg.setRecipient(Message.RecipientType.TO, new
			// InternetAddress(toAddr));
			msg.setRecipients(Message.RecipientType.TO, sendTo);
			// ��������
			msg.setSubject(email.getSubject(), "UTF-8");

			Multipart mp = new MimeMultipart(); // �ʼ�����
			MimeBodyPart mbpContent = new MimeBodyPart();
			mbpContent
					.setContent(email.getContent(), "text/html;charset=UTF-8"); // �ʼ���ʽ

			mp.addBodyPart(mbpContent);
			msg.setContent(mp);
			msg.setSentDate(new Date());

			// �����ʼ�
			Transport transport = session.getTransport("smtp");
			transport.connect((String) prop.get("mail.smtp.host"),
					email.getUsername(), email.getPassword());
			transport.sendMessage(msg,
					msg.getRecipients(MimeMessage.RecipientType.TO));

			transport.close();
			return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}
}