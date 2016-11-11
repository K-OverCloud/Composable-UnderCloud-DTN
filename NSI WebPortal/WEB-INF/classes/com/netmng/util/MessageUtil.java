package com.netmng.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

public class MessageUtil {
	@Autowired 
	public LocaleResolver localeResolver;
	@Autowired 
	public MessageSource messageSource;
	
	public String getMessage(String id, Object[] param, HttpServletRequest request) {
		return this.messageSource.getMessage(id, param, RequestContextUtils.getLocale(request));
	}
}
