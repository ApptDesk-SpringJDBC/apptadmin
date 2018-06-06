package com.telappoint.apptadmin.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.telappoint.apptadmin.form.HomeBean;
import com.telappoint.apptadmin.model.DisplayNames;
import com.telappoint.apptadmin.model.DynamicFieldDisplayResponse;
import com.telappoint.apptadmin.model.PrivilegedPageNamesResponse;
import com.telappoint.apptadmin.service.ApptAdminService;

@Component
public class ApptAdminCacheComponent {

	@Autowired
	private ApptAdminService apptAdminService;
	
	private Logger logger = Logger.getLogger(ApptAdminCacheComponent.class);

	private static final Object lock = new Object();
	private static boolean enableCache = true;
	
	private static Map<String,DisplayNames>  displayNamesCacheMap = new HashMap<String,DisplayNames>(); //{key = client_code, value= DisplayNames}
	private static Map<String,PrivilegedPageNamesResponse> privilegedPageNamesCacheMap = new HashMap<String,PrivilegedPageNamesResponse>(); //{key = client_code, value= {key = accessLevel, value= List of page Names.} }
	private static Map<String,Map<String,DynamicFieldDisplayResponse>>  dynamicFieldsCacheMap = new HashMap<String,Map<String,DynamicFieldDisplayResponse>>(); //{key = client_code, value= {key = page, value= DynamicFieldDisplayResponse}}
	
	public DisplayNames getDisplayNames(HomeBean homeBean) {
		DisplayNames displayNames = null;
		try{
			if(enableCache){
				displayNames = displayNamesCacheMap.get(getCacheMapKey(homeBean));
			}
			if(displayNames==null){
				synchronized (lock) {
					displayNames = apptAdminService.getDisplayNames(homeBean);
				}	
				if(enableCache){
					displayNamesCacheMap.put(getCacheMapKey(homeBean),displayNames);
				}
				logger.debug("###################  DisplayNames Data read from  --  DB --  ###################");
			}else{
				logger.debug("###################  DisplayNames Data read from  --  CACHE --  ###################");
			}			
		}catch(Exception e){
			logger.error("Error :: "+e.getMessage(),e);
		}
		return displayNames;		
	}
	
	public List<String> getAccessPrivilegedPageNames(HomeBean homeBean) {
		List<String> privilegedPageNames = null;
		PrivilegedPageNamesResponse privilegedPageNamesResponse = null;
		try{
			if(enableCache){
				privilegedPageNamesResponse = privilegedPageNamesCacheMap.get(getCacheMapKey(homeBean));
				if(privilegedPageNamesResponse!=null && privilegedPageNamesResponse.getPrevilegePageNames()!=null) {
					privilegedPageNames = privilegedPageNamesResponse.getPrevilegePageNames().get(homeBean.getUserLoginResponse().getAccessLevel());
				}
			}
			if(privilegedPageNames==null){
				synchronized (lock) {					
					privilegedPageNamesResponse = apptAdminService.getAccessPrivilegedPageNames(homeBean);
					if(privilegedPageNamesResponse!=null && privilegedPageNamesResponse.getPrevilegePageNames()!=null) {
						privilegedPageNames = privilegedPageNamesResponse.getPrevilegePageNames().get(homeBean.getUserLoginResponse().getAccessLevel().trim());
					}
					if(enableCache){
						privilegedPageNamesCacheMap.put(getCacheMapKey(homeBean),privilegedPageNamesResponse);
					}
				}
				logger.debug("###################  PrivilegedPageNames Data read from  --  DB --  ###################");
			}else{
				logger.debug("###################  PrivilegedPageNames Data read from  --  CACHE --  ###################");
			}			
		} catch(Exception e){
			logger.error("Error :: "+e.getMessage(),e);
		}
		return privilegedPageNames;		
	}

	public DynamicFieldDisplayResponse getDynamicFieldDisplayData(HomeBean homeBean,String page) {
		Map<String,DynamicFieldDisplayResponse> dynamicFields = null;
		DynamicFieldDisplayResponse dynamicFieldDisplayResponse = null;
		try{
			if(enableCache){
				dynamicFields = dynamicFieldsCacheMap.get(getCacheMapKey(homeBean));
				if(dynamicFields!=null && dynamicFields.containsKey(page)) {
					dynamicFieldDisplayResponse = dynamicFields.get(page);
				}
			}
			if(dynamicFieldDisplayResponse==null){
				synchronized (lock) {					
					dynamicFieldDisplayResponse = apptAdminService.getDynamicFieldDisplayData(homeBean,page);
					dynamicFields = new HashMap<String,DynamicFieldDisplayResponse>();
					dynamicFields.put(page,dynamicFieldDisplayResponse);
					if(enableCache){
						dynamicFieldsCacheMap.put(getCacheMapKey(homeBean),dynamicFields);
					}
				}
				logger.debug("###################  DynamicFieldDisplayResponse Data read from  --  DB --  ###################");
			}else{
				logger.debug("###################  DynamicFieldDisplayResponse Data read from  --  CACHE --  ###################");
			}			
		} catch(Exception e){
			e.printStackTrace();
			logger.error("Error :: "+e.getMessage(),e);
		}
		return dynamicFieldDisplayResponse;		
	}

	public String getCacheMapKey(HomeBean homeBean){
		return homeBean.getUserLoginResponse().getClientCode();
	}

	public boolean clearCache() {
		try{
			synchronized (lock) {
				displayNamesCacheMap.clear();
				privilegedPageNamesCacheMap.clear();
			}
			logger.debug("###################  Cleared Cache Data  ###################");
			return true;	
		}catch(Exception e){
			logger.error("Error :: "+e.getMessage(),e);
			return false;
		}			
	}
	
	public boolean clearCache(String clientCode) {
		try{
			synchronized (lock) {
				displayNamesCacheMap.remove(clientCode);
				privilegedPageNamesCacheMap.remove(clientCode);			
			}
			logger.debug("###################  Cache Data Cleared for clientCode ###################  "+clientCode);
			return true;	
		}catch(Exception e){
			logger.error("Error :: "+e.getMessage(),e);
			return false;
		}			
	}
}
