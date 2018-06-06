package com.telappoint.apptadmin.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.telappoint.apptadmin.form.HomeBean;

/**
 * @author Murali G
 * 
 */
public class AdminUtils {
	
	private static Logger logger = Logger.getLogger(AdminUtils.class);
	
	public static String getProtocalHostPort(HttpServletRequest request){
		try {
			StringBuffer url = request.getRequestURL(); // http://localhost:8081/adminappt/home.html
			String uri = request.getRequestURI(); // /adminappt/home.html
			return url.substring(0,url.toString().length()-uri.length()); // http://localhost:8081
		}catch(Exception e){
			return "";
		}
	}
	
	public static Object getPropertyValue(Object object,String fieldName) throws NoSuchFieldException {
		try {
			BeanInfo info = Introspector.getBeanInfo(object.getClass());
			for (PropertyDescriptor pd : info.getPropertyDescriptors())
				if (pd.getName().equals(fieldName)) {
					Method getter = pd.getReadMethod();
					if(getter != null) {
						getter.setAccessible(true);
						return getter.invoke(object, null);
					}
					
				}
		} catch (Exception e) {
			throw new NoSuchFieldException(object.getClass() + " has no field " + fieldName);
		}
		return "";
	}

	public static void setPropertyValue(Object object, String propertyName, Object propertyValue) throws Exception {
		try {
			BeanInfo bi = Introspector.getBeanInfo(object.getClass());
			PropertyDescriptor pds[] = bi.getPropertyDescriptors();
			for (PropertyDescriptor pd : pds) {
				if (pd.getName().equals(propertyName)) {
					Method setter = pd.getWriteMethod();
					if (setter != null) {
						setter.invoke(object, new Object[] { propertyValue });
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static String getRequestParameters(HttpServletRequest request) {
		boolean initail = true;
		StringBuilder requestData = new StringBuilder();
		try{
			Enumeration paramNames = request.getParameterNames();
			while(paramNames.hasMoreElements()) {
				String paramName = (String)paramNames.nextElement();
				String[] paramValues = request.getParameterValues(paramName);
				if(!initail){
					requestData.append(" , ");
				}
				requestData.append(paramName).append(" : ").append(paramValues[0]);
				initail = false;
			}
		}catch(Exception e){
			return "Error while getting Request Parameters";
		}
		return requestData.toString();
	}
	
	public static String populateBasicRequestParamsDetails(HomeBean sessionData,String endPointUrl) throws IOException {
		String url = PropertyUtils.getApptAdminRestServiceEndPointURL() + endPointUrl;
		url = url.replaceAll("@clientCodeParam@",sessionData.getUserLoginResponse().getClientCode());
		/*url = url.replaceAll("@deviceParam@",ApptAdminContants.ONLINE.getValue());
		url = url.replaceAll("@transIdParam@", sessionData.getTransId());*/
		return url;
	}
	
	public static <T> T getDataFromJSON(Object data,Class<T> clazz){
		try{
			Gson gson = new GsonBuilder().create();
			return gson.fromJson(gson.toJson(data),clazz);
		}catch (Exception e) {
			logger.error("Error ::: ", e);
			return null;
		}
	}
	
	public static String getDataFromJSON(Object data){
		try{
			return new GsonBuilder().create().toJson(data);
		}catch (Exception e) {
			logger.error("Error ::: ", e);
			return null;
		}
	}
	
	public static <T> List<T> getListDataFromJSON(Object data,Class<T[]> clazzArr){
		try{
			Gson gson = new GsonBuilder().create();			
			return Arrays.asList(gson.fromJson(gson.toJson(data),clazzArr));
		}catch (Exception e) {
			return null;
		}
	}
		
	public static <K,V> Map<K,V> getMapDataFromJSON(Object data,Class<K> keyClazz,Class<V> valClazz){
		try{
			Gson gson = new GsonBuilder().create();
			String jsonData = gson.toJson(data);
			Map<K,V> response = gson.fromJson(jsonData, new TypeToken<HashMap<K,V>>() {
				private static final long serialVersionUID = 1L;
			}.getType());
			
			return response;
		}catch (Exception e) {
			return null;
		}
	}
	
	public static <K,V> Map<K,List<V>> getMapOfListDataFromJSON(Object data,Class<K> keyClazz,Class<V> valClazz){
		try{
			Gson gson = new GsonBuilder().create();
			String jsonData = gson.toJson(data);
			Map<K,List<V>> response = gson.fromJson(jsonData, new TypeToken<HashMap<K,List<V>>>() {
				private static final long serialVersionUID = 1L;
			}.getType());
			
			return response;
		}catch (Exception e) {
			return null;
		}
	}
	
	public static String getJSONDataStr(Object data){
		try{
			Gson gson = new Gson();
			return gson.toJson(data);
		}catch (Exception e) {
			return "";
		}
	}
	
	public static Map<String, String> getBreakTimeDuration(int blockTimeInMin) {
		Map<String, String> breakTimeDurDropDownMap = new LinkedHashMap<String, String>();
		String key = null;
		String value = null;
		for (int duration = 0; duration <= 12 * 60; duration += blockTimeInMin) {
			key = String.valueOf(duration);
			if (duration > 60) {
				if (duration % 60 == 0) {
					value = String.valueOf(duration / 60) + " hours ";
				} else {
					value = String.valueOf(duration / 60) + " hour " + String.valueOf(duration % 60) + " mins";
				}
			} else if (duration < 60) {
				value = String.valueOf(duration % 60) + " mins ";

			} else {
				value = String.valueOf(duration / 60) + " hour ";

			}
			if (!breakTimeDurDropDownMap.containsKey(key)) {
				breakTimeDurDropDownMap.put(key, value);
			}
		}

		return breakTimeDurDropDownMap;
	}
	
	public static List<String> getMinsList(int blockTimeInMin) throws Exception {
		List<String> minsList = new ArrayList<String>();
		minsList.add("00");
		for (int duration = blockTimeInMin; duration < 60; duration += blockTimeInMin) {
			String mins = String.valueOf(duration % 60);
			minsList.add(mins);
		}
		return minsList;
	}
	
	public static <T> T getValue(Map<String,Object> map, String key ,Class<T> type) {
		return type.cast(map.get(key));
	}
}
