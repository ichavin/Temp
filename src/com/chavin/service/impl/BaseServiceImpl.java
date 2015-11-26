package com.chavin.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import com.chavin.dao.BaseMapper;
import com.chavin.service.BaseService;

public class BaseServiceImpl<D extends BaseMapper<T>, T extends Serializable> implements BaseService<T> {

	protected D mapper;
	
	@Resource
	public void setMapper(D mapper) {
		this.mapper = mapper;
	}

	@Override
	public int insert(T entity) throws Exception {
		return mapper.insert(entity);
	}

	@Override
	public int batchInsert(List<T> entitys) throws Exception {
		return mapper.batchInsert(entitys);
	}

	@Override
	public int delete() throws Exception {
		return mapper.delete();
	}

	@Override
	public int deleteById(Integer id) throws Exception {
		return mapper.deleteById(id);
	}

	@Override
	public int deleteByEntity(T entity) throws Exception {
		return mapper.deleteByEntity(entity);
	}

	@Override
	public int batchDeleteByEntity(T entity) throws Exception {
		return mapper.batchDeleteByEntity(entity);
	}

	@Override
	public int updateByEntity(T entity) throws Exception {
		return mapper.updateByEntity(entity);
	}

	@Override
	public int batchUpdateByEntity(T entity) throws Exception {
		return mapper.batchUpdateByEntity(entity);
	}

	@Override
	public T findById(Integer id) throws Exception {
		return mapper.findById(id);
	}

	@Override
	public T findByEntity(T entity) throws Exception {
		return mapper.findByEntity(entity);
	}

	@Override
	public List<T> findListByEntity(T entity) throws Exception {
		return mapper.findListByEntity(entity);
	}


}
