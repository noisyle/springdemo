package com.noisyle.crowbar.controller;

import com.noisyle.crowbar.core.base.AbstractController;
import com.noisyle.crowbar.core.exception.GeneralException;
import com.noisyle.crowbar.core.vo.ResponseData;
import com.noisyle.crowbar.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractController {
    final static String KEY_OF_USER_CONTEXT = "_USER_CONTEXT_";

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, HttpSession session) {
        User user = (User) session.getAttribute(KEY_OF_USER_CONTEXT);
        if (user != null) {
            model.addAttribute("user", user);
            return "admin/index";
        } else {
            return "forward:/admin/login";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, HttpSession session) {
        User user = (User) session.getAttribute(KEY_OF_USER_CONTEXT);
        if (user != null) {
            model.addAttribute("user", user);
            return "redirect:/admin";
        } else {
            return "admin/login";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object doLogin(@ModelAttribute(value = "user") User user, HttpSession session) {
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(user.getLoginName(), user.getPassword()));
            session.setAttribute(KEY_OF_USER_CONTEXT, user);
            ResponseData r = ResponseData.buildSuccessResponse(user);
            return r;
        } catch (UnknownAccountException e) {
            throw new GeneralException("login.wrongUsername", user.getLoginName());
        } catch (IncorrectCredentialsException e) {
            throw new GeneralException("login.wrongPassword");
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute(KEY_OF_USER_CONTEXT);
        SecurityUtils.getSubject().logout();
        return "redirect:/admin";
    }
}
