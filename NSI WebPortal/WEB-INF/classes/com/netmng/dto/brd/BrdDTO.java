package com.netmng.dto.brd;

import java.util.List;

import com.netmng.vo.Board;
import com.netmng.vo.FileInfo;
import com.netmng.vo.User;

public class BrdDTO extends Board {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2531247201567691643L;
	
	private String 	mode;
	private User user;
	private List<FileInfo> fileInfoList;
	
	public String getMode() {
		return this.mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public User getUser() {
		return this.user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<FileInfo> getFileInfoList() {
		return this.fileInfoList;
	}
	public void setFileInfoList(List<FileInfo> fileInfoList) {
		this.fileInfoList = fileInfoList;
	}
}
