package com.noisyle.springdemo.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.noisyle.springdemo.admin.entity.User;
import com.noisyle.springdemo.admin.service.AdminService;

@Controller
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@RequestMapping("/admin")
	public String index(Model model) {
		User user = adminService.getUser();
		model.addAttribute("user", user);
		return "admin/index";
	}
}
