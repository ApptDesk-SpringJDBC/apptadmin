package com.telappoint.apptadmin.model;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * 
 * @author Murali G
 *
 */
@JsonAutoDetect
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResourceResponse extends BaseResponse {
	
	// populated if more then one resources.
	private List<Resource> resourceList;
	private List<Resource> deletedResourceList;
	private Integer resourceId;

	//populated if one resource
	private Resource resource;

	@JsonIgnore
	private Map<String,List<Resource>> resourceListMap; //Map<locName,<list<resource>>
	
	public List<Resource> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<Resource> resourceList) {
		this.resourceList = resourceList;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public List<Resource> getDeletedResourceList() {
		return deletedResourceList;
	}

	public void setDeletedResourceList(List<Resource> deletedResourceList) {
		this.deletedResourceList = deletedResourceList;
	}

	public Map<String, List<Resource>> getResourceListMap() {
		return resourceListMap;
	}

	public void setResourceListMap(Map<String, List<Resource>> resourceListMap) {
		this.resourceListMap = resourceListMap;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
}
