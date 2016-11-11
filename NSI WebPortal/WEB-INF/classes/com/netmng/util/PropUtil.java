package com.netmng.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.netmng.svc.adm.AdmService;
import com.netmng.vo.MenuAuth;
import com.netmng.vo.User;

public class PropUtil {
	
	String sPropPath = "src/config/i18n/netmng.properties";
	String totalPath = "";
	//private static Map<String, String> mGlobalVal = new HashMap();
	private static Map<String, String> mGlobalVal;
	
	public PropUtil(){
		this.totalPath = this.getPropPath();
	}
	
	public PropUtil(String sLang){
		this.sPropPath = "src/config/i18n/messages_"+sLang+".properties";
		this.totalPath = this.getPropPath();
	}
	
	public String getProp(String sKey){
		Properties properties = null;
		if(properties==null)
			properties = new Properties();
		FileInputStream file = null;
		try {
			//file = new FileInputStream("C:/project/netmng/src/netmng_v2/src/config/i18n/netmng.properties");
			file = new FileInputStream(this.totalPath);
			properties.load(file);
			return properties.getProperty(sKey);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*// 현재클래스의 절대경로 위치로 프로젝트내에 properties 의 경로를 끼워맞춘다.
	public String getPropPath(){
	   //String totalPath = "";
	   try {
	      //String sapPath = "src/config/i18n/netmng.properties";
	      File mapLoader = new File(this.getClass().getResource(this.getClass().getSimpleName()+".class").getPath());
	      String classPath = mapLoader.getParent();
	      // classes 폴더명의 위치를 알아낸다
	      //int positionNo = classPath.indexOf("classes");
	      int positionNo = classPath.indexOf("build");
	      classPath = classPath.substring(0, positionNo);                    
	      // 현재 클래스파일의 위치를 기준으로 properties 경로를 맞춘후 파일객체를 통해서 로컬경로를 알아온다.
	      File totalPathFile = new File(classPath + this.sPropPath);
	      //totalPath = totalPathFile.getPath();
	      return totalPathFile.getPath();
	   } catch (RuntimeException e) {
	      e.printStackTrace();
	      return null;
	   }
	   //return totalPath;
	}*/
	// 현재클래스의 절대경로 위치로 프로젝트내에 properties 의 경로를 끼워맞춘다.
	public String getPropPath(){
	   try {
	      // 현재 클래스파일의 위치를 기준으로 properties 경로를 맞춘후 파일객체를 통해서 로컬경로를 알아온다.
	      File totalPathFile = new File(this.getRootPath() + this.sPropPath);
	      //totalPath = totalPathFile.getPath();
	      return totalPathFile.getPath();
	   } catch (RuntimeException e) {
	      e.printStackTrace();
	      return null;
	   }
	   //return totalPath;
	}
	
	public String getRootPath(){
	   try {
	      //String sapPath = "src/config/i18n/netmng.properties";
	      File mapLoader = new File(this.getClass().getResource(this.getClass().getSimpleName()+".class").getPath());
	      String classPath = mapLoader.getParent();
	      // classes 폴더명의 위치를 알아낸다
	      //int positionNo = classPath.indexOf("classes");
	      int positionNo = classPath.indexOf("build");
	      classPath = classPath.substring(0, positionNo);                    
	      
	      return classPath;
	   } catch (RuntimeException e) {
	      e.printStackTrace();
	      return null;
	   }
	   //return totalPath;
	}
	
	public static void setGlobalVal(String sKey, String sVal){
		if(mGlobalVal==null){
			System.out.println("=====>> PopUtil.setGlobalVal() : mGlobalVal is null, create new HashMap()");
			mGlobalVal = new HashMap();
		}
		mGlobalVal.put(sKey, sVal);
	}
	
	public static String getGlobalVal(String sKey){
		if(mGlobalVal==null){
			System.out.println("=====>> PropUtil.getGlobalVal() : mGlobalVal is null, create new HashMap()");
			mGlobalVal = new HashMap();
		}
		if(sKey!=null && !sKey.equals("")){
			String sRtn = mGlobalVal.get(sKey);
			System.out.println("=====>> PropUtil.getGlobalVal() : 1)sRtn("+sKey+")="+sRtn);
			mGlobalVal.remove(sKey);
			System.out.println("=====>> PropUtil.getGlobalVal() : 2)sRtn("+sKey+")="+sRtn);
			return sRtn;
		}else{
			return null;
		}
	}
}
