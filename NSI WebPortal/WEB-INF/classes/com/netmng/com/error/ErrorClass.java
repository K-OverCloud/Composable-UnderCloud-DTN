package com.netmng.com.error;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import com.netmng.util.MessageUtil;

@SuppressWarnings("unused")
public class ErrorClass {
	
	private static final long serialVersionUID = -8712498520604301581L;
	public static final String ERROR400 = "/error/400";
	public static final String ERROR500 = "/error/500";
	public static final String ERRORDATA = "/error/dataError";
	
	private BindingResult result;
	private String errMsg = null;
	private boolean objectError = true;
	
	public ErrorClass() {
		super();
	}

	public BindingResult getResult() {
		return this.result;
	}

	public void setResult(BindingResult result) {
		this.result = result;
	}

	public String getErrMsg() {
		return this.errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public boolean isObjectError() {
		return this.objectError;
	}

	public void setObjectError(boolean objectError) {
		this.objectError = objectError;
	}

	public ErrorClass getInstance(BindingResult result, HttpServletRequest request, MessageUtil messageUtil, boolean objectError) {
		
		this.result = result;
		this.errMsg = messageUtil.getMessage(result.getFieldError().getDefaultMessage(), new Object[0], request);
		this.objectError = objectError;
		return this;
	}
}
