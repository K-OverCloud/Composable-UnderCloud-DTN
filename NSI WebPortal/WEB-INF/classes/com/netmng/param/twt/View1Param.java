package com.netmng.param.twt;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import com.netmng.param.PageParam;

public class View1Param extends PageParam implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -418793962223922938L;
	
	@Pattern(regexp="|(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])", message="올바른 형식의 시작날짜를 입력하세요.")
	private String createdAtBegin;
	@Pattern(regexp="|(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])", message="올바른 형식의 종료날짜를 입력하세요.")
	private String createdAtEnd;

	private String srchTxt;

	public String getCreatedAtBegin() {
		return this.createdAtBegin;
	}
	public void setCreatedAtBegin(String createdAtBegin) {
		this.createdAtBegin = createdAtBegin;
	}
	public String getCreatedAtEnd() {
		return this.createdAtEnd;
	}
	public void setCreatedAtEnd(String createdAtEnd) {
		this.createdAtEnd = createdAtEnd;
	}
	public String getSrchTxt() {
		return this.srchTxt;
	}
	public void setSrchTxt(String srchTxt) {
		this.srchTxt = srchTxt;
	}
}
