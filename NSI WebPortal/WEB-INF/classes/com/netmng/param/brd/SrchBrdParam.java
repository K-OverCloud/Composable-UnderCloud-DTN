package com.netmng.param.brd;

import javax.validation.constraints.Pattern;

import com.netmng.param.PageParam;

public class SrchBrdParam extends PageParam {

	/**
	 * 
	 */
	private static final long serialVersionUID = 832714835617989528L;
	
	@Pattern(regexp="|(19|20)\\d{2}.(0[1-9]|1[0-2]).(0[1-9]|[12][0-9]|3[01])", message="com.netmng.param.brd.SrchBrdParam.createBegin")
	private String createBegin;
	@Pattern(regexp="|(19|20)\\d{2}.(0[1-9]|1[0-2]).(0[1-9]|[12][0-9]|3[01])", message="com.netmng.param.brd.SrchBrdParam.createEnd")
	private String createEnd;

	private String mode;
	private String srchTxt;
	
	public String getCreateBegin() {
		return this.createBegin;
	}
	public void setCreateBegin(String createBegin) {
		this.createBegin = createBegin;
	}
	public String getCreateEnd() {
		return this.createEnd;
	}
	public void setCreateEnd(String createEnd) {
		this.createEnd = createEnd;
	}
	public String getMode() {
		return this.mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getSrchTxt() {
		return this.srchTxt;
	}
	public void setSrchTxt(String srchTxt) {
		this.srchTxt = srchTxt;
	}
}
