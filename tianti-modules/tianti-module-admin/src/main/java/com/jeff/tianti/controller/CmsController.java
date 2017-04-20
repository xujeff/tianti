package com.jeff.tianti.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeff.tianti.cms.dto.ArticleQueryDTO;
import com.jeff.tianti.cms.dto.ColumnInfoDTO;
import com.jeff.tianti.cms.dto.ColumnInfoQueryDTO;
import com.jeff.tianti.cms.entity.Article;
import com.jeff.tianti.cms.entity.ColumnInfo;
import com.jeff.tianti.cms.service.ArticleService;
import com.jeff.tianti.cms.service.ColumnInfoService;
import com.jeff.tianti.common.dto.AjaxResult;
import com.jeff.tianti.common.entity.PageModel;
import com.jeff.tianti.util.Constants;
/**
 * CMS的Controller
 * @author Jeff Xu
 */
@Controller
@RequestMapping("/cms")
public class CmsController {
	
	@Autowired
	private ColumnInfoService columnInfoService;
	
	@Autowired
	private ArticleService articleService;
	
	/**
	 * 获取栏目列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/column/list")
	public String columnList(HttpServletRequest request, Model model){
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		String rootColumnId = request.getParameter("rootColumnId");		
		
		ColumnInfoQueryDTO cDTO =new ColumnInfoQueryDTO();
		cDTO.setLevel(ColumnInfo.LEVEL_ROOT);
		List<ColumnInfo> rootCoulumnInfoList = this.columnInfoService.queryColumnInfoList(cDTO);
		if(StringUtils.isBlank(rootColumnId) && rootCoulumnInfoList != null && rootCoulumnInfoList.size() > 0){
			rootColumnId = rootCoulumnInfoList.get(0).getId();
		}
		ColumnInfoQueryDTO columnInfoQueryDTO = new ColumnInfoQueryDTO();
		columnInfoQueryDTO.setRootColumnId(rootColumnId);
		columnInfoQueryDTO.setIsRootColumnLike(false);
		columnInfoQueryDTO.setCode(code);
		columnInfoQueryDTO.setName(name);
		List<ColumnInfo> list = this.columnInfoService.queryColumnInfoList(columnInfoQueryDTO);
		
		model.addAttribute("list", list);
		model.addAttribute("columnInfoQueryDTO", columnInfoQueryDTO);
		model.addAttribute(Constants.MENU_NAME, Constants.MENU_COLUMN_LIST);
		model.addAttribute("rootCoulumnInfoList", rootCoulumnInfoList);
		
		return "/cms/column_default_list";
	}
	
	/**
	 * 校验栏目编码是否存在
	 * @param request
	 * @return
	 */
	@RequestMapping("/column/ajax/validator/code")
	@ResponseBody
	public Map<String, Object> ajaxValidatorUsername(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		String code = request.getParameter("code");
		ColumnInfoQueryDTO columnInfoQueryDTO = new ColumnInfoQueryDTO();
		columnInfoQueryDTO.setCode(code);
		columnInfoQueryDTO.setIsCodeLike(false);
		List<ColumnInfo> columnInfoList = this.columnInfoService.queryColumnInfoList(columnInfoQueryDTO);
		if(columnInfoList != null && columnInfoList.size() > 0){
			map.put("error", "编码已经存在");
		}else{
			map.put("ok", "");
		}
		return map;
	} 
	
	/**
	 * 栏目修改
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/column/edit")
	public String edit(HttpServletRequest request, Model model){
		ColumnInfo columnInfo = null;
		String id = request.getParameter("id");
		String columnLevel = request.getParameter("columnLevel");
		String rootColumnId = request.getParameter("rootColumnId");
		ColumnInfoQueryDTO columnInfoQueryDTO =new ColumnInfoQueryDTO();
		columnInfoQueryDTO.setLevel(ColumnInfo.LEVEL_ROOT);
		List<ColumnInfo> rootCoulumnInfoList = this.columnInfoService.queryColumnInfoList(columnInfoQueryDTO);
		if(StringUtils.isNotBlank(id)){
			columnInfo = this.columnInfoService.find(id);
		}
		model.addAttribute("columnInfo", columnInfo);
		model.addAttribute("columnLevel", columnLevel);
		model.addAttribute("rootColumnId", rootColumnId);
		model.addAttribute("rootCoulumnInfoList", rootCoulumnInfoList);
		return "/cms/dialog/column_default_edit";
	}
	
	/**
	 * 栏目保存操作
	 * @param request
	 * @return
	 */
	@RequestMapping("/column/ajax/save")
	@ResponseBody
	public AjaxResult doColumnajaxSave(HttpServletRequest request){
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(false);
		try {
			String id = request.getParameter("id");
			String code = request.getParameter("code");
			String name = request.getParameter("name");
			String columnLevel = request.getParameter("columnLevel");
			String parentId = request.getParameter("parentId");
			String orderNoStr = request.getParameter("orderNo");
		    Integer orderNo = null;
		    if(StringUtils.isNotBlank(orderNoStr)){
		    	orderNo = Integer.parseInt(orderNoStr);
		    }
			ColumnInfo columnInfo = null;
			if(StringUtils.isNotBlank(id)){
				columnInfo = this.columnInfoService.find(id);
				columnInfo.setName(name);
				if(columnLevel.equals("level0")){
					columnInfo.setLevel(ColumnInfo.LEVEL_ROOT);
					columnInfo.setPath(columnInfo.getId());
				}else{
					columnInfo.setLevel(ColumnInfo.LEVEL_LEAF);
					if(StringUtils.isNotBlank(parentId)){
						ColumnInfo parentColumnInfo = this.columnInfoService.find(parentId);
						columnInfo.setParent(parentColumnInfo);
					}
				}
				columnInfo.setChannel(ColumnInfo.CHANNEL_PC);
				columnInfo.setOrderNo(orderNo);
				columnInfo.setUpdateDate(new Date());
			}else{
				columnInfo = new ColumnInfo();
				columnInfo.setCode(code);
				columnInfo.setName(name);
				if(columnLevel.equals("level0")){
					columnInfo.setLevel(ColumnInfo.LEVEL_ROOT);
				}else{
					columnInfo.setLevel(ColumnInfo.LEVEL_LEAF);
					if(StringUtils.isNotBlank(parentId)){
						ColumnInfo parentColumnInfo = this.columnInfoService.find(parentId);
						columnInfo.setParent(parentColumnInfo);
					}
				}
				columnInfo.setChannel(ColumnInfo.CHANNEL_PC);
				columnInfo.setOrderNo(orderNo);
				columnInfo.setCreateDate(new Date());
				columnInfo.setDeleteFlag(ColumnInfo.DELETE_FLAG_NORMAL);
			}
						
			if(StringUtils.isNotBlank(id)){
				this.columnInfoService.update(columnInfo);
			}else{
				this.columnInfoService.save(columnInfo);
				if(columnInfo != null){
					if(columnInfo.getParent() != null){
						String path = columnInfo.getParent().getId()+"/"+columnInfo.getId();
						columnInfo.setPath(path);
					}else{
						columnInfo.setPath(columnInfo.getId());
					}
					this.columnInfoService.update(columnInfo);
				}
			}
			ajaxResult.setSuccess(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxResult;
	}
	
	/**
	 * 栏目删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/column/ajax/delete")
	@ResponseBody
	public AjaxResult ajaxUpdColumnDelete(HttpServletRequest request){
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(false);
		
		try {
			String[] ids = request.getParameterValues("ids");
			String deleteFlag = request.getParameter("deleteFlag");
			
			if(ids != null && ids.length > 0){
				for(String id:ids){
					ColumnInfo columnInfo = this.columnInfoService.find(id);
					columnInfo.setDeleteFlag(deleteFlag);
					this.columnInfoService.update(columnInfo);
				}
			}
			ajaxResult.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ajaxResult;
	}
	
	/**
	 * 获取文章列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/article/list")
	public String articleList(HttpServletRequest request, Model model){
		String type = request.getParameter("type");
		String rootColumnId = request.getParameter("rootColumnId");
		String columnId = request.getParameter("columnId");
		String title = request.getParameter("title");
		String publisher = request.getParameter("publisher");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String createDateSortCss = request.getParameter("createDateSortCss");
		String currentPageStr = request.getParameter("currentPage");
		String pageSizeStr = request.getParameter("pageSize");
		int currentPage = 1;
		int pageSize = 10;
		if(StringUtils.isNotBlank(currentPageStr)){
			currentPage = Integer.parseInt(currentPageStr);
		}
		if(StringUtils.isNotBlank(pageSizeStr)){
			pageSize = Integer.parseInt(pageSizeStr);
		}
		ColumnInfoQueryDTO columnInfoQueryDTO =new ColumnInfoQueryDTO();
		columnInfoQueryDTO.setLevel(ColumnInfo.LEVEL_ROOT);
		List<ColumnInfo> rootCoulumnInfoList = this.columnInfoService.queryColumnInfoList(columnInfoQueryDTO);
		List<ColumnInfoDTO> columnInfoDTOList = new ArrayList<ColumnInfoDTO>();
		
		
		if(rootCoulumnInfoList != null && rootCoulumnInfoList.size() > 0){
			if(StringUtils.isBlank(rootColumnId)){
				rootColumnId = rootCoulumnInfoList.get(0).getId();
			}
			for(ColumnInfo c:rootCoulumnInfoList){
				ColumnInfoDTO columnInfoDTO = new ColumnInfoDTO();
				columnInfoDTO.setId(c.getId());
				columnInfoDTO.setName(c.getName());
				
				ColumnInfoQueryDTO childColumnInfoQueryDTO = new ColumnInfoQueryDTO();
				childColumnInfoQueryDTO.setRootColumnId(c.getId());
				childColumnInfoQueryDTO.setIsRootColumnLike(false);
				List<ColumnInfo> childColumnInfoList = this.columnInfoService.queryColumnInfoList(childColumnInfoQueryDTO);
				if(childColumnInfoList != null && childColumnInfoList.size() > 0){
					List<ColumnInfoDTO> rColumnInfoDTOList = new ArrayList<ColumnInfoDTO>();
					for(ColumnInfo rc:childColumnInfoList){
						ColumnInfoDTO rColumnInfoDTO = new ColumnInfoDTO();
						rColumnInfoDTO.setId(rc.getId());
						rColumnInfoDTO.setName(rc.getName());
						rColumnInfoDTOList.add(rColumnInfoDTO);
					}
					columnInfoDTO.setChildColumnInfoList(rColumnInfoDTOList);
				}
				columnInfoDTOList.add(columnInfoDTO);
			}
		}
		
		ArticleQueryDTO articleQueryDTO = new ArticleQueryDTO();
		articleQueryDTO.setColumnId(columnId);
		articleQueryDTO.setType(type);
		articleQueryDTO.setRootColumnId(rootColumnId);
		articleQueryDTO.setTitle(title);
		articleQueryDTO.setPublisher(publisher);
		articleQueryDTO.setStartDate(startDate);
		articleQueryDTO.setEndDate(endDate);
		articleQueryDTO.setCreateDateSortCss(createDateSortCss);
		articleQueryDTO.setCurrentPage(currentPage);
		articleQueryDTO.setPageSize(pageSize);
		
		PageModel<Article> page = this.articleService.queryArticlePage(articleQueryDTO);
		List<Map<String,Object>> statisMapList = this.articleService.queryStatisMapList(articleQueryDTO);
		Map<String,Object> statisMap = null;
		if(statisMapList != null && statisMapList.size() > 0){
			statisMap = statisMapList.get(0);
		}
		model.addAttribute("page", page);
		model.addAttribute("type", type);
		model.addAttribute("statisMap", statisMap);
		model.addAttribute("articleQueryDTO", articleQueryDTO);
		model.addAttribute("rootCoulumnInfoList", rootCoulumnInfoList);
		model.addAttribute("columnInfoDTOList", columnInfoDTOList);
		model.addAttribute(Constants.MENU_NAME, Constants.MENU_ARTICLE_LIST);
		
		return "/cms/article_default_list";
	}
	
	/**
	 * 文章修改
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/article/edit")
	public String articleEdit(HttpServletRequest request, Model model){
		Article article = null;
		String id = request.getParameter("id");		
		String rootColumnId = request.getParameter("rootColumnId");
		String columnId = request.getParameter("columnId");
		if(StringUtils.isNotBlank(id)){
			article = this.articleService.find(id);
			if(article != null){
				rootColumnId = article.getRootColumnInfo().getId();
				columnId = article.getColumnInfo().getId();
			}
		}
		ColumnInfoQueryDTO columnInfoQueryDTO =new ColumnInfoQueryDTO();
		columnInfoQueryDTO.setLevel(ColumnInfo.LEVEL_ROOT);
		List<ColumnInfo> rootCoulumnInfoList = this.columnInfoService.queryColumnInfoList(columnInfoQueryDTO);
		
		model.addAttribute("rootColumnId", rootColumnId);
		model.addAttribute("columnId", columnId);
		model.addAttribute("article", article);
		model.addAttribute("rootCoulumnInfoList", rootCoulumnInfoList);
		model.addAttribute(Constants.MENU_NAME, Constants.MENU_ARTICLE_LIST);
		
		return "/cms/article_edit";
	}
	
	/**
	 * 文章保存操作
	 * @param request
	 * @return
	 */
	@RequestMapping("/article/ajax/save")
	@ResponseBody
	public AjaxResult doArticlejaxSave(HttpServletRequest request){
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(false);
		try {
			String id = request.getParameter("id");
			String rootColumnId = request.getParameter("rootColumnId");
			String columnInfoId = request.getParameter("leafColumnId");
			String title = request.getParameter("title");
			String publisher = request.getParameter("publisher");
			String content = request.getParameter("content");
			String summary = request.getParameter("summary");
			String coverImageUrl = request.getParameter("coverImageUrl");
			String href = request.getParameter("href");
			String orderNoStr = request.getParameter("orderNo");
			String articleTypeStr = request.getParameter("articleType");
		   
			Article article = null;
			Integer orderNo = null;
			Integer articleType = null;
			ColumnInfo rootColumnInfo = null;
			ColumnInfo columnInfo = null;
			if(StringUtils.isNotBlank(rootColumnId)){
				rootColumnInfo = this.columnInfoService.find(rootColumnId);
			}
			if(StringUtils.isNotBlank(columnInfoId)){
				columnInfo = this.columnInfoService.find(columnInfoId);
			}
			if(StringUtils.isNotBlank(orderNoStr)){
				orderNo = Integer.parseInt(orderNoStr);
			}
			if(StringUtils.isNotBlank(articleTypeStr)){
				if(articleTypeStr.equals("contentType")){
					articleType = 0;
				}else if(articleTypeStr.equals("hrefType")){
					articleType = 1;
				}else if(articleTypeStr.equals("adType")){
					articleType = 2;
				}
			}
			if(StringUtils.isNotBlank(id)){
				article = this.articleService.find(id);
				article.setTitle(title);
				article.setPublisher(publisher);
				article.setContent(content);
				article.setType(articleType);
				article.setRootColumnInfo(rootColumnInfo);
				article.setColumnInfo(columnInfo);
				article.setSummary(summary);
				article.setUpdateDate(new Date());
				article.setOrderNo(orderNo);
				article.setHref(href);
				if(StringUtils.isNotBlank(coverImageUrl)){
					article.setCoverImageUrl(coverImageUrl);
				}
			}else{
				article = new Article();
				article.setTitle(title);
				article.setPublisher(publisher);
				article.setContent(content);
				article.setType(articleType);
				article.setRootColumnInfo(rootColumnInfo);
				article.setColumnInfo(columnInfo);
				article.setSummary(summary);
				article.setCreateDate(new Date());
				article.setViewCount(0);
				article.setDeleteFlag(ColumnInfo.DELETE_FLAG_NORMAL);
				article.setIsAudit(true);
				article.setOrderNo(orderNo);
				article.setHref(href);
				if(StringUtils.isNotBlank(coverImageUrl)){
					article.setCoverImageUrl(coverImageUrl);
				}
			}
						
			if(StringUtils.isNotBlank(id)){
				this.articleService.update(article);
			}else{
				this.articleService.save(article);
			}
			ajaxResult.setSuccess(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxResult;
	}
	
	/**
	 * 根据根节点获取叶子节点
	 * @param request
	 * @return
	 */
	@RequestMapping("/column/ajax/getLeafColumn")
	@ResponseBody
	public AjaxResult getLeafColumn(HttpServletRequest request){
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(true);
		String rootColumnId = request.getParameter("rootColumnInfoId");
		ColumnInfoQueryDTO columnInfoQueryDTO = new ColumnInfoQueryDTO();
		columnInfoQueryDTO.setIsRootColumnLike(false);
		columnInfoQueryDTO.setRootColumnId(rootColumnId);
		List<ColumnInfo> columnInfoList = this.columnInfoService.queryColumnInfoList(columnInfoQueryDTO);
		ajaxResult.setData(columnInfoList);
		return ajaxResult;
	}
	
	/**
	 * 文章删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/article/ajax/delete")
	@ResponseBody
	public AjaxResult ajaxArticleDelete(HttpServletRequest request){
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(false);
		
		try {
			String[] ids = request.getParameterValues("ids");
			String deleteFlag = request.getParameter("deleteFlag");
			
			if(ids != null && ids.length > 0){
				for(String id:ids){
					Article article = this.articleService.find(id);
					article.setDeleteFlag(deleteFlag);
					this.articleService.update(article);
				}
			}
			ajaxResult.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ajaxResult;
	}
	
	/**
	 * 文章审核
	 * @param request
	 * @return
	 */
	@RequestMapping("/article/ajax/audit")
	@ResponseBody
	public AjaxResult ajaxArticleAudit(HttpServletRequest request){
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(false);
		
		try {
			Boolean isAudit = false;
			String[] ids = request.getParameterValues("ids");
			String auditFlag = request.getParameter("auditFlag");
			if(StringUtils.isNotBlank(auditFlag) && auditFlag.equals("1")){
				isAudit = true;
			}
			if(ids != null && ids.length > 0){
				for(String id:ids){
					Article article = this.articleService.find(id);
					article.setIsAudit(isAudit);
					this.articleService.update(article);
				}
			}
			ajaxResult.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ajaxResult;
	}
	
	/**
	 * 文章置顶
	 * @param request
	 * @return
	 */
	@RequestMapping("/article/ajax/top")
	@ResponseBody
	public AjaxResult ajaxArticleTop(HttpServletRequest request){
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(false);
		
		try {
			Boolean isTop = false;
			String[] ids = request.getParameterValues("ids");
			String topFlag = request.getParameter("topFlag");
			if(StringUtils.isNotBlank(topFlag) && topFlag.equals("1")){
				isTop = true;
			}
			if(ids != null && ids.length > 0){
				for(String id:ids){
					Article article = this.articleService.find(id);
					article.setIsTop(isTop);
					this.articleService.update(article);
				}
			}
			ajaxResult.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxResult;
	}

}
