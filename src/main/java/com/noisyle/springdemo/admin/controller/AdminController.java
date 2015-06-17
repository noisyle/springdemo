package com.noisyle.springdemo.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.noisyle.springdemo.admin.entity.User;
import com.noisyle.springdemo.admin.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@RequestMapping("")
	public String index(Model model) {
		User user = adminService.getUser();
		model.addAttribute("user", user);
		return "admin/index";
	}
	
	@RequestMapping("/init")
	public @ResponseBody String init(Model model) {
		adminService.init();
		return "初始化成功";
	}
}
