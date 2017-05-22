package com.jeff.tianti.cms.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jeff.tianti.cms.dto.ArticleQueryDTO;
import com.jeff.tianti.cms.dto.CurrentArticleInfoDTO;
import com.jeff.tianti.cms.entity.Article;
import com.jeff.tianti.common.dao.CustomBaseSqlDaoImpl;
import com.jeff.tianti.common.entity.PageModel;
/**
 * @author xujianfang
 * @desc ArticleDaoImpl类 
 * @date 2017-03-16
 */

public class ArticleDaoImpl extends CustomBaseSqlDaoImpl implements ArticleDaoCustom  {

    public PageModel<Article> queryArticlePage(ArticleQueryDTO articleQueryDTO){
    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         Map<String,Object> map = new HashMap<String,Object>();
         StringBuilder hql = new StringBuilder();
         hql.append("select t from Article t where 1=1 ");
         if(articleQueryDTO != null){
        	 if(StringUtils.isNotBlank(articleQueryDTO.getRootColumnId())){
        		 hql.append(" and t.rootColumnInfo.id = :rootColumnId ");
        		 map.put("rootColumnId", articleQueryDTO.getRootColumnId());        		 
        	 }
        	 if(StringUtils.isNotBlank(articleQueryDTO.getColumnId())){
        		 hql.append(" and t.columnInfo.id = :columnId ");
            	 map.put("columnId", articleQueryDTO.getColumnId());
        	 }
        	 if(StringUtils.isNotBlank(articleQueryDTO.getTitle())){
        		 hql.append(" and t.title like  :title ");
        		 map.put("title", "%"+articleQueryDTO.getTitle()+"%");
        	 }
        	 if(StringUtils.isNotBlank(articleQueryDTO.getPublisher())){
        		 hql.append(" and t.publisher like  :publisher ");
        		 map.put("publisher", "%"+articleQueryDTO.getPublisher()+"%");
        	 }
		 if(StringUtils.isNotBlank(articleQueryDTO.getDeleteFlag())){
        		 hql.append(" and t.deleteFlag = :deleteFlag ");
            	         map.put("deleteFlag", articleQueryDTO.getDeleteFlag());
        	 }
        	 if(StringUtils.isNotBlank(articleQueryDTO.getStartDate())){
        		 hql.append(" and t.createDate  >= :startDate ");
        		 try {
					map.put("startDate",sdf.parse(articleQueryDTO.getStartDate()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	 }
        	 if(StringUtils.isNotBlank(articleQueryDTO.getEndDate())){
        		 hql.append(" and t.createDate  <= :endDate ");
        		 try {
					map.put("endDate",sdf.parse(articleQueryDTO.getEndDate()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	 }
        	 if(StringUtils.isNotBlank(articleQueryDTO.getType())){
        		 if(articleQueryDTO.getType().equals("laji")){
        			 hql.append(" and t.deleteFlag =1 ");
        		 }else if(articleQueryDTO.getType().equals("shenhe")){
        			 hql.append(" and t.isAudit =1 ");
        		 }else if(articleQueryDTO.getType().equals("zhiding")){
        			 hql.append(" and t.isTop =1 ");
        		 }
        	 }
         }
         hql.append(" order by t.createDate desc ");
         return this.queryForPageWithParams(hql.toString(),map,articleQueryDTO.getCurrentPage(),articleQueryDTO.getPageSize());
    }

    public List<Article> queryArticleList(ArticleQueryDTO articleQueryDTO){
    	 List<Article> articleList = null;
         Map<String,Object> map = new HashMap<String,Object>();
         StringBuilder hql = new StringBuilder();
         hql.append("select t from Article t where 1=1 ");
         if(articleQueryDTO != null){
        	 if(StringUtils.isNotBlank(articleQueryDTO.getColumnId())){
        		 hql.append(" and t.columnInfo.id = :columnId ");
        		 map.put("columnId", articleQueryDTO.getColumnId());
        	 }
        	 if(StringUtils.isNotBlank(articleQueryDTO.getTitle())){
        		 hql.append(" and t.title like = :title ");
        		 map.put("title", "%"+articleQueryDTO.getTitle()+"%");
        	 }
        	 if(StringUtils.isNotBlank(articleQueryDTO.getPublisher())){
        		 hql.append(" and t.publisher like = :publisher ");
        		 map.put("publisher", "%"+articleQueryDTO.getPublisher()+"%");
        	 }
		 if(StringUtils.isNotBlank(articleQueryDTO.getDeleteFlag())){
        		 hql.append(" and t.deleteFlag = :deleteFlag ");
            	         map.put("deleteFlag", articleQueryDTO.getDeleteFlag());
        	 }
        	 if(StringUtils.isNotBlank(articleQueryDTO.getStartDate())){
        		 hql.append(" and t.createDate  >= :startDate ");
        		 map.put("startDate",articleQueryDTO.getStartDate());
        	 }
        	 if(StringUtils.isNotBlank(articleQueryDTO.getEndDate())){
        		 hql.append(" and t.createDate  <= :endDate ");
        		 map.put("endDate",articleQueryDTO.getEndDate());
        	 }
        	 if(StringUtils.isNotBlank(articleQueryDTO.getType())){
        		 if(articleQueryDTO.getType().equals("laji")){
        			 hql.append(" and t.deleteFlag =1 ");
        		 }else if(articleQueryDTO.getType().equals("shenhe")){
        			 hql.append(" and t.isAudit =1 ");
        		 }else if(articleQueryDTO.getType().equals("zhiding")){
        			 hql.append(" and t.isTop =1 ");
        		 }
        	 }
         }
         hql.append(" order by t.createDate desc ");
         if(articleQueryDTO.getTop() != null){
        	 PageModel pageModel = this.queryForPageWithParams(hql.toString(),map,0, articleQueryDTO.getTop());
        	 if(pageModel != null){
        		 articleList = pageModel.getList();
        	 }
         }else{
        	 articleList = this.queryByMapParams(hql.toString(),map);
         }
         return articleList;
    }
    
    public List<Map<String, Object>> queryStatisMapList(ArticleQueryDTO articleQueryDTO){
    	String sql = " select count(1) as totalCount,(select count(1) from cms_article where is_top=1 and delete_flag = '0') as zhidingCount,(select count(1) from cms_article where is_audit=1 and delete_flag = '0') as shenheCount,(select count(1) from cms_article where delete_flag='1') as deleteCount from cms_article a  ";
    	return this.querySqlObjects(sql);
    }
    
    public List<Article> queryNextArticleList(CurrentArticleInfoDTO currentArticleInfoDTO){
    	Map<String,Object> map = new HashMap<String,Object>();
    	StringBuilder hql = new StringBuilder();
    	hql.append(" select a from Article a where 1=1  ");
    	if(StringUtils.isNotBlank(currentArticleInfoDTO.getArticleId())){
    		hql.append(" and a.id <> :articleId ");
    		map.put("articleId", currentArticleInfoDTO.getArticleId());
    	}
    	if(StringUtils.isNotBlank(currentArticleInfoDTO.getColumnId())){
    		hql.append(" and a.columnInfo.id = :columnInfoId ");
    		map.put("columnInfoId", currentArticleInfoDTO.getColumnId());
    	}
    	if(currentArticleInfoDTO.getArticleDate() != null){
    		hql.append(" and a.createDate < :date ");
    		map.put("date", currentArticleInfoDTO.getArticleDate());
    		hql.append(" order by a.createDate desc ");
    	}else if(currentArticleInfoDTO.getOrderNo() != null){
    		hql.append(" and a.orderNo < :orderNo ");
    		map.put("orderNo", currentArticleInfoDTO.getOrderNo());
    		hql.append(" order by a.orderNo desc ");
    	}
    	return this.queryByMapParams(hql.toString(), map);
    }
    
    public List<Article> queryPreArticleList(CurrentArticleInfoDTO currentArticleInfoDTO){
    	Map<String,Object> map = new HashMap<String,Object>();
    	StringBuilder hql = new StringBuilder();
    	hql.append(" select a from Article a where 1=1  ");
    	if(StringUtils.isNotBlank(currentArticleInfoDTO.getArticleId())){
    		hql.append(" and a.id <> :articleId ");
    		map.put("articleId", currentArticleInfoDTO.getArticleId());
    	}
    	if(StringUtils.isNotBlank(currentArticleInfoDTO.getColumnId())){
    		hql.append(" and a.columnInfo.id = :columnInfoId ");
    		map.put("columnInfoId", currentArticleInfoDTO.getColumnId());
    	}
    	if(currentArticleInfoDTO.getArticleDate() != null){
    		hql.append(" and a.createDate > :date ");
    		map.put("date", currentArticleInfoDTO.getArticleDate());
    		hql.append(" order by a.createDate asc ");
    	}else if(currentArticleInfoDTO.getOrderNo() != null){
    		hql.append(" and a.orderNo > :orderNo ");
    		map.put("orderNo", currentArticleInfoDTO.getOrderNo());
    		hql.append(" order by a.orderNo asc ");
    	}
    	return this.queryByMapParams(hql.toString(), map);
    }


}
