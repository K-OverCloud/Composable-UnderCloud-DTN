package com.netmng.xss;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringEscapeUtils;

import com.nhncorp.lucy.security.xss.XssFilter;

public class XssRequestWrapper extends HttpServletRequestWrapper {

	private XssFilter filter;
	
	public XssFilter getFilter() {
		return this.filter;
	}
	public void setFilter(XssFilter filter) {
		this.filter = filter;
	}
	public XssRequestWrapper(HttpServletRequest request) {
		super(request);
		this.filter = XssFilter.getInstance("lucy-xss-superset.xml");
		// TODO Auto-generated constructor stub
	}
	public String[] getParameterValues(String parameter) { 
		String[] values = super.getParameterValues(parameter);         
		
		if (values==null) { 
			return null;             
		}         
		int count = values.length;         
		String[] encodedValues = new String[count];
		for(int i=0; i<count; i++) {
			encodedValues[i] = filter(values[i]);          
		}           
		
		return encodedValues;        
	}              
	
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);             
		if (value == null) {                    
			return null;                      
		}             
		
		return filter(value);       
	}              
	
	public String getHeader(String name) {
		String value = super.getHeader(name);           
		if (value == null)               
			return null;          
		return filter(value);                  
	}         
	
	private String filter(String input) {        
		if(input==null) {            
			return null;        
		}        
		//String clean = new HTMLInputFilter().filter(input.replaceAll("\"", "%22").replaceAll("\'","%27"));        
		//return clean.replaceAll("<", "%3C").replaceAll(">", "%3E");  
		//System.out.println("this.filter.doFilter =========================== " + StringEscapeUtils.escapeJavaScript(input));
		
		return StringEscapeUtils.escapeJavaScript(input);
	}
}
