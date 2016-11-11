package com.netmng.com.intercept;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.netmng.svc.adm.AdmService;
import com.netmng.util.ComUtil;
import com.netmng.vo.User;

public class SesstionCK implements HandlerInterceptor {

	@Autowired(required=true) 
	private ComUtil comUtil;

	@Autowired(required=true) 
	private AdmService admService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("SesstionCK.preHandle");
		
		HttpSession session = request.getSession(true);
		
		System.out.println("=====>> SessionCK="+request.getSession().getServletContext().getContextPath() + "/user/loginF.do");
		if(session.getAttribute(User.SESSION_KEY) == null) {
			System.out.println("=====>> SessionCK=true");
			this.comUtil.setRefererSession(request);
			response.sendRedirect(request.getSession().getServletContext().getContextPath() + "/user/loginF.do");
			return false;
		} else {
			System.out.println("=====>> SessionCK=false");
			User user = (User)session.getAttribute(User.SESSION_KEY);
			Map<String, String> map = new HashMap<String, String>();
			map.put("seq", user.getSeq());
			map.put("url", request.getRequestURI());
			
			if(this.comUtil.userAuthCk(this.admService, user, map)) {
				return true;
			} else {
				response.sendRedirect(request.getSession().getServletContext().getContextPath() + "/user/auth.do");
				return false;
			}
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("SesstionCK.postHandle");
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("SesstionCK.afterCompletion");
	}
}
