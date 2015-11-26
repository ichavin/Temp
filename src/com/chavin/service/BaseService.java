package com.chavin.service;

import java.io.Serializable;
import java.util.List;

/**
 * 基础service接口，包含常用的方法
 * 
 * BaseService
 * @author chavin
 * @version 1.0.0
 *
 */
public interface BaseService<T extends Serializable> {

	/**
	 * 插入
	 * @param entity
	 * @throws Exception
	 */
	public int insert(T entity) throws Exception;
	
	/**
	 * 批量插入
	 * @param entitys
	 * @throws Exception
	 */
	public int batchInsert(List<T> entitys) throws Exception;
	
	/**
	 * 删除全部
	 * @throws Exception
	 */
	public int delete() throws Exception;
	
	/**
	 * 根据id删除
	 * @param id
	 * @throws Exception
	 */
	public int deleteById(Integer id) throws Exception;

	/**
	 * 根据实体删除
	 * @param entity
	 * @throws Exception
	 */
	public int deleteByEntity(T entity) throws Exception;
	
	
	/**
	 * 跟据实体利用in操作批量删除
	 * @param entity
	 * @throws Exception
	 */
	public int batchDeleteByEntity(T entity) throws Exception;
	
	/**
	 * 根据实体修改
	 * @param entity
	 * @throws Exception
	 */
	public int updateByEntity(T entity) throws Exception;
	
	/**
	 * 根据实体集合批量修改
	 * @param entity
	 * @throws Exception
	 */
	public int batchUpdateByEntity(T entity) throws Exception;
	
	/**
	 * 根据id查询实体对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public T findById(Integer id) throws Exception;
	
	/**
	 * 根据实体查询对象
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public T findByEntity(T entity) throws Exception;
	
	/**
	 * 根据集合获取集合
	 * @param entitys
	 * @return
	 * @throws Exception
	 */
	public List<T> findListByEntity(T entity) throws Exception;
	
}
