package com.noisyle.springdemo.admin.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.noisyle.springdemo.admin.entity.User;
import com.noisyle.springdemo.admin.service.AdminService;
import com.noisyle.springdemo.common.exception.ControllerException;
import com.noisyle.springdemo.common.web.AbstractController;

@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractController {
	@Autowired
	private AdminService adminService;
	
	@RequestMapping("")
	public String index(Model model) {
		User user = adminService.getUser();
		model.addAttribute("user", user);
		return "admin/index";
	}
	
	@RequestMapping("/init.json")
	public void init(HttpServletResponse response) {
		try {
			adminService.init();
			responseMessage.setInfoMessage("初始化成功");
			response.getWriter().write(getJsonString(responseMessage));
		} catch (Exception e) {
			throw new ControllerException("初始化失败", e, responseMessage);
		}
	}
}
