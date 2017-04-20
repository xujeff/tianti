package com.jeff.tianti.cms.dao;

import com.jeff.tianti.common.entity.PageModel;
import java.util.List;
import com.jeff.tianti.cms.entity.ColumnInfo;
import com.jeff.tianti.cms.dto.ColumnInfoQueryDTO;
/**
 * @author xujianfang
 * @desc ColumnInfoDaoCustom接口 
 * @date 2017-03-16
 */
public interface ColumnInfoDaoCustom {

      PageModel<ColumnInfo> queryColumnInfoPage(ColumnInfoQueryDTO columnInfoQueryDTO);

      List<ColumnInfo> queryColumnInfoList(ColumnInfoQueryDTO columnInfoQueryDTO);



}