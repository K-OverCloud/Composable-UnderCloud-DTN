package com.netmng.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.netmng.websvc.rest.server.param.event.Interface;

public class Event {
	
	private	String	seq;
	private	String	cause;
	private	String	description;
	
	private String			router;
	private List<String>	listInterface;
	
	private	String	restore_yn;
	private	String	line;
	private	Date	createtime;
	
	private List<EventLog> eventLogList;
	
	public String getSeq() {
		return this.seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getCause() {
		return this.cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRestore_yn() {
		return this.restore_yn;
	}
	public void setRestore_yn(String restore_yn) {
		this.restore_yn = restore_yn;
	}
	public String getLine() {
		return this.line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public Date getCreatetime() {
		return this.createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public List<EventLog> getEventLogList() {
		return this.eventLogList;
	}
	public void setEventLogList(List<EventLog> eventLogList) {
		this.eventLogList = eventLogList;
	}
	
	
	public String getRouter() {
		return router;
	}
	public void setRouter(String router) {
		this.router = router;
	}
	public List<String> getListInterface() {
		if(this.listInterface==null)
			this.listInterface = new ArrayList();			
		return listInterface;
	}
	public void setListInterface(List<String> listInterface) {
		this.listInterface = listInterface;
	}
	
	
}
