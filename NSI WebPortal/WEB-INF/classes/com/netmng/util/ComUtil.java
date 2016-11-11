package com.netmng.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.netmng.svc.adm.AdmService;
import com.netmng.vo.MenuAuth;
import com.netmng.vo.User;

public class ComUtil {
	
	public void setRefererSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute(User.REFERER_KEY, request.getRequestURI());
		Map<String, String[]> paramMap = new HashMap<String, String[]>();
		
		Enumeration<?> enume = request.getParameterNames();
		
		while(enume.hasMoreElements()){
			String 		param = enume.nextElement().toString();
			String[] 	value = request.getParameterValues(param);
			
			paramMap.put(param, value);
		}
		
		session.setAttribute(User.PARAM_KEY, paramMap);
	}
	
	public boolean userAuthCk(AdmService admService, User user, Map<String, String> map) {
		
		try {
			if(admService.getUserInfoSelect(user) != null) {
				MenuAuth menuAuth = admService.getMenuAuthSelect(map);
				if(menuAuth != null && "Y".equals(menuAuth.getList_auth())) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch(Exception e) {
			return false;
		}
	}
	
	public String getReserveStatusStr(String status) {
		return status.equals("C")? "reserved" : status.equals("P")? "provisioned" : status.equals("R")? "released" : status.equals("T")? "terminate" : "";
	}
	
	public String getReserveFlagStr(String flag) {
		return flag.equals("C")? "confirmed" : flag.equals("R")? "request" : flag.equals("F")? "failed" : "";
	}
	
	
}
