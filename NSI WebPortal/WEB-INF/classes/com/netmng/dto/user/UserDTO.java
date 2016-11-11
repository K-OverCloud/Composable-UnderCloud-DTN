package com.netmng.dto.user;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.netmng.vo.Grade;
import com.netmng.vo.User;

public class UserDTO extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1541255828358564727L;

	private String 	mode;

	//@NotNull(groups = {User.UserInsert.class}, message="com.netmng.vo.User.jumin_no")
	//@NotEmpty(groups = {User.UserInsert.class}, message="com.netmng.vo.User.jumin_no")
	//@Pattern(regexp="\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])", groups = {User.UserInsert.class}, message="com.netmng.vo.User.jumin_no.fail")
	private String jumin_no1;

	//@NotNull(groups = {User.UserInsert.class}, message="com.netmng.vo.User.jumin_no")
	//@NotEmpty(groups = {User.UserInsert.class}, message="com.netmng.vo.User.jumin_no")
	//@Pattern(regexp="[1-4]\\d{6}", groups = {User.UserInsert.class}, message="com.netmng.vo.User.jumin_no.fail")
	private String jumin_no2;
	
	@NotNull(groups = {User.MyInfoPassUpdate.class}, message="com.netmng.vo.User.newPass")
	@NotEmpty(groups = {User.MyInfoPassUpdate.class}, message="com.netmng.vo.User.newPass")
	private String 	newPass;		// 패스워드 확인
	
	@NotNull(groups = {User.UserInsert.class, User.MyInfoPassUpdate.class}, message="com.netmng.vo.User.passConfirmed")
	@NotEmpty(groups = {User.UserInsert.class, User.MyInfoPassUpdate.class}, message="com.netmng.vo.User.passConfirmed")
	private String 	passConfirmed;		// 패스워드 확인
	
	private Grade grade;
	
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getJumin_no1() {
		return this.jumin_no1;
	}
	public void setJumin_no1(String jumin_no1) {
		this.jumin_no1 = jumin_no1;
	}
	public String getJumin_no2() {
		return this.jumin_no2;
	}
	public void setJumin_no2(String jumin_no2) {
		this.jumin_no2 = jumin_no2;
	}
	public String getNewPass() {
		return this.newPass;
	}
	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}
	public String getPassConfirmed() {
		return this.passConfirmed;
	}
	public void setPassConfirmed(String passConfirmed) {
		this.passConfirmed = passConfirmed;
	}
	public Grade getGrade() {
		return this.grade;
	}
	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	
}
