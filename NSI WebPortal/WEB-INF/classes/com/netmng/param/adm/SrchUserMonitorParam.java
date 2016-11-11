package com.netmng.param.adm;

import com.netmng.param.PageParam;
import com.netmng.util.StrUtil;

public class SrchUserMonitorParam extends PageParam {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3646947678735977185L;
	private String 	mode;
	private String 	user_seq;
	private String	subSortKey;
	private String	subSortType;
	
	public String getMode() {
		return this.mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}	
	public String getUser_seq() {
		return this.user_seq;
	}
	public void setUser_seq(String user_seq) {
		this.user_seq = user_seq;
	}
	@Override
	public String getSortKey() {
		return StrUtil.nullToStr(super.getSortKey()).equals("")? "D.USER_SEQ" : super.getSortKey();
	}
	@Override
	public String getSortType() {
		return StrUtil.nullToStr(super.getSortType()).equals("")? "DESC" : super.getSortType();
	}
	public String getSubSortKey() {
		return StrUtil.nullToStr(this.subSortKey).equals("")? "SEQ" : this.subSortKey;
	}
	public void setSubSortKey(String subSortKey) {
		this.subSortKey = subSortKey;
	}
	public String getSubSortType() {
		return StrUtil.nullToStr(this.subSortType).equals("")? "DESC" : this.subSortType;
	}
	public void setSubSortType(String subSortType) {
		this.subSortType = subSortType;
	}
}
