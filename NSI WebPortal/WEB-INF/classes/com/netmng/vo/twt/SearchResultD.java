package com.netmng.vo.twt;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchResultD {

	private long searm_seq;
	private long searresd_twt_id;
	private long searresm_seq;
	private long searresd_seq;
	private String seard_seq;
	private String searresd_twt_from_user;
	private long searresd_twt_from_user_id;
	private String searresd_twt_to_user;
	private long searresd_twt_to_user_id;
	private Date searresd_twt_created_at;
	private String searresd_twt_text;
	private String searresd_twt_htmlText;
	private String searresd_twt_source;
	private String searresd_twt_profile_img_url;
	private Date searresd_time;
	
	public long getSearm_seq() {
		return this.searm_seq;
	}
	public void setSearm_seq(long searm_seq) {
		this.searm_seq = searm_seq;
	}
	public long getSearresd_twt_id() {
		return this.searresd_twt_id;
	}
	public void setSearresd_twt_id(long searresd_twt_id) {
		this.searresd_twt_id = searresd_twt_id;
	}
	public long getSearresm_seq() {
		return this.searresm_seq;
	}
	public void setSearresm_seq(long searresm_seq) {
		this.searresm_seq = searresm_seq;
	}
	public long getSearresd_seq() {
		return this.searresd_seq;
	}
	public void setSearresd_seq(long searresd_seq) {
		this.searresd_seq = searresd_seq;
	}
	public String getSeard_seq() {
		return this.seard_seq;
	}
	public void setSeard_seq(String seard_seq) {
		this.seard_seq = seard_seq;
	}
	public String getSearresd_twt_from_user() {
		return this.searresd_twt_from_user;
	}
	public void setSearresd_twt_from_user(String searresd_twt_from_user) {
		this.searresd_twt_from_user = searresd_twt_from_user;
	}
	public long getSearresd_twt_from_user_id() {
		return this.searresd_twt_from_user_id;
	}
	public void setSearresd_twt_from_user_id(long searresd_twt_from_user_id) {
		this.searresd_twt_from_user_id = searresd_twt_from_user_id;
	}
	public String getSearresd_twt_to_user() {
		return this.searresd_twt_to_user;
	}
	public void setSearresd_twt_to_user(String searresd_twt_to_user) {
		this.searresd_twt_to_user = searresd_twt_to_user;
	}
	public long getSearresd_twt_to_user_id() {
		return this.searresd_twt_to_user_id;
	}
	public void setSearresd_twt_to_user_id(long searresd_twt_to_user_id) {
		this.searresd_twt_to_user_id = searresd_twt_to_user_id;
	}
	public Date getSearresd_twt_created_at() {
		return this.searresd_twt_created_at;
	}
	public void setSearresd_twt_created_at(Date searresd_twt_created_at) {
		this.searresd_twt_created_at = searresd_twt_created_at;
	}
	public String getSearresd_twt_text() {
		return this.searresd_twt_text;
	}
	public void setSearresd_twt_text(String searresd_twt_text) {
		this.searresd_twt_text = searresd_twt_text;
		Pattern p = Pattern.compile("([\\p{Alnum}]+)://([a-z0-9.\\-&/%=?:@#$(),.+;~\\_]+)", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(this.searresd_twt_text);
		this.searresd_twt_htmlText = m.replaceAll("<a href='http://$2' target=_blank>http://$2</a>");
	}
	public String getSearresd_twt_htmlText() {
		return this.searresd_twt_htmlText;
	}
	public void setSearresd_twt_htmlText(String searresd_twt_htmlText) {
		this.searresd_twt_htmlText = searresd_twt_htmlText;
	}
	public String getSearresd_twt_source() {
		return this.searresd_twt_source;
	}
	public void setSearresd_twt_source(String searresd_twt_source) {
		this.searresd_twt_source = searresd_twt_source;
	}
	public String getSearresd_twt_profile_img_url() {
		return this.searresd_twt_profile_img_url;
	}
	public void setSearresd_twt_profile_img_url(String searresd_twt_profile_img_url) {
		this.searresd_twt_profile_img_url = searresd_twt_profile_img_url;
	}
	public Date getSearresd_time() {
		return this.searresd_time;
	}
	public void setSearresd_time(Date searresd_time) {
		this.searresd_time = searresd_time;
	}
	
}
