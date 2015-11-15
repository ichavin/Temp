package com.chavin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.chavin.dao.BaseMapper;
import com.chavin.service.BaseService;

public class BaseServiceImpl<D extends BaseMapper<T>, T> implements BaseService<T> {

	protected D mapper;
	
	@Resource
	public void setMapper(D mapper) {
		this.mapper = mapper;
	}

	@Override
	public void insert(T entity) throws Exception {
		mapper.insert(entity);
	}

	@Override
	public void batchInsert(List<T> entitys) {
		mapper.batchInsert(entitys);
	}

	@Override
	public void delete() throws Exception {
		mapper.delete();
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		mapper.deleteById(id);
	}

	@Override
	public void deleteByKey(String key) throws Exception {
		mapper.deleteByKey(key);
		
	}

	@Override
	public void deleteByEntity(T entity) throws Exception {
		mapper.deleteByEntity(entity);
	}

	@Override
	public void batchDeleteByIds(List<Integer> ids) throws Exception {
		mapper.batchDeleteByIds(ids);
	}

	@Override
	public void batchDeleteByKeys(List<String> keys) throws Exception {
		mapper.batchDeleteByKeys(keys);
	}

	@Override
	public void batchDeleteByEntitys(List<T> entitys) throws Exception {
		mapper.batchDeleteByEntitys(entitys);
	}

	@Override
	public void updateById(Integer id, T entity) throws Exception {
		mapper.updateById(id, entity);
	}

	@Override
	public void updateByKey(String key, T entity) throws Exception {
		mapper.updateByKey(key, entity);
	}

	@Override
	public void updateByEntity(T entity) throws Exception {
		mapper.updateByEntity(entity);
	}

	@Override
	public void batchUpdateByEntitys(List<T> entitys) throws Exception {
		mapper.batchUpdateByEntitys(entitys);
	}

	@Override
	public void batchUpdateByIds(List<Integer> ids, T entity) throws Exception {
		mapper.batchUpdateByIds(ids, entity);
	}

	@Override
	public void batchUpdateByKeys(List<String> keys, T entity) throws Exception {
		mapper.batchUpdateByKeys(keys, entity);
	}

	@Override
	public T findById(String id) throws Exception {
		return mapper.findById(id);
	}

	@Override
	public T findByKey(String key) throws Exception {
		return mapper.findByKey(key);
	}

	@Override
	public T findByEntity(T entity) throws Exception {
		return mapper.findByEntity(entity);
	}

	@Override
	public List<T> findListByIds(List<Integer> ids) throws Exception {
		return mapper.findListByIds(ids);
	}

	@Override
	public List<T> findListByKeys(List<String> keys) throws Exception {
		return mapper.findListByKeys(keys);
	}

	@Override
	public List<T> findListByEntitys(List<T> entitys) throws Exception {
		return mapper.findListByEntitys(entitys);
	}


}
