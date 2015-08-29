package com.noisyle.crowbar.controller;

import com.noisyle.crowbar.core.base.AbstractController;
import com.noisyle.crowbar.core.vo.ResponseData;
import com.noisyle.crowbar.model.Activity;
import com.noisyle.crowbar.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class ActivityController extends AbstractController {
    @Autowired
    private ActivityService activityService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        return "activity/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Object list(HttpServletRequest request) {
        return ResponseData.buildSuccessResponse(activityService.list());
    }

    @RequestMapping(value = "/activity", method = RequestMethod.POST)
    public @ResponseBody Object save(HttpServletRequest request, @ModelAttribute Activity activity) {
        activity.setSponsorId(1L);
        activity.setStatus(1);
        activityService.save(activity);
        return ResponseData.buildSuccessResponse(activity);
    }

    @RequestMapping(value = "/activity", method = RequestMethod.GET)
    public @ResponseBody Object get(HttpServletRequest request, @RequestParam("id") Long id) {
        Activity activity = activityService.get(id);
        return ResponseData.buildSuccessResponse(activity);
    }

    @RequestMapping(value = "/activity", method = RequestMethod.DELETE)
    public @ResponseBody Object delete(HttpServletRequest request, @RequestParam("id") Long id) {
        activityService.delete(id);
        return ResponseData.buildSuccessResponse("删除成功！");
    }
}
