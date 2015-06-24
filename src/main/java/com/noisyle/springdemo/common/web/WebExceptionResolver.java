package com.noisyle.springdemo.common.web;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.noisyle.springdemo.common.util.JsonUtils;

public class WebExceptionResolver implements HandlerExceptionResolver {

	final private static Logger logger = LoggerFactory.getLogger(WebExceptionResolver.class);

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception ex) {

		String uri = request.getRequestURI().toLowerCase();
		logger.error("Controller中拦截到异常\n--method:\"{}\"\n--url:\"{}\"", object, uri, ex);
		HandlerMethod method = (HandlerMethod) object;
		ResponseBody annotation = method.getMethodAnnotation(ResponseBody.class);
		if (annotation!=null) {

			response.resetBuffer();
			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache, must-revalidate");
			Writer writer = null;

			ResponseMessage msg = new ResponseMessage();
			msg.setErrorMessage(ex.getMessage());

			try {
				writer = response.getWriter();
				writer.write(JsonUtils.toJson(msg));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		} else {

			ModelAndView model = new ModelAndView();
			model.setViewName("admin/index"); // TODO 添加错误页面
			model.addObject("e", ex);

			return model;
		}
	}

}
