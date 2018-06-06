package com.telappoint.apptadmin.restclient;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.telappoint.apptadmin.client.json.ResponseModel;
import com.telappoint.apptadmin.utils.AdminUtils;

/**
 * 
 * @author Murali G
 * 
 */
public class ApptAdminRestClient {
	
	private Logger logger = Logger.getLogger(ApptAdminRestClient.class);
	
	private static Client client;

	private static volatile ApptAdminRestClient instance;

	private ApptAdminRestClient() {
		getApptdeskClient();
	}

	public static ApptAdminRestClient getInstance() {
		if (instance == null) {
			synchronized (ApptAdminRestClient.class) {
				if (instance == null)
					instance = new ApptAdminRestClient();
			}
		}
		return instance;
	}

	public static Client getApptdeskClient() {
		ClientConfig config = new DefaultClientConfig();
		config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		client = Client.create(config);
		return client;
	}
	
	public ResponseModel performGETRequest(String endPointUrl,String serviceMethod) throws Exception {
		long startTime = System.currentTimeMillis();	
		//replacing special characters
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		endPointUrl = endPointUrl.replaceAll("\\|","%7C");
		endPointUrl = endPointUrl.replaceAll("~","%7E");
				
		WebResource webResource = client.resource(endPointUrl);
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		System.out.println("GET Request ::: EndPointUrl :::  "+endPointUrl);
		logger.debug("GET Request ::: EndPointUrl :::  "+endPointUrl);
		if (response.getStatus() != 200) {
			logger.error("Failed GET Request ::: ( "+serviceMethod+" ) ::: EndPointUrl :::  "+endPointUrl +" , \t HTTP Status code ::: "+response.getStatus()+" , \t Time taken ::" + (System.currentTimeMillis()-startTime));
			throw new Exception("Failed GET Request ::: EndPointUrl :::  "+endPointUrl +" , \t HTTP Status code ::: "+response.getStatus()+" , \t Time taken ::" + (System.currentTimeMillis()-startTime));
		}
		logger.debug(" GET Request ::: ( "+serviceMethod+" ) ::: EndPointUrl :::::  "+endPointUrl +" , \t Time taken ::" + (System.currentTimeMillis()-startTime));
		return response.getEntity(ResponseModel.class);
	}

	public ResponseModel performPOSTRequest(String endPointUrl,Object request,String serviceMethod) throws Exception {		
		long startTime = System.currentTimeMillis();	
		WebResource webResource  = client.resource(endPointUrl);
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class,request);
		System.out.println("POST Request ::: EndPointUrl :::  "+endPointUrl +" , \t JSON Request Data ::: "+((request instanceof String) ? (String)request : AdminUtils.getDataFromJSON(request)));
		logger.debug("POST Request ::: EndPointUrl :::  "+endPointUrl +" , \t JSON Request Data ::: "+((request instanceof String) ? (String)request : AdminUtils.getDataFromJSON(request)));
		if (response.getStatus() != 200 && response.getStatus() != 201) {
			logger.error("Failed POST Request ::: ( "+serviceMethod+" ) ::: EndPointUrl :::  "+endPointUrl +" , \t HTTP Status code ::: "+response.getStatus()+" , \t Time taken ::" + (System.currentTimeMillis()-startTime));
			throw new Exception("Failed POST Request ::: EndPointUrl :::  "+endPointUrl +" , \t JSON Request Data ::: "+((request instanceof String) ? (String)request : AdminUtils.getDataFromJSON(request)) +" , \t HTTP Status code ::: "+response.getStatus()+" , \t Time taken ::" + (System.currentTimeMillis()-startTime));
		}		
		logger.debug(" POST Request ::: ( "+serviceMethod+" ) ::: EndPointUrl :::::  "+endPointUrl+" , \t Time taken ::" + (System.currentTimeMillis()-startTime));
		return response.getEntity(ResponseModel.class);
	}
	
	public static void main(String[] args) {
		try {
			
			/*String endPointUrl = "http://localhost:8082/ApptAdminRestService/service/loginAuthenticate";
			System.out.println("endPointUrl ::: "+endPointUrl);
			
			UserLoginTO loginForm = new UserLoginTO();
			loginForm.setIpAddress("127.0.0.1");
			loginForm.setUsername("demodoctors2");
			loginForm.setPassword("Test123");
			
			System.out.println("loginForm ::::::::::::: "+AdminUtils.getJSONDataStr(loginForm));
			
			ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,loginForm,"loginAuthenticate");
			System.out.println("loginAuthenticate : response :  "+responseModel.getData());
			
			UserLoginResponse userLoginResponse = AdminUtils.getDataFromJSON(responseModel.getData(),UserLoginResponse.class);
			System.out.println("UserLoginResponse ::::::::::::: "+userLoginResponse);
			*/
			/*String endPointUrl = "http://localhost:8082/ApptAdminRestService/service/getApptSysConfig?clientCode=DEMODOC2";
			System.out.println("endPointUrl ::: "+endPointUrl);
			
			ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"loginAuthenticate");
			System.out.println("getApptSysConfig : response :  "+responseModel.getData());
			
			ApptSysConfig apptSysConfig = AdminUtils.getDataFromJSON(responseModel.getData(),ApptSysConfigResponse.class).getApptSysConfig();
			System.out.println("apptSysConfig ::::::::::::: "+apptSysConfig);*/
			
			/*String endPointUrl = "http://localhost:8082/ApptAdminRestService/service/getDisplayNames?clientCode=DEMODOC2";
			System.out.println("endPointUrl ::: "+endPointUrl);
			
			ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"loginAuthenticate");
			System.out.println("getDisplayNames : response :  "+responseModel.getData());
			
			DisplayNames displayNames = AdminUtils.getDataFromJSON(responseModel.getData(),DisplayNamesResponse.class).getDisplayNames();
			System.out.println("displayNames ::::::::::::: "+displayNames);
			*/
			
			/*String endPointUrl = "http://localhost:8082/ApptAdminRestService/service/getClientDetails?clientCode=DEMODOC2";
			System.out.println("endPointUrl ::: "+endPointUrl);
			
			ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"loginAuthenticate");
			System.out.println("getClientDetails : response :  "+responseModel.getData());
			
			Client client = AdminUtils.getDataFromJSON(responseModel.getData(),Client.class);
			System.out.println("getClientDetails ::::::::::::: "+client);
			*/
			
			/*String endPointUrl = "http://localhost:8082/ApptAdminRestService/service/getPrivilegedPageNames?clientCode=DEMODOC2";
			System.out.println("endPointUrl ::: "+endPointUrl);
			
			ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getPrivilegedPageNames");
			System.out.println("getPrivilegedPageNames : response :  "+responseModel.getData());
			
			PrivilegedPageNamesResponse privilegedPageNamesResponse = AdminUtils.getDataFromJSON(responseModel.getData(),PrivilegedPageNamesResponse.class);
			System.out.println("privilegedPageNamesResponse ::::::::::::: "+privilegedPageNamesResponse);
			System.out.println("privilegedPageNamesResponse ::::::::::::: "+privilegedPageNamesResponse.getPrevilegePageNames());
			System.out.println("privilegedPageNamesResponse ::::::::::::: "+privilegedPageNamesResponse.getPrevilegePageNames().get("Super User"));*/
			
			
			/*String endPointUrl = "http://localhost:8082/ApptAdminRestService/service/getAdminHomePage";
			System.out.println("endPointUrl ::: "+endPointUrl);
			
			HomePageRequest homePageRequest = new HomePageRequest();
			homePageRequest.setClientCode("DEMODOC2");
			homePageRequest.setOnlyActiveResources("Y");
			homePageRequest.setResourceOrderByColumn("locationName");
			homePageRequest.setOnlyActiveLocations("Y");
			homePageRequest.setOnlyActiveServices("Y");
			homePageRequest.setLoginUserId("7");
			
			System.out.println("homePageResponse ::::::::::::: "+AdminUtils.getJSONDataStr(homePageRequest));
			
			ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,homePageRequest,"getAdminHomePage");
			System.out.println("getAdminHomePage : response :  "+responseModel.getData());
			HomePageResponse homePageResponse = AdminUtils.getDataFromJSON(responseModel.getData(),HomePageResponse.class);
			System.out.println("homePageResponse ::::::::::::: "+homePageResponse);*/
			
			/*String endPointUrl = "http://localhost:8082/ApptAdminRestService/service/getGaugeChart?clientCode=DEMODOC2&locationId=1&startDate=2016-11-01&endDate=2016-12-01";
			System.out.println("endPointUrl ::: "+endPointUrl);
			ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getGaugeChart");
			System.out.println("getGaugeChart : response 111 :  "+responseModel.getData());
			GaugeChartResponse gaugeChartResponse = AdminUtils.getDataFromJSON(responseModel.getData(),GaugeChartResponse.class);
			System.out.println("getGaugeChart : response :  "+gaugeChartResponse);
			
			System.out.println("StartDate :  "+gaugeChartResponse.getGaugeStartDate());
			System.out.println("EndDate :  "+gaugeChartResponse.getGaugeEndDate());
			System.out.println("OpenedAppts :  "+gaugeChartResponse.getGaugeOpenedAppts());
			System.out.println("BookedAppts :  "+gaugeChartResponse.getGaugeBookedAppts());
			
			System.out.println("\n===================================================\n");
			
			endPointUrl = "http://localhost:8082/ApptAdminRestService/service/getGaugeChartList?clientCode=DEMODOC2&locationId=1&startDate=2016-11-01&endDate=2016-12-01";
			System.out.println("endPointUrl ::: "+endPointUrl);
			responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getGaugeChart");
			System.out.println("getGaugeChart : response 111 :  "+responseModel.getData());
			List<GaugeChartResponse> gaugeChartResponseList = AdminUtils.getListDataFromJSON(responseModel.getData(),GaugeChartResponse[].class);
			System.out.println("getGaugeChart : response :  "+gaugeChartResponseList);
			for(GaugeChartResponse gaugeChart : gaugeChartResponseList){
				System.out.println(gaugeChart);
				System.out.println("StartDate :  "+gaugeChart.getGaugeStartDate());
				System.out.println("EndDate :  "+gaugeChart.getGaugeEndDate());
				System.out.println("OpenedAppts :  "+gaugeChart.getGaugeOpenedAppts());
				System.out.println("BookedAppts :  "+gaugeChart.getGaugeBookedAppts());
			}
			
			
			System.out.println("\n===================================================\n");
			
			endPointUrl = "http://localhost:8082/ApptAdminRestService/service/getGaugeChartMap?clientCode=DEMODOC2&locationId=1&startDate=2016-11-01&endDate=2016-12-01";
			System.out.println("endPointUrl ::: "+endPointUrl);
			responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getGaugeChart");
			System.out.println("getGaugeChart : response 111 :  "+responseModel.getData());
			Map<String, String> gaugeChartResponseMap = AdminUtils.getMapDataFromJSON(responseModel.getData(),String.class,String.class);
			System.out.println("getGaugeChart : response :  "+gaugeChartResponse);
			for(String gaugeChart : gaugeChartResponseMap.keySet()){
				System.out.println(gaugeChart+" == "+gaugeChartResponseMap.get(gaugeChart));
			}*/
			
			/*String endPointUrl = "http://localhost:8082/ApptAdminRestService/service/"+ApptAdminRestConstants.REST_SERVICE_GET_ADMIN_HOME_PAGE.getRequestURI();
			System.out.println("endPointUrl ::: "+endPointUrl);
			HomePageRequest homePageRequest = new HomePageRequest();			
			homePageRequest.setClientCode("DEMODOC2");
			homePageRequest.setLoginUserId("7");
			homePageRequest.setOnlyActiveLocations("Y");
			homePageRequest.setOnlyActiveResources("Y");
			homePageRequest.setOnlyActiveServices("Y");
			homePageRequest.setResourceOrderByColumn("first_name");
			homePageRequest.setUserName("demodoctors2");
			System.out.println("Request Data ::: "+new GsonBuilder().create().toJson(homePageRequest));
			System.out.println("clientCode :::: "+homePageRequest.getClientCode());
			ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,homePageRequest,"getAdminHomePage");
			
			System.out.println("homePageResponse : response 111 :  "+responseModel.getData());
			HomePageResponse homePageResponse = AdminUtils.getDataFromJSON(responseModel.getData(),HomePageResponse.class);
			System.out.println("homePageResponse : response :  "+homePageResponse);*/
			
			/*System.out.println("StartDate :  "+gaugeChartResponse.getGaugeStartDate());
			System.out.println("EndDate :  "+gaugeChartResponse.getGaugeEndDate());
			System.out.println("OpenedAppts :  "+gaugeChartResponse.getGaugeOpenedAppts());
			System.out.println("BookedAppts :  "+gaugeChartResponse.getGaugeBookedAppts());*/
			
			/*String endPointUrl = "http://localhost:8082/ApptAdminRestService/service/getStackedChart?clientCode=DEMODOC2&locationId=1&resourceId=1";
			System.out.println("endPointUrl ::: "+endPointUrl);
			ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getStackedChart");
			System.out.println("getStackedChart : response 111 :  "+responseModel.getData());
			StackedChartResponse stackedChartResponse = AdminUtils.getDataFromJSON(responseModel.getData(),StackedChartResponse.class);
			System.out.println("getStackedChart : response :  "+stackedChartResponse);
			
			System.out.println("NoOfApptsBooked :  "+stackedChartResponse.getNoOfApptsBooked());
			System.out.println("NoOfApptsOpened :  "+stackedChartResponse.getNoOfApptsOpened());
			System.out.println("StackedChartDays :  "+stackedChartResponse.getStackedChartDays());*/
			
			/*String endPointUrl = "http://localhost:8082/ApptAdminRestService/service/getPieChart?clientCode=DEMODOC2&locationId=1&selectedDate=11/01/2016";
			System.out.println("endPointUrl ::: "+endPointUrl);
			ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getPieChart");
			System.out.println("getPieChart : response 111 :  "+responseModel.getData());
			PieChartResponse pieChartResponse = AdminUtils.getDataFromJSON(responseModel.getData(),PieChartResponse.class);
			System.out.println("getPieChart : response :  "+pieChartResponse);
			
			System.out.println("NoOfConfirmedAppts :  "+pieChartResponse.getNoOfConfirmedAppts());
			System.out.println("PieChartDate :  "+pieChartResponse.getPieChartDate());*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
