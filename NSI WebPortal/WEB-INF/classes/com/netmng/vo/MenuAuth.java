package com.netmng.vo;

import java.io.Serializable;

public class MenuAuth implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8807711784909298001L;

	private String 	seq;
	private String 	grade_seq;
	private String 	menu_seq;
	private String 	list_auth;
	private String	view_auth;
	private String	create_auth;
	private String	update_auth;
	private String	delete_auth;
	private String	ripple_auth;
	
	public String getSeq() {
		return this.seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getGrade_seq() {
		return this.grade_seq;
	}
	public void setGrade_seq(String grade_seq) {
		this.grade_seq = grade_seq;
	}
	public String getMenu_seq() {
		return this.menu_seq;
	}
	public void setMenu_seq(String menu_seq) {
		this.menu_seq = menu_seq;
	}
	public String getList_auth() {
		return this.list_auth;
	}
	public void setList_auth(String list_auth) {
		this.list_auth = list_auth;
	}
	public String getView_auth() {
		return this.view_auth;
	}
	public void setView_auth(String view_auth) {
		this.view_auth = view_auth;
	}
	public String getCreate_auth() {
		return this.create_auth;
	}
	public void setCreate_auth(String create_auth) {
		this.create_auth = create_auth;
	}
	public String getUpdate_auth() {
		return this.update_auth;
	}
	public void setUpdate_auth(String update_auth) {
		this.update_auth = update_auth;
	}
	public String getDelete_auth() {
		return this.delete_auth;
	}
	public void setDelete_auth(String delete_auth) {
		this.delete_auth = delete_auth;
	}
	public String getRipple_auth() {
		return this.ripple_auth;
	}
	public void setRipple_auth(String ripple_auth) {
		this.ripple_auth = ripple_auth;
	}
	
}
