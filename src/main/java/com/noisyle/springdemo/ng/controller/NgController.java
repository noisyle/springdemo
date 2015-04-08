package com.noisyle.springdemo.ng.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NgController {
	@RequestMapping("/ng")
	public String index(Model model) {
		return "ng/test";
	}
	
	@RequestMapping("/ng/list")
	public @ResponseBody List hello(Model model) {
		List list = new LinkedList();
		HashMap map = null;
		
		map = new HashMap();
		map.put("title", "asdf");
		map.put("content", "aszxcvdf");
		list.add(map);
		
		map = new HashMap();
		map.put("title", "wert");
		map.put("content", "xcvbes");
		list.add(map);
		
		map = new HashMap();
		map.put("title", "hugm");
		map.put("content", "xfvbrt");
		list.add(map);
		
		map = new HashMap();
		map.put("title", "qzaa");
		map.put("content", "bnytyt");
		list.add(map);
		
		return list;
	}
}
