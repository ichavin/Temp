package com.chavin.dao;

import java.util.List;

/**
 * 基础dao接口，包含常用的方法
 * 
 * BaseDao
 * 时间：2015年10月28日-下午7:02:09 
 * @version 1.0.0
 *
 */
public interface BaseMapper<T> {

	/**
	 * 插入
	 * @param entity
	 * @throws Exception void<br/>
	 * @exception 
	 * @since  1.0.0
	 */
	public void insert(T entity) throws Exception;
	
	/**
	 * 批量插入 
	 * @param entitys void<br/>
	 * @exception 
	 * @since  1.0.0
	 */
	public void batchInsert(List<T> entitys);
	
	/**
	 * 删除全部 
	 * @throws Exception void<br/>
	 * @exception 
	 * @since  1.0.0
	 */
	public void delete() throws Exception;
	
	/**
	 * 根据id删除 
	 * @param id
	 * @throws Exception void<br/>
	 * @exception 
	 * @since  1.0.0
	 */
	public void deleteById(Integer id) throws Exception;
	
	/**
	 * 根据String类型的key删除 
	 * @param key
	 * @throws Exception void<br/>
	 * @exception 
	 * @since  1.0.0
	 */
	public void deleteByKey(String key) throws Exception;
	
	/**
	 * 根据实体删除 
	 * @param entity
	 * @throws Exception void<br/>
	 * @exception 
	 * @since  1.0.0
	 */
	public void deleteByEntity(T entity) throws Exception;
	
	/**
     * 根据ids利用in操作批量删除
	 * @param ids
	 * @throws Exception void<br/>
	 * @exception 
	 * @since  1.0.0
	 */
	public void batchDeleteByIds(List<Integer> ids) throws Exception;

	/**
     * 根据keys利用in操作批量删除
	 * @param keys
	 * @throws Exception void<br/>
	 * @exception 
	 * @since  1.0.0
	 */
	public void batchDeleteByKeys(List<String> keys) throws Exception;
	
	/**
	 * 根据实体利用in操作批量删除
	 * @param entitys
	 * @throws Exception void<br/>
	 * @exception 
	 * @since  1.0.0
	 */
	public void batchDeleteByEntitys(List<T> entitys) throws Exception;
	
	/**
	 * 根据id进行修改
	 * @param id
	 * @param entity
	 * @throws Exception void<br/>
	 * @exception 
	 * @since  1.0.0
	 */
	public void updateById(Integer id, T entity) throws Exception;
	
	/**
	 * 根据key进行修改
	 * @param key
	 * @param entity
	 * @throws Exception void<br/>
	 * @exception 
	 * @since  1.0.0
	 */
	public void updateByKey(String key, T entity) throws Exception;
	
	/**
	 * 根据实体修改
	 * @param entity
	 * @throws Exception void<br/>
	 * @exception 
	 * @since  1.0.0
	 */
	public void updateByEntity(T entity) throws Exception;
	
	/**
	 * 根据实体集合批量修改
	 * @param entitys
	 * @throws Exception void<br/>
	 * @exception 
	 * @since  1.0.0
	 */
	public void batchUpdateByEntitys(List<T> entitys) throws Exception;
	
	/**
	 * 根据ids集合批量修改
	 * @param ids
	 * @param entity
	 * @throws Exception void<br/>
	 * @exception 
	 * @since  1.0.0
	 */
	public void batchUpdateByIds(List<Integer> ids, T entity) throws Exception;
	
	/**
	 * 根据keys集合批量修改
	 * @param keys
	 * @param entity
	 * @throws Exception void<br/>
	 * @exception 
	 * @since  1.0.0
	 */
	public void batchUpdateByKeys(List<String> keys, T entity) throws Exception;
	
	/**
	 * 根据id查询实体对象
	 * @param id
	 * @return
	 * @throws Exception T<br/>
	 * @exception 
	 * @since  1.0.0
	 */
	public T findById(String id) throws Exception;
	
	/**
	 * 根据key查询实体对象
	 * @param key
	 * @return
	 * @throws Exception T<br/>
	 * @exception 
	 * @since  1.0.0
	 */
	public T findByKey(String key) throws Exception;
	
	/**
	 * 根据实体查询对象
	 * @param entity
	 * @return
	 * @throws Exception T<br/>
	 * @exception 
	 * @since  1.0.0
	 */
	public T findByEntity(T entity) throws Exception;
	
	/**
	 * 根据ids查询<br/>
	 * @param ids
	 * @return
	 * @throws Exception T<br/>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<T> findListByIds(List<Integer> ids) throws Exception;
	
	/**
	 * 根据keys查询<br/>
	 * @param keys
	 * @return
	 * @throws Exception T<br/>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<T> findListByKeys(List<String> keys) throws Exception;
	
	/**
	 * 根据集合获取集合
	 * @param entitys
	 * @return
	 * @throws Exception List<T><br/>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<T> findListByEntitys(List<T> entitys) throws Exception;
	
}
