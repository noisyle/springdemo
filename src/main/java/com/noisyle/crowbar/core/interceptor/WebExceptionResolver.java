package com.noisyle.crowbar.core.interceptor;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.noisyle.crowbar.core.exception.GeneralException;
import com.noisyle.crowbar.core.util.SpringContextHolder;
import com.noisyle.crowbar.core.vo.ResponseData;

public class WebExceptionResolver implements HandlerExceptionResolver {

    final private static Logger logger = LoggerFactory.getLogger(WebExceptionResolver.class);

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object, Exception ex) {
        ModelAndView model = new ModelAndView();
        String uri = request.getRequestURI();
        logger.error("Controller中拦截到异常\n--method:\"{}\"\n--url:\"{}\"", object, uri, ex);
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            String message;
            if (ex instanceof GeneralException) {
                GeneralException gex = (GeneralException) ex;
                Locale locale = ((LocaleContextResolver) SpringContextHolder.getBean("localeResolver")).resolveLocale(request);
                message = SpringContextHolder.getApplicationContext().getMessage(gex.getMessage(), gex.getArgs(), gex.getMessage(), locale);
            } else {
                message = ex.getMessage();
            }
            MappingJackson2JsonView view = new MappingJackson2JsonView();
            Map<String, Object> map = new HashMap<>();
            map.put("status", ResponseData.Status.ERROR.toString());
            map.put("message", message);
            model.setView(view);
            view.setAttributesMap(map);
        } else {
            model.setViewName("error/500");
            model.addObject("e", ex);
        }
        return model;
    }

}
