package com.netmng.vo;

import java.io.Serializable;
import java.util.Date;

public class Menu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2305546130482621453L; 

	private String 	seq;
	private String 	g_seq;
	private String 	p_seq;
	private String 	lvl;
	private String 	step;
	private String 	cate_seq;
	private String 	name;
	private String 	comment;
	private String 	url;
	private String 	type;
	private String 	use_yn;
	private Date 	createTime;
	
	public String getSeq() {
		return this.seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getG_seq() {
		return this.g_seq;
	}
	public void setG_seq(String g_seq) {
		this.g_seq = g_seq;
	}
	public String getP_seq() {
		return this.p_seq;
	}
	public void setP_seq(String p_seq) {
		this.p_seq = p_seq;
	}
	public String getLvl() {
		return this.lvl;
	}
	public void setLvl(String lvl) {
		this.lvl = lvl;
	}
	public String getStep() {
		return this.step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getCate_seq() {
		return this.cate_seq;
	}
	public void setCate_seq(String cate_seq) {
		this.cate_seq = cate_seq;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return this.comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
