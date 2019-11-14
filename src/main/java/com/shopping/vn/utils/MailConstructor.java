package com.shopping.vn.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.shopping.vn.dto.UserDto;


@Component
public class MailConstructor {
	
	@Autowired
	private Environment env;
	
//	@Autowired
//	private TemplateEngine templateEngine;
	
	public SimpleMailMessage constructNewUserEmail(UserDto user, String password) {
		String message="\nPlease use the following credentials to log in and edit your personal information including your own password."
				+ "\nEmail:"+user.getEmail()+"\nPassword:"+password;
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("ndvan Shopping - New User");
		email.setText(message);
		email.setFrom(env.getProperty("support.email"));
		return email;
	}
	
//	public MimeMessagePreparator constructOrderConfirmationEmail (User user, Order order, Locale locale) {
//		Context context = new Context();
//		context.setVariable("order", order);
//		context.setVariable("user", user);
//		context.setVariable("cartItemList", order.getCartItemList());
//		String text = templateEngine.process("orderConfirmationEmailTemplate", context);
//		
//		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
//			@Override
//			public void prepare(MimeMessage mimeMessage) throws Exception {
//				MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
//				email.setTo(user.getEmail());
//				email.setSubject("Order Confirmation - "+order.getId());
//				email.setText(text,true);
//				email.setFrom(new InternetAddress("ray.deng83@gmail.com"));
//			}
//		};
//		
//		return messagePreparator;
//	}

}
