package com.netmng.param;

import java.io.Serializable;

public class PageParam implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6647585366819412417L;
	private long pageIdx 	= 1;
	private long row		= 10;
	private long totRow		= 0;
	private long sIdx		= (this.pageIdx - 1) * this.row;		// mysql은 0부터 oracle, mssql은 +1부터
	private long eIdx		= this.pageIdx * this.row;				// mysql은 사용하지 않음
	private String sortKey;
	private String sortType;
	
	public long getPageIdx() {
		return this.pageIdx;
	}
	public void setPageIdx(long pageIdx) {
		this.pageIdx = pageIdx;
		this.sIdx = (this.pageIdx - 1) * this.row;
		this.eIdx = this.pageIdx * this.row;
	}
	public long getRow() {
		return this.row;
	}
	public void setRow(long row) {
		this.row = row;
		this.sIdx = (this.pageIdx - 1) * this.row;
		this.eIdx = this.pageIdx * this.row;
	}
	public long getTotRow() {
		return this.totRow;
	}
	public void setTotRow(long totRow) {
		this.totRow = totRow;
	}
	public long getsIdx() {
		return this.sIdx;
	}
	public void setsIdx(long sIdx) {
		this.sIdx = sIdx;
	}
	public long geteIdx() {
		return this.eIdx;
	}
	public void seteIdx(long eIdx) {
		this.eIdx = eIdx;
	}
	public String getSortKey() {
		return this.sortKey;
	}
	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}
	public String getSortType() {
		return this.sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
}
