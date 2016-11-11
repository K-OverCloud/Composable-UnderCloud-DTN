package com.netmng.param.nsa;

import javax.validation.constraints.Pattern;

import com.netmng.param.PageParam;
import com.netmng.util.StrUtil;

public class SrchReserveParam extends PageParam {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6787129925033046063L;
	
	private String mode;
	private String user_seq;	
	
	@Pattern(regexp="|(19|20)\\d{2}.(0[1-9]|1[0-2]).(0[1-9]|[12][0-9]|3[01])", message="com.netmng.param.adm.SrchInterMonitorParam.createBegin")
	private String 	createBegin;
	@Pattern(regexp="|(19|20)\\d{2}.(0[1-9]|1[0-2]).(0[1-9]|[12][0-9]|3[01])", message="com.netmng.param.adm.SrchInterMonitorParam.createEnd")
	private String	createEnd;
	
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
		return StrUtil.nullToStr(super.getSortKey()).equals("")? "SEQ" : super.getSortKey();
	}
	@Override
	public String getSortType() {
		return StrUtil.nullToStr(super.getSortType()).equals("")? "DESC" : super.getSortType();
	}
	
	public String getCreateBegin() {
		return createBegin;
	}
	public void setCreateBegin(String createBegin) {
		this.createBegin = createBegin;
	}
	public String getCreateEnd() {
		return createEnd;
	}
	public void setCreateEnd(String createEnd) {
		this.createEnd = createEnd;
	}
}
