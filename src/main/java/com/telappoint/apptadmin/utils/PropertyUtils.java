package com.telappoint.apptadmin.utils;
/**
 * 
 * @author Murali G
 *
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertyUtils {
	
	private static final Log logger = LogFactory.getLog(PropertyUtils.class);

	private static Map<String, Properties> propsMap = new HashMap<String, Properties>();
	public static final String ADMIN_APPT_PROPFILE = "apptadmin.properties";
	public static final String ADMIN_APPT_PROPFILE_FILE_PATH = "/apps/properties/apptadmin.properties";
	public static final String MAIL_PROPFILE = "mail.properties";
	public static final String MAIL_PROPFILE_FILE_PATH = "/apps/properties/mail.properties";
	public static final String APPLICATION_MESSAGES_FILE_PATH = "/apps/properties/application_resources.properties";
	public static final String APPLICATION_MESSAGES_FILE = "application_resources.properties";

	public static InputStream getResourceAsStream(String fileName) throws Exception {
		InputStream propsIn = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		if (propsIn == null) {
			propsIn = PropertyUtils.class.getResourceAsStream(fileName);
		}
		if (propsIn == null) {
			propsIn = ClassLoader.getSystemResourceAsStream(fileName);
		}
		if (propsIn == null) {
			logger.error(fileName + " not found");
			throw new Exception(fileName + " file is not found");
		}
		return propsIn;
	}
	
	
	public static Properties getProperties(String fileNameWithPath,String fileName,String propMapKey) {
		Properties properties = new Properties();
		if (propsMap.get(propMapKey) == null) {
			InputStream is = null;
			try {
				is = new FileInputStream(fileNameWithPath);
				properties.load(is);
			} catch (Exception e) {
				logger.error("Error:" + e, e);
				try {
					is = getResourceAsStream(fileName);
					properties.load(is);
				} catch (Exception e1) {
					logger.error("Error:" + e1, e1);
				}
			}finally{
				if(is!=null){
					try {
						is.close();
					} catch (IOException e) {
						logger.error("Error:" + e, e);
					}
				}
			}
			propsMap.put(propMapKey, properties);
		} else {
			properties = propsMap.get(propMapKey);
		}
		return properties;
	}
			
	public static Properties getApptProperties() {
		return getProperties(ADMIN_APPT_PROPFILE_FILE_PATH,ADMIN_APPT_PROPFILE,"adminApptProperties");
	}
	
	public static Properties getMailProperties() {
		return getProperties(MAIL_PROPFILE_FILE_PATH,MAIL_PROPFILE,"mailProperties");
	}

	public static Properties getAppMessagesProperties() {
		return getProperties(APPLICATION_MESSAGES_FILE_PATH,APPLICATION_MESSAGES_FILE,"applicationMessagesProperties");
	}
	
	public static <T> T getPropertyFromAdminApptProp(String propKey,Class<T> returnType,Object defaultVal){
		try{
			Properties properties = getApptProperties();
			return returnType.cast(properties.getProperty(propKey));
		}catch (Exception e) {		
			logger.error("Exception ::  ",e);
		}
		return returnType.cast(defaultVal);
	}
	
	public static String getApptAdminRestServiceEndPointURL() throws IOException {
		Properties properties = getApptProperties();
		return (properties.get("APPT_ADMIN_RESTWS_ENDPOINT_URL") !=null)?(String)properties.get("APPT_ADMIN_RESTWS_ENDPOINT_URL") : "";
	}
	
	public static String getMailProperty(String key) throws IOException {
		Properties properties = getMailProperties();
		return (properties.get(key) !=null)?(String)properties.get(key) : "";
	}

	public static String getMessageForKey(final String key) throws IOException {
		final Properties properties = getAppMessagesProperties();
		return (properties.get(key) !=null)?(String)properties.get(key) : "";
	}
}
