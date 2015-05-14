package com.noisyle.springdemo.chat.controller;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChatController {
	@RequestMapping("/chat")
	public String index(HttpServletRequest request, Model model) {
		String username = (String) request.getSession().getAttribute("username");
		if(username==null){
			Random r = new Random(new Date().getTime());
			DecimalFormat df = new DecimalFormat("000000");
			username = "匿名用户"+df.format(r.nextInt(999999));
			request.getSession().setAttribute("username", username);
		}
		model.addAttribute("name", username); 
		return "chat/index";
	}
	
	@RequestMapping("/chat/test")
	public String text(Model model) {
		return "chat/test";
	}
}
