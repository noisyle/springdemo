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
		String str = "Wake up,Brush teeth,Shower,Have breakfast,Go to work,Write a Nettuts article,Go to the gym,Meet friends,Go to bed";
		List list = new LinkedList();
		for(String name:str.split(",")){
			HashMap map = new HashMap();
			map.put("name", name);
			list.add(map);
		}
		return list;
	}
}
