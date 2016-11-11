package com.netmng.param.adm;

import com.netmng.param.PageParam;
import com.netmng.util.StrUtil;

public class SrchErrorParam extends PageParam {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8695334183261405171L;

	private String mode;

	public String getMode() {
		return this.mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
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
