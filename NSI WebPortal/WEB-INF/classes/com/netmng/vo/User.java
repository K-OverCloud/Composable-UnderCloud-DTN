package com.netmng.vo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

@GroupSequence({User.class, User.UserInsert.class, User.MyInfoUpdate.class, User.MyInfoPassUpdate.class, User.UserUpdate.class, User.UserDelete.class, User.Login.class, User.AdmUserView.class, User.FinfUser.class})
public class User implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9152958938720164702L;
	
	public static interface UserInsert {}
	public static interface MyInfoUpdate {}
	public static interface MyInfoPassUpdate {}
	public static interface UserUpdate {}
	public static interface UserDelete {}
	public static interface Login {}
	public static interface AdmUserView {}
	public static interface FinfUser {}
	
	public static String SESSION_KEY = "SESS_USER";
	public static String SESSION_NOTICELIST_KEY = "SESS_NOTICE_LIST";
	public static String REFERER_KEY = "REFERER";
	public static String PARAM_KEY = "PARAM_INFO";
	
	@NotNull(groups = {User.UserDelete.class, User.AdmUserView.class}, message="com.netmng.vo.User.seq")
	@NotEmpty(groups = {User.UserDelete.class, User.AdmUserView.class}, message="com.netmng.vo.User.seq")
	private String 	seq;				// 회원일련번호	
	@NotNull(groups = {User.UserInsert.class, User.Login.class}, message="com.netmng.vo.User.id")
	@NotEmpty(groups = {User.UserInsert.class, User.Login.class}, message="com.netmng.vo.User.id")
	@Pattern(groups = {User.UserInsert.class}, regexp="[a-zA-Z0-9]{5,100}", message="com.netmng.vo.User.id.incorrected")
	private String 	id;					// 회원id
	@NotNull(groups = {User.UserInsert.class, User.MyInfoUpdate.class, User.MyInfoPassUpdate.class, User.Login.class}, message="com.netmng.vo.User.pass")
	@NotEmpty(groups = {User.UserInsert.class, User.MyInfoUpdate.class, User.MyInfoPassUpdate.class, User.Login.class}, message="com.netmng.vo.User.pass")
	private String 	pass;				// 회원비밀번호
	@NotNull(groups = {User.UserInsert.class, User.MyInfoUpdate.class, User.FinfUser.class}, message="com.netmng.vo.User.name")
	@NotEmpty(groups = {User.UserInsert.class, User.MyInfoUpdate.class, User.FinfUser.class}, message="com.netmng.vo.User.name")
	private String 	name;				// 회원명
	
	private String 	jumin_no;			// 주민등록번호
	private String	company;			// 기관
	@Pattern(groups = {User.UserInsert.class, User.FinfUser.class}, regexp="[a-zA-Z0-9][^:;]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$", message="com.netmng.vo.User.email.incorrected")
	private String	email;				// 이메일
	private String 	grade_seq;			// 회원등급
	private String 	active_yn	= "Y";	// 활동여부
	private Date 	createTime;			// 가입시각
	private Date 	delTime;			// 탈퇴시각
	
	private Grade	grade;
	
	public String getSeq() {
		return this.seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPass() {
		return this.pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJumin_no() {
		return this.jumin_no;
	}
	public void setJumin_no(String jumin_no) {
		this.jumin_no = jumin_no;
	}
	public String getCompany() {
		return this.company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGrade_seq() {
		return this.grade_seq;
	}
	public void setGrade_seq(String grade_seq) {
		this.grade_seq = grade_seq;
	}
	public String getActive_yn() {
		return this.active_yn;
	}
	public void setActive_yn(String active_yn) {
		this.active_yn = active_yn;
	}
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getDelTime() {
		return this.delTime;
	}
	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}
	public Grade getGrade() {
		return this.grade;
	}
	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	
}
