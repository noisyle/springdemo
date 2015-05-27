package com.noisyle.springdemo.tanks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TanksController {
	@RequestMapping("/tanks")
	public String index(Model model) {
		return "tanks/index";
	}
	
}
