package com.jeff.tianti.org.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.jeff.tianti.common.service.CommonService;
import com.jeff.tianti.org.dao.ResourceDao;
import com.jeff.tianti.org.entity.Resource;

@Service
public class ResourceService extends CommonService<Resource,String>{

	@Autowired
	private ResourceDao resourceDao;

	@Autowired
	public void setResourceDao(ResourceDao resourceDao) {
		super.setCommonDao(resourceDao);
	}
	
	public List<Resource> getRootResourceList(){
		return resourceDao.getRootResourceList();
	}
	/**
	 * 根据类型取所有资源
	 * @param type
	 * @return
	 */
	public List<Resource> getSystemResourceList(){
		return this.resourceDao.getSystemResourceList();
	}
	
	public List<Map<String, Object>> getMap(){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		List<Resource> resources = getSystemResourceList();
		if(resources != null && !resources.isEmpty()){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", "all");
			map.put("name", "所有菜单");
			map.put("pId", "0");
			map.put("open", true);
			list.add(map);
			
			for(Resource r : resources){
				map = new HashMap<String, Object>();
				map.put("id", r.getId());
				map.put("name", r.getName());
				String pId = "all";
				if(r.getParent() != null){
					pId = r.getParent().getId();
				}else{
				}
				map.put("pId", pId);
				list.add(map);
			}
		}
		
		return list;
	}
	
	//@Cacheable(value = "org_cache", key="'allMenu'")
	public List<Resource> findAllMenu(){
		List<Resource> resources = new ArrayList<Resource>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deleteFlag", Resource.DELETE_FLAG_NORMAL);
		List<Resource> list = resourceDao.findMenuResource(params);
		if(list != null && !list.isEmpty()){
			Map<String, Resource> map = new HashMap<String, Resource>(); 
			for(Resource r : list){
				map.put(r.getId(), r);
			}
			
			for(Resource r : list){
				if(r.getParent() == null){
					resources.add(r);
				}else{
					Resource parentResource = map.get(r.getParent().getId());
					if(parentResource == null){
						resources.add(r);
					}else{
						if(parentResource.getChildren() == null){
							parentResource.setChildren(new ArrayList<Resource>());
						}
						parentResource.getChildren().add(r);
					}
				}
			}
			
		}
		
		return resources;
	}
	
	@CacheEvict(value="org_cache", key="'allMenu'")
	public void updateDeleteFlag(String[] ids, String deleteFlag){
		if(ids != null){
			for(String id : ids){
				Resource resource = this.find(id);
				resource.setDeleteFlag(deleteFlag);
				this.update(resource);
			}
		}
	}
	
	@CacheEvict(value="org_cache", key="'allMenu'")
	public void saveResource(Resource resource){
		if(StringUtils.isNotBlank(resource.getId())){
			this.update(resource);
		}else{
			this.save(resource);
		}
	}
	
	public List<Resource> findMenuResource(Map<String, Object> params) {
		return resourceDao.findMenuResource(params);
	}
	
}
