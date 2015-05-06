package com.noisyle.springdemo.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChatController {
	@RequestMapping("/chat")
	public String index(Model model) {
		return "chat/index";
	}
	
	@RequestMapping("/chat/test")
	public String text(Model model) {
		return "chat/test";
	}
}
