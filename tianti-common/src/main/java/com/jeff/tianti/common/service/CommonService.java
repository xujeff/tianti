package com.jeff.tianti.common.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.jeff.tianti.common.dao.CommonDao;
/**
 * 基础Service的定义
 * @author Jeff Xu
 * @since 2015-12-09
 * @param <E>
 * @param <ID>
 */
public abstract class CommonService<E,ID extends Serializable>  {
	
	protected CommonDao<E,ID> commonDao;

	public void setCommonDao(CommonDao<E, ID> commonDao) {
		this.commonDao = commonDao;
	}

	public CommonDao<E, ID> getCommonDao() {
		return commonDao;
	}
	
	/**
	 * 根据ID获取某个Entity
	 * @param id
	 * @return
	 */
	public E get(ID id) {
		return commonDao.getOne(id);
	}

	/**
	 * 根据ID查找某个Entity（建议使用）
	 * @param id
	 * @return
	 */
	public E find(ID id) {
		return commonDao.findOne(id);
	}

	/**
	 * 获取所有的Entity列表
	 * @return
	 */
	public List<E> getAll() {
		return commonDao.findAll();
	}
	
	/**
	 * 获取Entity的总数
	 * @return
	 */
	public Long getTotalCount() {
		return commonDao.count();
	}

	/**
	 * 保存Entity
	 * @param entity
	 * @return
	 */
	public E save(E entity) {
		return commonDao.save(entity);
	}

	/**
	 * 修改Entity
	 * @param entity
	 * @return
	 */
	public E update(E entity) {
		return commonDao.save(entity);
	}
	
	/**
	 * 删除Entity
	 * @param entity
	 */
	public void delete(E entity) {
		commonDao.delete(entity);
	}

	/**
	 * 根据Id删除某个Entity
	 * @param id
	 */
	public void delete(ID id) {
		commonDao.delete(id);
	}

	/**
	 * 删除Entity的集合类
	 * @param entities
	 */
	public void delete(Collection<E> entities) {
		commonDao.delete(entities);
	}

	/**
	 * 清空缓存，提交持久化
	 */
	public void flush() {
		commonDao.flush();
	}
	
	/**
	 * 根据查询信息获取某个Entity的列表
	 * @param spec
	 * @return
	 */
	public List<E> findAll(Specification<E> spec) {
		return commonDao.findAll(spec);
	}
	
	/**
	 * 获取Entity的分页信息
	 * @param pageable
	 * @return
	 */
	public Page<E> findAll(Pageable pageable){
		return commonDao.findAll(pageable);
	}
	
	/**
	 * 根据查询条件和分页信息获取某个结果的分页信息
	 * @param spec
	 * @param pageable
	 * @return
	 */
	public Page<E> findAll(Specification<E> spec, Pageable pageable) {
		return commonDao.findAll(spec, pageable);
	}
	
	/**
	 * 根据查询条件和排序条件获取某个结果集列表
	 * @param spec
	 * @param sort
	 * @return
	 */
	public List<E> findAll(Specification<E> spec, Sort sort) {
		return commonDao.findAll(spec);
	}
	
	/**
	 * 查询某个条件的结果数集
	 * @param spec
	 * @return
	 */
	public long count(Specification<E> spec) {
		return commonDao.count(spec);
	}

}
