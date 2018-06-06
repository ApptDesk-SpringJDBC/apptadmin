package com.telappoint.apptadmin.controller;

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
import com.telappoint.apptadmin.model.AdminLogin;
import com.telappoint.apptadmin.model.BaseResponse;
import com.telappoint.apptadmin.service.ApptAdminService;
import com.telappoint.apptadmin.utils.AdminUtils;
import com.telappoint.apptadmin.utils.ApptAdminCacheComponent;
import com.telappoint.apptadmin.utils.PasswordValidator;


@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class UserController extends ApptAdminHelperController { 
	
	private Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private ApptAdminService apptAdminService;
	@Autowired
	private ApptAdminCacheComponent	apptAdminCacheComponent;
	
	@RequestMapping(value="users", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView showUsersPage(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.APPTADMINDESK_SHOW_USERS_PAGE;		
		ModelAndView modelView = new ModelAndView();	
		try{
			modelView.addObject("users",apptAdminService.getUsers(homeBean));
			modelView.addObject("dynamicFieldDisplayData",apptAdminCacheComponent.getDynamicFieldDisplayData(homeBean,"user"));
			modelView.addObject("resourceResponse",apptAdminService.getAllResourcesBasicData(homeBean));
			modelView.addObject("locationResponse",apptAdminService.getAllLocationsBasicData(homeBean));
			populateDisplayNames(homeBean,modelView);
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_USERS_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(homeBean.getUserLoginResponse().getClientCode(),APPCODE,homeBean.getTransId(), 
					ErrorCodesConstants.ERROR_CODE_USERS_SHOW.getPage(), 
					ErrorCodesConstants.ERROR_CODE_USERS_SHOW.getErrorCode(), 
					ErrorCodesConstants.ERROR_CODE_USERS_SHOW.getDescription());
			modelView.addObject("errorBean", errorBean);
			targetPage = JspPageNameConstants.APPTADMINDESK_ERROR_PAGE;
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"showUsersPage", request,e);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	@RequestMapping(value="saveUser", method = RequestMethod.POST)
	public @ResponseBody String saveUser(@ModelAttribute("homeBean") HomeBean homeBean,@ModelAttribute AdminLogin adminLogin, HttpServletRequest request) throws Exception {
		BaseResponse baseResponse = null;
		try{			
			baseResponse = apptAdminService.saveUser(homeBean,adminLogin);
			getUpdatedBaseResponse(baseResponse,"User details saved sucessfully!","Error while saving User");
		}catch (Exception e) {
			baseResponse = new BaseResponse();
			baseResponse.setStatus(false);
			baseResponse.setMessage("Error while saving User");
			logger.error(ErrorCodesConstants.ERROR_CODE_USERS_SAVE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"saveUser", request,e);
		}
	    return AdminUtils.getJSONDataStr(baseResponse);  
	}
	
	@RequestMapping(value="getUserById",method = RequestMethod.GET)
	public @ResponseBody String getResourceById(@ModelAttribute("homeBean") HomeBean homeBean, @RequestParam("id") String id, HttpServletRequest request) throws Exception {
		AdminLogin adminLogin = null;	
		try{
			adminLogin = apptAdminService.getUserById(homeBean,id);
		}catch (Exception e) {
			adminLogin = new AdminLogin();	
			logger.error(ErrorCodesConstants.ERROR_CODE_USERS_BY_ID.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getUserById", request,e);
		}
		return AdminUtils.getJSONDataStr(adminLogin); 
	}
	
	@RequestMapping(value="updateUser", method = RequestMethod.POST)
	public @ResponseBody String updateUser(@ModelAttribute("homeBean") HomeBean homeBean,@ModelAttribute AdminLogin adminLogin, HttpServletRequest request) throws Exception {
		BaseResponse baseResponse = null;
		try{	
			baseResponse = apptAdminService.updateUser(homeBean,adminLogin);
			getUpdatedBaseResponse(baseResponse,"Resource details updated sucessfully!","Error while updating User");
		}catch (Exception e) {
			baseResponse = new BaseResponse();
			baseResponse.setStatus(false);
			baseResponse.setMessage("Error while updating User");
			logger.error(ErrorCodesConstants.ERROR_CODE_USERS_UPDATE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"updateUser", request,e);
		}
	    return AdminUtils.getJSONDataStr(baseResponse);  
	}
	
	@RequestMapping(value="deleteUser",method = RequestMethod.GET)
	public @ResponseBody String deleteUser(@ModelAttribute("homeBean") HomeBean homeBean, @RequestParam("id") String id, HttpServletRequest request) throws Exception {
		BaseResponse baseResponse = null;	
		try{
			baseResponse = apptAdminService.deleteResource(homeBean,id);
			getUpdatedBaseResponse(baseResponse,"Resource details deleted sucessfully!","Error while deleting User");
		}catch (Exception e) {
			baseResponse = new BaseResponse();
			baseResponse.setStatus(false);
			baseResponse.setMessage("Error while deleting User");	
			logger.error(ErrorCodesConstants.ERROR_CODE_USERS_DELETE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"deleteUser", request,e);
		}
		return AdminUtils.getJSONDataStr(baseResponse); 
	}
	
	@RequestMapping(value = "validatePassword", method = RequestMethod.GET)
	public @ResponseBody String validatePassword(@RequestParam("username") String username,@RequestParam("password") String password, @ModelAttribute("homeBean") HomeBean homeBean,
			HttpServletRequest request) throws Exception {	
		String response = "";
		try {
			String passwordComplexity = apptAdminService.getPasswordComplexity(homeBean).getComplexityValue();
			response = PasswordValidator.validatePassword(passwordComplexity, username, password);
		} catch (Exception e) {
			response = "Error while validating Password";	
			logger.error(ErrorCodesConstants.ERROR_CODE_USERS_DELETE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"validatePassword", request,e);
		}
		return response;
	}
	
	@RequestMapping(value = "validateUserName", method = RequestMethod.GET)
	public @ResponseBody String validateUserName(@RequestParam("userName") String userName,@RequestParam("userId") String userId, 
			@ModelAttribute("homeBean") HomeBean homeBean,HttpServletRequest request) throws Exception {	
		BaseResponse baseResponse = null;
		try{	
			baseResponse = apptAdminService.validateUserName(homeBean, userId, userName);
			getUpdatedBaseResponse(baseResponse,"User Name is valid!","User Name already exists!");
		}catch (Exception e) {
			baseResponse = new BaseResponse();
			baseResponse.setStatus(false);
			baseResponse.setMessage("Error while validating User Name!");
			logger.error(ErrorCodesConstants.ERROR_CODE_VALIDATE_USER.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"validateUserName", request,e);
		}
	    return AdminUtils.getJSONDataStr(baseResponse);  
	}
 }
