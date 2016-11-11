/**
 * 
 */
package com.netmng.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 최수영
 * 
 * 카테고리 보드
 *
 */
public class BoardCate implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5035955335659596830L;
	
	private String 	seq;				// 시퀀스
	private String 	title;				// 게시판 명칭(100자)
	private String 	use_yn = "Y";		// 사용여부 : 기본값 Y
	private String 	comment;			// 설명
	private Date 	createTime;			// 생성시각
	
	public String getSeq() {
		return this.seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUse_yn() {
		return this.use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getComment() {
		return this.comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
