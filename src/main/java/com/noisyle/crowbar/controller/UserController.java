package com.noisyle.crowbar.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.noisyle.crowbar.core.base.AbstractController;
import com.noisyle.crowbar.model.User;
import com.noisyle.crowbar.service.UserService;

@Controller
@RequestMapping("/admin/user")
public class UserController extends AbstractController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String index() {
		return "admin/user/userlist";
	}

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public @ResponseBody Object list(@RequestParam Map<String, String> params) {
		Map<String, Object> map = new HashMap<>();
		List<User> list = userService.list();
		map.put("data", list);
		map.put("recordsTotal", list.size());
		map.put("recordsFiltered", list.size());
		map.put("draw", 0);
		return map;
	}

}
