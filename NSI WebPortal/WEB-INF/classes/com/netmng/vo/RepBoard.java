/**
 * 
 */
package com.netmng.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 최수영
 * 
 * 답변
 *
 */
public class RepBoard implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6506066599791606770L;
	
	private String seq;							// 답변글 일련번호
	private String brd_seq;						// 게시판 글 일련번호
	private String content;						// 내용
	private String user_seq;					// 회원일련번호(비회원일 경우는 -1)
	private String user_id;						// 비회원 아이디(대화명)(100자)
	private String user_pass;					// 비회원 패스워드(100자)
	private String user_email;					// 비회원 이메일(1000자)
	private String g_seq;						// 그룹 번호(최상위 글 일련번호)
	private String p_seq;						// 부모글 번호
	private String step;						// 그룹순서
	private String lvl;							// 최상위글은 0
	private String use_yn 			= "Y";		// 사용여부(기본값 Y)
	private Date createTime;					// 작성시각
	
	public String getSeq() {
		return this.seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getBrd_seq() {
		return this.brd_seq;
	}
	public void setBrd_seq(String brd_seq) {
		this.brd_seq = brd_seq;
	}
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUser_seq() {
		return this.user_seq;
	}
	public void setUser_seq(String user_seq) {
		this.user_seq = user_seq;
	}
	public String getUser_id() {
		return this.user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pass() {
		return this.user_pass;
	}
	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
	}
	public String getUser_email() {
		return this.user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
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
	public String getStep() {
		return this.step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getLvl() {
		return this.lvl;
	}
	public void setLvl(String lvl) {
		this.lvl = lvl;
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
