package com.noisyle.crowbar.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

public class WebHandlerInterceptor implements HandlerInterceptor {

	private static Logger logger = LoggerFactory.getLogger(WebHandlerInterceptor.class);
	
	/**
	 * 在调用controller具体方法前拦截
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		String refer = request.getHeader("referer");
		
		//请求静态资源时不校验
		if(object instanceof ResourceHttpRequestHandler){
			logger.debug("请求静态资源url:{}\nrefer:{}", url, refer);
			return true;
		}
		
		logger.debug("请求url:{}\nmethod:{}\nparams:{}\nrefer:{}", url, request.getMethod(), request.getParameterMap(), refer);
		return true;
	}

	/**
	 * 在调用controller具体方法后拦截
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
			ModelAndView modelAndView) throws Exception {

	}
	
	/**
	 * 完成页面的render后调用
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) throws Exception {
		
	}

}
