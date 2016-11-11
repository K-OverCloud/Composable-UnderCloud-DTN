package com.netmng.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

public class StrUtil {
	
	public static String PATTERN_SCRIPT = "<script[^>]*>[\\w|\\t|\\r|\\W]*?</script>*";
	public static boolean bSysPrint = true;
	
	public static String nullToStr(String str){
		return str != null ? str : "";
	}
	
	public static String strToNull(String str){
		return str != "" ? str : null;
	}
	
	public static String emptyToStr(String str){
		return str.replace(" ", "");
	}
	
	public static String textToDbformat(String str){
		return str.replace("\\", "\\\\");
	}
	
	public static String textPattern(String pattern, String str) {
		return "정상";
	}
	
	public static String twtFormToBasic(String twtForm){
		
		Map<String, String> monthMap = new HashMap<String, String>();
		monthMap.put("Jan", "01");
		monthMap.put("Feb", "02");
		monthMap.put("Mar", "03");
		monthMap.put("Apr", "04");
		monthMap.put("May", "05");
		monthMap.put("Jun", "06");
		monthMap.put("Jul", "07");
		monthMap.put("Aug", "08");
		monthMap.put("Sept", "09");
		monthMap.put("Oct", "10");
		monthMap.put("Nov", "11");
		monthMap.put("Dec", "12");
		
		String[] temp = twtForm.split(" ");
		//String month = monthMap.get(temp[1]).toString();
		String[] time = temp[3].split(":");
		
		StringBuffer returnStr = new StringBuffer();
		returnStr.append(temp[5])
				.append("-")
				.append(monthMap.get(temp[1]).toString())
				.append("-")
				.append(temp[2].length() == 2 ? temp[2] : "0" + temp[2])
				.append(" ")
				.append(time[0])
				.append(":")
				.append(time[1])
				.append(":")
				.append(time[2]);
		return returnStr.toString();
	}
	
	public static Date transformDate(String str, String[] pattern) {
		try {
			return DateUtils.parseDate(str, pattern);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static String transformString(Date date, String pattern) {
		return DateFormatUtils.format(date, pattern);
	}
	
	public static void sysPrint(String str){
		if(bSysPrint){
			System.out.println("=====>> "+str);
		}
	}
	
	public static String[] split(String str, String separator){
		if(str!=null && !str.equals("")){
			int iCnt = countSubstring(str, separator);
			String[] arrS = new String[iCnt+1];
			int iS = -1;
			int iE = 0;
			for(int i=0; iE >= 0 && i<arrS.length; i++){
				iE = str.indexOf(separator, iS+1);
				if(iE>0){
					arrS[i] = str.substring(iS+1, iE);
				}else{
					arrS[i] = str.substring(iS+1, str.length());
				}
				iS = iE;
			}
			return arrS;
		}else{
			return null;
		}
	}
	
	public static int countSubstring(String sStr, String sSub) {
		int iCnt	= 0;
		int index	= 0;
		while( (index = sStr.indexOf(sSub, index)) > 0 ) {
			iCnt++;
			index += sSub.length();
		}
		return iCnt;
	}
	
	public static ArrayList<Integer> chgStrToNumRange(String s){
		ArrayList<Integer> list = new ArrayList();
		if(s!=null && !s.equals("")){
			String[] arrS = split(s, ",");
	    	for(int i=0; i<arrS.length; i++){
	    		String sSplit = arrS[i].trim();
	    		if(sSplit.indexOf("-") != -1){	//범위
	    			int iSplit1 = Integer.parseInt(split(sSplit, "-")[0]);
	    			int iSplit2 = Integer.parseInt(split(sSplit, "-")[1]);
	    			for(int s1=iSplit1; s1<=iSplit2; s1++){
	    				list.add(s1);
	    			}
	    		}else{	//단일 숫자
	    			int iSplit = Integer.parseInt(sSplit);
	    			list.add(iSplit);
	    		}
	    	}
		}
    	return list;
	}
}
