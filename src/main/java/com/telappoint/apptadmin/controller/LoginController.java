package com.telappoint.apptadmin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.telappoint.apptadmin.client.contants.LoginConstants;
import com.telappoint.apptadmin.common.controller.ApptAdminHelperController;
import com.telappoint.apptadmin.constants.JspPageNameConstants;
import com.telappoint.apptadmin.form.HomeBean;
import com.telappoint.apptadmin.form.ResetPasswordTO;
import com.telappoint.apptadmin.form.UserLoginTO;
import com.telappoint.apptadmin.model.UserLoginResponse;
import com.telappoint.apptadmin.service.ApptAdminService;
import com.telappoint.apptadmin.utils.ApptAdminCacheComponent;
import com.telappoint.apptadmin.utils.PasswordValidator;

/**
 * 
 * @author Murali G
 * 
 */

@Controller
public class LoginController extends ApptAdminHelperController {
	
	private Logger logger = Logger.getLogger(LoginController.class);
	
	public final static String USERNAME_EMPTY = "Please enter your Username.<br>";
	public final static String PASSWORD_EMPTY = "Please enter your Password.<br>";
	public final static String USER_PASSWORD_INVALID = "Please enter a valid password (minimum 6 characters).<br>";
	
	public final static String APPTDESK_APP_CODE = "APPTDESK";
	
	@Autowired
	private ApptAdminService apptAdminService;
	@Autowired
	private ApptAdminCacheComponent apptAdminCacheComponent;
	
	@RequestMapping(value={"/","redirect",JspPageNameConstants.APPTADMINDESK_LOGIN_URL}, method = RequestMethod.GET)
	public ModelAndView showAdminLoginPage(HttpServletRequest request) throws Exception {
		ModelAndView modelView = new ModelAndView();
		UserLoginTO loginForm = new UserLoginTO();
		return getLoginModelAndView(loginForm,modelView, request);  
	}

	private ModelAndView getLoginModelAndView(UserLoginTO loginForm,ModelAndView modelView,HttpServletRequest request) {
		String ipAddress = request.getRemoteAddr();
		loginForm.setIpAddress(ipAddress);
		modelView.addObject("loginForm", loginForm);
		modelView.setViewName(JspPageNameConstants.APPTADMINDESK_LOGIN_PAGE);
		return modelView;
	}
	
	@RequestMapping(value = JspPageNameConstants.APPTADMINDESK_LOGIN_AUTH, method = RequestMethod.POST)
	public ModelAndView authenticate(@ModelAttribute("loginForm") UserLoginTO loginForm,HttpServletRequest request) throws Exception {
		ModelAndView modelView = new ModelAndView();
		try {
			String error = validateForm(loginForm);
			if (error != null && error != ""){
				modelView.addObject("useLoginResponse",error);
				getLoginModelAndView(loginForm,modelView, request); 
			} else {
				UserLoginResponse userLoginResponse = apptAdminService.loginAuthenticate(loginForm);
				if(userLoginResponse.isAuthStatus()){
					HomeBean homeBean =new HomeBean();
					homeBean.setUserLoginResponse(userLoginResponse);
					
					if(loginForm.isKeepMeLoggedIn()){
						request.getSession().setMaxInactiveInterval(LoginConstants.KEEP_ME_LOGGED_IN_SESSION_INTERVAL);
						homeBean.setSelectedKeepMeLoggedIn(true);
					}else{
						homeBean.setSelectedKeepMeLoggedIn(false);
					}
					
					HttpSession session = request.getSession();
					session.setAttribute("homeBean", homeBean);
					
					populateHomePageDetails(request, modelView, homeBean);
				}else {
					modelView.addObject("useLoginResponse",(userLoginResponse.getMessage() !=null && userLoginResponse.getMessage().length()>0) ? userLoginResponse.getMessage() : USER_PASSWORD_INVALID);
					getLoginModelAndView(loginForm,modelView, request); 
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error : "+((e.getMessage() !=null)?e.getMessage():""),e);
			apptAdminService.sendErrorEmail("N.A","authenticate", request,e);
			getLoginModelAndView(loginForm,modelView, request); 
		}
		return modelView;
	}
	
	public String validateForm(UserLoginTO loginForm) {
		String error = null;
		if (loginForm != null) {
			if (loginForm.getUsername().isEmpty()) {
				error = USERNAME_EMPTY;
			} else if (loginForm.getPassword().isEmpty()) {
				error = PASSWORD_EMPTY;
			} else if (loginForm.getPassword().length()<5) {
				error = USER_PASSWORD_INVALID;
			}
		}
		return error;
	}
	
	@RequestMapping(value = JspPageNameConstants.APPTADMINDESK_LOG_OUT, method = RequestMethod.GET)
    public String logOut(HttpServletRequest request, Model model) {
		String targetPage = JspPageNameConstants.APPTADMINDESK_REDIRECT+JspPageNameConstants.APPTADMINDESK_LOGIN_PAGE+JspPageNameConstants.APPTADMINDESK_HTML_EXTENSION;	
		HttpSession session = request.getSession();
        session.invalidate();
	    return targetPage; 
    }
	
	@RequestMapping(value = JspPageNameConstants.APPTADMINDESK_RESET_PASSWORD_REQUEST_TOKEN, method = RequestMethod.POST)
	public ModelAndView sendResetPasswordRequestToken(@RequestParam("usename") String usename,HttpServletRequest request) {	
		String targetPage = JspPageNameConstants.APPTADMINDESK_REDIRECT+JspPageNameConstants.APPTADMINDESK_LOGIN_PAGE+JspPageNameConstants.APPTADMINDESK_HTML_EXTENSION;
		ModelAndView modelView = new ModelAndView();	
		String hostName = request.getServerName();
		String hostport = String.valueOf(request.getServerPort());
		String applicationName = "adminnotify";
	    String contextPath = request.getContextPath();
		if(contextPath.contains("/")){
			applicationName = contextPath.substring(contextPath.indexOf("/")+1);
		}
		ResetPasswordTO resetPasswordTO = new ResetPasswordTO();
		resetPasswordTO.setUserName(usename);
		resetPasswordTO.setApplicationName(applicationName);
		resetPasswordTO.setHostName(hostName);
		resetPasswordTO.setHostport(hostport);
		String resetPasswordResponse = "" ; //adminApptService.sendResetPasswordRequestToken(resetPasswordTO); TODO : Needs to implement this method
		modelView.addObject("resetPasswordResponse",resetPasswordResponse);
		modelView.setViewName(targetPage);
	    return modelView;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = JspPageNameConstants.APPTADMINDESK_RESET_PASSWORD_REQUEST, method = RequestMethod.GET)
	public ModelAndView resetPasswordRequest(@RequestParam("token") String token,HttpServletRequest request) {		
		String targetPage = JspPageNameConstants.APPTADMINDESK_RESET_PASSWORD;
		ModelAndView modelView = new ModelAndView();
		try{
			ResetPasswordTO resetPasswordTO  = new ResetPasswordTO(); //adminApptService.resetPasswordRequest(token); TODO : Needs to implement this method
			if(resetPasswordTO!=null) {
				resetPasswordTO.setToken(token);
				resetPasswordTO.setNewpassword(null);
				resetPasswordTO.setConformpassword(null);
				modelView.addObject("resetPasswordTO",resetPasswordTO);
			}else{
				UserLoginTO loginForm = new UserLoginTO();
				getLoginModelAndView(loginForm,modelView, request);  
				modelView.addObject("resetPasswordResponse","Invalid request");
			}
		} catch (Exception e) {
			logger.error("exception in resetPasswordRequest - "+((e.getMessage() !=null)?e.getMessage():"") , e);
			apptAdminService.sendErrorEmail("N.A","resetPasswordRequest",request,e);
		}
		modelView.setViewName(targetPage);
	    return modelView;
	}
	
	@RequestMapping(value = "validateResetPassword", method = RequestMethod.GET)
	public @ResponseBody String validateResetPassword(@RequestParam("username") String username,@RequestParam("password") String password,
			@RequestParam("passwordComplexity") String passwordComplexity,HttpServletRequest request) throws Exception {	
			String response = "";
		try {
			response = PasswordValidator.validatePassword(passwordComplexity, username, password);
		} catch (Exception e) {
			logger.error("exception in resetPasswordRequest - "+((e.getMessage() !=null)?e.getMessage():"") , e);
			apptAdminService.sendErrorEmail("N.A","validateResetPassword",request,e);
		}
		return response;
	}
	
	@RequestMapping(value = JspPageNameConstants.APPTADMINDESK_UPDATE_PASSWORD, method = RequestMethod.POST)
	public ModelAndView updatePassword(@ModelAttribute("resetPasswordTO") ResetPasswordTO resetPasswordTO,HttpServletRequest request) {		
		String targetPage = JspPageNameConstants.APPTADMINDESK_REDIRECT + JspPageNameConstants.APPTADMINDESK_LOGIN_PAGE + JspPageNameConstants.APPTADMINDESK_HTML_EXTENSION;
		ModelAndView modelView = new ModelAndView();	
		try{			
			String resetPasswordResponse = null; //adminApptService.updatePassword(resetPasswordTO);  TODO : Needs to implement this method
			modelView.addObject("resetPasswordResponse",resetPasswordResponse);
			modelView.setViewName(targetPage);
		}catch (Exception e) {
			logger.error("exception in resetPasswordRequest - "+((e.getMessage() !=null)?e.getMessage():"") , e);
			apptAdminService.sendErrorEmail("N.A","updatePassword",request,e);
		}
	    return modelView;
	}
	
	@RequestMapping(value=JspPageNameConstants.APPTADMINDESK_SESSION_EXPIRED_PAGE, method = RequestMethod.GET)
	public ModelAndView showSessionExpiredPage(HttpServletRequest request) throws Exception {
		ModelAndView modelView = new ModelAndView();
		logger.error(" Session is Expired so redirecting to login page");
		modelView.setViewName(JspPageNameConstants.APPTADMINDESK_SESSION_EXPIRED_PAGE);
	    return modelView;  
	}
}
