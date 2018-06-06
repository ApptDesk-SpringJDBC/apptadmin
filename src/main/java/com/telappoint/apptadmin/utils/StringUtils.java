/**
 * 
 */
package com.telappoint.apptadmin.utils;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Murali G
 * 
 */
public class StringUtils {

	private static final Log logger = LogFactory.getLog(StringUtils.class);

	public static <T> String concatWithSeparator(List<T> list, String separator) {
		StringBuilder idStr = new StringBuilder();
		boolean initial = true;
		for (T t : list) {
			if (initial==false) {
				idStr.append(separator);				
			} else {
				initial = false;
			}
			idStr.append(t);
		}
		return idStr.toString();
	}

	public static boolean isEmpty(String input) {
		return input == null || input.trim().length() == 0;
	}

	public static boolean isNotEmpty(String input) {
		return !isEmpty(input);
	}
	
	public static <T> T getDataIfNotEmptyOtherwiseGetDefaultValue(String data,Class<T> returnType,Object defaultVal){
		try{
			return isNotEmpty(data) ? returnType.cast(data) : returnType.cast(defaultVal);
		}catch (Exception e) {		
			logger.error("Exception ::  ",e);
		}
		return returnType.cast(defaultVal);
	}
}
