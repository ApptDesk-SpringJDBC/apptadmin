package com.telappoint.apptadmin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.telappoint.apptadmin.common.controller.ApptAdminHelperController;
import com.telappoint.apptadmin.constants.ErrorCodesConstants;
import com.telappoint.apptadmin.constants.JspPageNameConstants;
import com.telappoint.apptadmin.form.ErrorBean;
import com.telappoint.apptadmin.form.HomeBean;
import com.telappoint.apptadmin.model.BaseResponse;
import com.telappoint.apptadmin.model.Resource;
import com.telappoint.apptadmin.model.ResourceResponse;
import com.telappoint.apptadmin.service.ApptAdminService;
import com.telappoint.apptadmin.utils.AdminUtils;
import com.telappoint.apptadmin.utils.ApptAdminCacheComponent;


@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class ResourceController extends ApptAdminHelperController { 
	
	private Logger logger = Logger.getLogger(ResourceController.class);
	
	@Autowired
	private ApptAdminService apptAdminService;
	@Autowired
	private ApptAdminCacheComponent	apptAdminCacheComponent;
	
	@RequestMapping(value="resources", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView showResourcesPage(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.APPTADMINDESK_SHOW_RESOURCES_PAGE;		
		ModelAndView modelView = new ModelAndView();	
		try{
			modelView.addObject("resourceResponse",getResourceListMap(apptAdminService.getAllResourcesBasicData(homeBean)));
			modelView.addObject("dynamicFieldDisplayData",apptAdminCacheComponent.getDynamicFieldDisplayData(homeBean,"resource"));
			modelView.addObject("resourceTitle",apptAdminService.getResourceTitleList(homeBean));
			//modelView.addObject("resourceType",apptAdminService.getResourceTypeList(homeBean));
			modelView.addObject("resourcePrefix",apptAdminService.getResourcePrefixList(homeBean));
			populateDisplayNames(homeBean, modelView);
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_RESOURCES_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(homeBean.getUserLoginResponse().getClientCode(),APPCODE,homeBean.getTransId(), 
					ErrorCodesConstants.ERROR_CODE_RESOURCES_SHOW.getPage(), 
					ErrorCodesConstants.ERROR_CODE_RESOURCES_SHOW.getErrorCode(), 
					ErrorCodesConstants.ERROR_CODE_RESOURCES_SHOW.getDescription());
			modelView.addObject("errorBean", errorBean);
			targetPage = JspPageNameConstants.APPTADMINDESK_ERROR_PAGE;
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"showResourcesPage", request,e);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	@RequestMapping(value="saveResource", method = RequestMethod.POST)
	public @ResponseBody String saveResource(@ModelAttribute("homeBean") HomeBean homeBean,@ModelAttribute Resource resource, HttpServletRequest request) throws Exception {
		BaseResponse baseResponse = null;
		try{			
			baseResponse = apptAdminService.saveResource(homeBean, resource);
			getUpdatedBaseResponse(baseResponse,apptAdminCacheComponent.getDisplayNames(homeBean).getResourceName()+" details saved sucessfully!","Error while saving "+apptAdminCacheComponent.getDisplayNames(homeBean).getResourceName());
		}catch (Exception e) {
			baseResponse = new BaseResponse();
			baseResponse.setStatus(false);
			baseResponse.setMessage("Error while saving "+apptAdminCacheComponent.getDisplayNames(homeBean).getResourceName());
			logger.error(ErrorCodesConstants.ERROR_CODE_RESOURCES_SAVE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"saveResource", request,e);
		}
	    return AdminUtils.getJSONDataStr(baseResponse);  
	}
	
	@RequestMapping(value="getResourceById",method = RequestMethod.GET)
	public @ResponseBody String getResourceById(@ModelAttribute("homeBean") HomeBean homeBean, @RequestParam("id") String id, HttpServletRequest request) throws Exception {
		Resource resource = null;	
		try{
			resource = apptAdminService.getCompleteResourceDataById(homeBean,id);
		} catch (Exception e) {
			resource = new Resource();	
			logger.error(ErrorCodesConstants.ERROR_CODE_RESOURCES_BY_ID.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getResourceById", request,e);
		}
		return AdminUtils.getJSONDataStr(resource); 
	}
	
	@RequestMapping(value="updateResource", method = RequestMethod.POST)
	public @ResponseBody String updateResource(@ModelAttribute("homeBean") HomeBean homeBean,@ModelAttribute Resource resource, HttpServletRequest request) throws Exception {
		BaseResponse baseResponse = null;
		try{	
			baseResponse = apptAdminService.updateResource(homeBean,resource);
			getUpdatedBaseResponse(baseResponse,apptAdminCacheComponent.getDisplayNames(homeBean).getResourceName()+" details updated sucessfully!","Error while updating "+apptAdminCacheComponent.getDisplayNames(homeBean).getResourceName());
		} catch (Exception e) {
			baseResponse = new BaseResponse();
			baseResponse.setStatus(false);
			baseResponse.setMessage("Error while updating Resource");
			logger.error(ErrorCodesConstants.ERROR_CODE_RESOURCES_UPDATE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"updateResource", request,e);
		}
	    return AdminUtils.getJSONDataStr(baseResponse);  
	}
	
	@RequestMapping(value="deleteResource",method = RequestMethod.GET)
	public @ResponseBody String deleteResource(@ModelAttribute("homeBean") HomeBean homeBean, @RequestParam("id") String id, HttpServletRequest request) throws Exception {
		BaseResponse baseResponse = null;	
		try{
			baseResponse = apptAdminService.deleteResource(homeBean,id);
			getUpdatedBaseResponse(baseResponse,apptAdminCacheComponent.getDisplayNames(homeBean).getResourceName()+" details deleted sucessfully!","Error while deleting "+apptAdminCacheComponent.getDisplayNames(homeBean).getResourceName());
		}catch (Exception e) {
			baseResponse = new BaseResponse();
			baseResponse.setStatus(false);
			baseResponse.setMessage("Error while deleting Resource");	
			logger.error(ErrorCodesConstants.ERROR_CODE_RESOURCES_DELETE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"deleteResource", request,e);
		}
		return AdminUtils.getJSONDataStr(baseResponse); 
	}
	
	@RequestMapping(value="unDeleteResource",method = RequestMethod.GET)
	public @ResponseBody String unDeleteResource(@ModelAttribute("homeBean") HomeBean homeBean, @RequestParam("id") String id, HttpServletRequest request) throws Exception {
		BaseResponse baseResponse = null;		
		try{
			baseResponse = apptAdminService.unDeleteResource(homeBean,id);
			getUpdatedBaseResponse(baseResponse,apptAdminCacheComponent.getDisplayNames(homeBean).getResourceName()+" details undeleted sucessfully!","Error while undeleting "+apptAdminCacheComponent.getDisplayNames(homeBean).getResourceName());
		}catch (Exception e) {
			baseResponse = new BaseResponse();
			baseResponse.setStatus(false);
			baseResponse.setMessage("Error while undeleting "+apptAdminCacheComponent.getDisplayNames(homeBean).getResourceName());	
			logger.error(ErrorCodesConstants.ERROR_CODE_RESOURCES_UN_DELETE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"unDeleteResource", request,e);
		}
		return AdminUtils.getJSONDataStr(baseResponse); 
	}
	
	private ResourceResponse getResourceListMap(ResourceResponse resourceResponse){
		Map<String,List<Resource>> resourceListMap = new HashMap<String,List<Resource>>();
		if(resourceResponse!=null && resourceResponse.getResourceList()!=null && resourceResponse.getResourceList().size()>0){
			for(Resource resource : resourceResponse.getResourceList()){
				if(resource!=null){
					List<Resource> resourceList = null;
					if(resourceListMap.containsKey(resource.getLocationName())) {
						resourceList = resourceListMap.get(resource.getLocationName());
					} else {
						resourceList = new ArrayList<Resource>();
					}
					resourceList.add(resource);
					resourceListMap.put(resource.getLocationName(), resourceList);
				}
			}
			resourceResponse.setResourceListMap(resourceListMap);
		}
		return resourceResponse;
	}
 }
