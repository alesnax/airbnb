package com.epam.apartment.service.impl;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.epam.apartment.service.MailService;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	JavaMailSender mailSender;

	private MimeMessagePreparator getMessagePreparator(String subject, String body, String email) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
				helper.setSubject(subject);
				helper.setFrom("a.nakhankou@gmail.com");
				helper.setTo(email);

				// use the true flag to indicate you need a multipart message
				helper.setText(body, true);

				// Additionally, let's add a resource as an attachment as well.
				// helper.addAttachment("no_avater.jpg", new
				// ClassPathResource("linux-icon.png"));
			}
		};
		return preparator;

	}

	@Override
	public void sendEmail(String subject, String body, String email) {
		MimeMessagePreparator preparator = getMessagePreparator(subject, body, email);
		mailSender.send(preparator);
	}

}
