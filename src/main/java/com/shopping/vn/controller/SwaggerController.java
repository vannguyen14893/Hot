package com.shopping.vn.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@ApiIgnore // @Api(hidden = true) not gonna work
@Controller
@RequestMapping("/swagger")
public class SwaggerController {
	
	
	@GetMapping
	public String swagger() {
		return "redirect:/swagger-ui.html";
	}

	@MessageMapping("/socket")
	@SendTo("/swagger/greetings")
	public String greeting() {
		return "Hello world";
	}

}
