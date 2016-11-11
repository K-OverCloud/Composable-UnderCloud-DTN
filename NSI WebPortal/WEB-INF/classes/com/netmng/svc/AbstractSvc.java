package com.netmng.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Service
public class AbstractSvc {
	
	@Autowired 
	public SqlMapClientTemplate sqlMapClientTemplate;
	
	public Object getSessionData(String sessName) {		
		return RequestContextHolder.getRequestAttributes().getAttribute(sessName, RequestAttributes.SCOPE_SESSION);
	}
}
