package com.netmng.vo;

import java.io.Serializable;
import java.util.Date;

public class NotificationLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2920823847177486508L;

	private String 	noti_seq;
	private String 	connection_id;
	private int	notification_id;
	private Date	occur_time;
	private String	noti_type;
	private String	noti_type_desc;
	
	public NotificationLog(){
	}
	public NotificationLog(	String sConnectionId,
							int iNotificationId,
							javax.xml.datatype.XMLGregorianCalendar timeStamp,
							String noti_type,
							String noti_type_desc
							){
		this.setConnection_id(sConnectionId);
		this.setNotification_id(iNotificationId);
		if(timeStamp==null)	this.setOccur_time(null);
		else				this.setOccur_time(timeStamp.toGregorianCalendar().getTime());
		this.setNoti_type(noti_type);
		this.setNoti_type_desc(noti_type_desc);
	}
	
	public String getNoti_seq() {
		return noti_seq;
	}
	public void setNoti_seq(String noti_seq) {
		this.noti_seq = noti_seq;
	}
	public String getConnection_id() {
		return connection_id;
	}
	public void setConnection_id(String connection_id) {
		this.connection_id = connection_id;
	}
	public int getNotification_id() {
		return notification_id;
	}
	public void setNotification_id(int notification_id) {
		this.notification_id = notification_id;
	}
	public Date getOccur_time() {
		return occur_time;
	}
	public void setOccur_time(Date occur_time) {
		this.occur_time = occur_time;
	}
	public String getNoti_type() {
		return noti_type;
	}
	public void setNoti_type(String noti_type) {
		this.noti_type = noti_type;
	}
	public String getNoti_type_desc() {
		return noti_type_desc;
	}
	public void setNoti_type_desc(String noti_type_desc) {
		this.noti_type_desc = noti_type_desc;
	}
	
}
