package com.netmng.com.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

public class LocaleChange implements HandlerInterceptor {
	
	@Autowired 
	public LocaleResolver localeResolver;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		String locale = request.getParameter("lang");
		
		if(locale != null && locale.equals("ko")) {
			this.localeResolver.setLocale(request, response, StringUtils.parseLocaleString(locale.toLowerCase()));
		} else if(locale != null && locale.equals("en")) {
			this.localeResolver.setLocale(request, response, StringUtils.parseLocaleString(locale.toLowerCase()));
		} else if(locale == null || locale.equals("")) {
			String setLang = response.getLocale().getLanguage();
			if(!"ko".equals(setLang) && !"en".equals(setLang)){
				this.localeResolver.setLocale(request, response, StringUtils.parseLocaleString("en".toLowerCase()));
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
