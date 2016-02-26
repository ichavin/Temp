package com.chavin.service;

import java.util.List;

import com.chavin.po.SchemeMeterial;
import com.core.exception.CustomException;

public interface SchemeMeterialService extends BaseService<SchemeMeterial>{

	public void operSchemeMerterial(Integer schemeId, List<SchemeMeterial> schemeMeterialList) throws Exception;
	
}
