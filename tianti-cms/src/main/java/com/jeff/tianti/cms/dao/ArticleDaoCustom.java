package com.jeff.tianti.cms.dao;

import com.jeff.tianti.common.entity.PageModel;
import java.util.List;
import java.util.Map;

import com.jeff.tianti.cms.entity.Article;
import com.jeff.tianti.cms.dto.ArticleQueryDTO;
import com.jeff.tianti.cms.dto.CurrentArticleInfoDTO;
/**
 * @author xujianfang
 * @desc ArticleDaoCustom接口 
 * @date 2017-03-16
 */
public interface ArticleDaoCustom {

      PageModel<Article> queryArticlePage(ArticleQueryDTO articleQueryDTO);

      List<Article> queryArticleList(ArticleQueryDTO articleQueryDTO);

      List<Map<String, Object>> queryStatisMapList(ArticleQueryDTO articleQueryDTO);
      
      List<Article> queryNextArticleList(CurrentArticleInfoDTO currentArticleInfoDTO);
      
      List<Article> queryPreArticleList(CurrentArticleInfoDTO currentArticleInfoDTO);

}