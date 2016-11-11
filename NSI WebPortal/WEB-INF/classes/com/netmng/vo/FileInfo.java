package com.netmng.vo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;

@GroupSequence({FileInfo.class, FileInfo.FileDown.class, FileInfo.FileList.class})
public class FileInfo implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6157471854595439106L;
	
	public static interface FileDown {}
	public static interface FileList {}
	
	private String 	mode;
	
	@NotNull(groups = {FileInfo.FileDown.class}, message="vo.FileInfo.seq")
	private String 	seq;
	private String 	cate_seq;			// 게시판인지 타 기능에 관련된 파일인지 구분(board_cate.seq 값 참조)
	private String 	cont_seq;			
	
	private String 	name;
	private String 	path;
	private String 	realName;
	private String 	url;
	private String 	size;
	private String	mime;
	
	private String 	del_yn = "N";		// 삭제여부 : 기본값 N
	private Date 	createTime;			// 생성시각
	
	public String getMode() {
		return this.mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getSeq() {
		return this.seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getCate_seq() {
		return this.cate_seq;
	}
	public void setCate_seq(String cate_seq) {
		this.cate_seq = cate_seq;
	}
	public String getCont_seq() {
		return this.cont_seq;
	}
	public void setCont_seq(String cont_seq) {
		this.cont_seq = cont_seq;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return this.path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getRealName() {
		return this.realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSize() {
		return this.size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getMime() {
		return this.mime;
	}
	public void setMime(String mime) {
		this.mime = mime;
	}
	public String getDel_yn() {
		return this.del_yn;
	}
	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
		
}
