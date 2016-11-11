package com.netmng.param.adm;

import com.netmng.param.PageParam;
import com.netmng.util.StrUtil;

public class SrchUserParam extends PageParam {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1148913184894444429L;
	
	private String mode;
	private String srchTxt;
	
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
	@Override
	public String getSortKey() {
		return StrUtil.nullToStr(super.getSortKey()).equals("")? "SEQ" : super.getSortKey();
	}
	@Override
	public String getSortType() {
		return StrUtil.nullToStr(super.getSortType()).equals("")? "DESC" : super.getSortType();
	}
	
}
