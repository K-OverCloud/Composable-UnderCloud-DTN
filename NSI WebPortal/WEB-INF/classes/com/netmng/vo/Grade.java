package com.netmng.vo;

import java.io.Serializable;
import java.util.Date;

public class Grade implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8413558168029373395L;

	private String 	seq;
	private String 	step;
	private String 	name;
	private String 	title;
	private String 	comment;
	private String 	use_yn;
	private Date 	createTime;
	
	public String getSeq() {
		return this.seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getStep() {
		return this.step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getComment() {
		return this.comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUse_yn() {
		return this.use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
