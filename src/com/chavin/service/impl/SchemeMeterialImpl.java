package com.chavin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.chavin.dao.SchemeMeterialMapper;
import com.chavin.po.Scheme;
import com.chavin.po.SchemeMeterial;
import com.chavin.service.SchemeMeterialService;

@Service
public class SchemeMeterialImpl extends BaseServiceImpl<SchemeMeterialMapper, SchemeMeterial> implements SchemeMeterialService {

	@Override
	public void operSchemeMerterial(Integer schemeId, List<SchemeMeterial> schemeMeterialList) throws Exception {
		SchemeMeterial entity = new SchemeMeterial();
		Scheme scheme = new Scheme();
		scheme.setId(schemeId);
		entity.setScheme(scheme);
		mapper.deleteByEntity(entity);
		if(schemeMeterialList != null && schemeMeterialList.size() >0){
			mapper.batchInsert(schemeMeterialList);
		}
	}


}
