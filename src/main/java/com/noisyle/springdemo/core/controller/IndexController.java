package com.noisyle.springdemo.core.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
	@RequestMapping("/")
	public String index(Model model) {
		return "index";
	}
	
	@RequestMapping("/hello")
	public @ResponseBody HashMap<String, Object> hello(Model model) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("a", "呵呵");
		map.put("b", 2);
		return map;
	}
}
