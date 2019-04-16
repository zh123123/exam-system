package com.Z.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.Z.pojo.JSONResult;

@ControllerAdvice
public class MyExceptionHandler {
	
	public static final String EXCEPTION_VIEW =  "error";
	
//	@ExceptionHandler(value=Exception.class)
//	@org.springframework.web.bind.annotation.ExceptionHandler(value=Exception.class)
//	public Object errorHandler (HttpServletRequest request , HttpServletResponse response ,
//			Exception e) {
//		
//    	e.printStackTrace();
//    	
//		ModelAndView mav = new ModelAndView();
//        mav.addObject("exception", e);
//        mav.addObject("url", request.getRequestURL());
//        mav.setViewName(EXCEPTION_VIEW);
//        return mav;
//	}
	
	@ExceptionHandler(value = Exception.class)
	public Object errorHandler (HttpServletRequest request , HttpServletResponse response ,
			Exception e) { 
		
		if(isAjax(request)) {
			return JSONResult.errorException(e.getMessage());
		}else {
			ModelAndView mav = new ModelAndView();
	        mav.addObject("exception", e);
	        mav.addObject("url", request.getRequestURL());
	        mav.setViewName(EXCEPTION_VIEW);
	        return mav;
		}
		
	}
	
	/**
	 * @Description: 判断是否是ajax请求
	 * 
	 */
	public static boolean isAjax(HttpServletRequest httpRequest){
		return  (httpRequest.getHeader("X-Requested-With") != null  
					&& "XMLHttpRequest"
						.equals( httpRequest.getHeader("X-Requested-With").toString()) );
	}
	
}
