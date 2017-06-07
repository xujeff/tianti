package com.jeff.tianti.common.util;

import java.sql.Timestamp;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class CommonUtils {
	
	public static String obj2Str(Object obj){
		if(obj == null){
			return "";
		}
		return obj.toString().trim();
	}
	
	public static Integer obj2Int(Object obj){
		String str = String.valueOf(obj);
		if(StringUtils.isNotBlank(str) && StringUtils.isNumeric(str)){
			try {
				return Integer.parseInt(str);
			} catch (Exception e) {
			}
		}
		return null;
	}
	
	public static Boolean obj2Boolean(Object obj){
		if(obj == null){
			return null;
		}
		if("0".equals(obj.toString().trim())){
			return false;
		}else if("1".equals(obj.toString().trim())){
			return true;
		}
		return null;
	}
	
	public static Date obj2Date(Object obj){
		Date date = null;
		if(obj != null && obj instanceof Timestamp){
			Timestamp timestamp = (Timestamp) obj;
			date = new Date(timestamp.getTime());
		}
		return date;
	}
	
	public static boolean isMobile(String mobiles){  
	  
		Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
		
		Matcher m = p.matcher(mobiles); 
		
		return m.matches();
	}

}
