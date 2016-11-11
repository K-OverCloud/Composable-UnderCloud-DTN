package com.netmng.param.adm;

import javax.validation.constraints.Pattern;

import com.netmng.param.PageParam;
import com.netmng.util.StrUtil;

public class SrchInterMonitorParam extends PageParam {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8012988947075580053L;

	private String 	mode;
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
	@Override
	public String getSortKey() {
		return StrUtil.nullToStr(super.getSortKey()).equals("")? "SEQ" : super.getSortKey();
	}
	@Override
	public String getSortType() {
		return StrUtil.nullToStr(super.getSortType()).equals("")? "DESC" : super.getSortType();
	}
}
