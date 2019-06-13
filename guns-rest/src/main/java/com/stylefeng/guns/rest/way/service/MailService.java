package com.stylefeng.guns.rest.way.service;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * 邮件发送Service
 * @author shengquan
 */
@Service
public class MailService {
	private static final Logger logger = LoggerFactory.getLogger(MailService.class);

	@Autowired
	private JavaMailSender mailSender;
		/**
	    * @Description: 发送html邮件
	    */
	   public void sendHtmlMail(String fromMailAddress, String targetMailAddress,
				String mailContent){
		   MimeMessage message = null;
		    try {
		        message = mailSender.createMimeMessage();
		        MimeMessageHelper helper = new MimeMessageHelper(message, true);
		        helper.setFrom(fromMailAddress);
		        helper.setTo(targetMailAddress);
		        helper.setSubject("【互啊佑WhoAreYou】验证码 Verification Code"); // 邮件主题
		        // 发送htmltext值需要给个true，不然不生效
		        helper.setText(mailContent, true);
				mailSender.send(message);
		    } catch (Exception e) {
		    	logger.error("发送简单文本邮件异常！",e.getMessage());
		    }
	   }
}
