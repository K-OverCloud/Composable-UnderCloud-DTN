package com.netmng.vo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.GroupSequence;

import com.netmng.dto.adm.stp.RouterDTO;

@GroupSequence({InterfaceInfo.AdmTopologyView.class, InterfaceInfo.AdmTopologyInsert.class, InterfaceInfo.AdmTopologyUpdate.class})
public class InterfaceInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1551068238889952036L;

	public static interface AdmTopologyView {}
	public static interface AdmTopologyInsert {}
	public static interface AdmTopologyUpdate {}
	
	private String 	seq;
	private String	group_seq;
	private String 	router_seq;
	private String 	primary_ip;
	private String	secondary_ip;
	private String 	primary_bw;
	private String	secondary_bw;
	private String 	link_interface_seq;
	private String	use_yn;
	private Date	createtime;
	private Date	deltime;
	private boolean	p_fault_yn;
	private boolean	s_fault_yn;
	private	String	use_line;
	
	private RouterDTO	routerDTO;

	private String local_id;
	private String local_nm;

	public String getSeq() {
		return this.seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getGroup_seq() {
		return this.group_seq;
	}
	public void setGroup_seq(String group_seq) {
		this.group_seq = group_seq;
	}
	public String getRouter_seq() {
		return this.router_seq;
	}
	public void setRouter_seq(String router_seq) {
		this.router_seq = router_seq;
	}
	public String getPrimary_ip() {
		return this.primary_ip;
	}
	public void setPrimary_ip(String primary_ip) {
		this.primary_ip = primary_ip;
	}
	public String getSecondary_ip() {
		return this.secondary_ip;
	}
	public void setSecondary_ip(String secondary_ip) {
		this.secondary_ip = secondary_ip;
	}
	public String getPrimary_bw() {
		return this.primary_bw;
	}
	public void setPrimary_bw(String primary_bw) {
		this.primary_bw = primary_bw;
	}
	public String getSecondary_bw() {
		return this.secondary_bw;
	}
	public void setSecondary_bw(String secondary_bw) {
		this.secondary_bw = secondary_bw;
	}
	public String getLink_interface_seq() {
		return this.link_interface_seq;
	}
	public void setLink_interface_seq(String link_interface_seq) {
		this.link_interface_seq = link_interface_seq;
	}
	public String getUse_yn() {
		return this.use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public Date getCreatetime() {
		return this.createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getDeltime() {
		return this.deltime;
	}
	public void setDeltime(Date deltime) {
		this.deltime = deltime;
	}
	public boolean getP_fault_yn() {
		return this.p_fault_yn;
	}
	public void setP_fault_yn(boolean p_fault_yn) {
		this.p_fault_yn = p_fault_yn;
	}
	public boolean getS_fault_yn() {
		return this.s_fault_yn;
	}
	public void setS_fault_yn(boolean s_fault_yn) {
		this.s_fault_yn = s_fault_yn;
	}
	public RouterDTO getRouterDTO() {
		return this.routerDTO;
	}
	public void setRouterDTO(RouterDTO routerDTO) {
		this.routerDTO = routerDTO;
	}
	public String getUse_line() {
		return this.use_line;
	}
	public void setUse_line(String use_line) {
		this.use_line = use_line;
	}
	public String getLocal_id() {
		return local_id;
	}
	public void setLocal_id(String local_id) {
		this.local_id = local_id;
	}
	public String getLocal_nm() {
		return local_nm;
	}
	public void setLocal_nm(String local_nm) {
		this.local_nm = local_nm;
	}	
}
