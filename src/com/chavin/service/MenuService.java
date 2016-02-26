package com.chavin.service;

import java.util.List;

import com.chavin.po.Menu;

public interface MenuService extends BaseService<Menu>{

	public List<Menu> getMenu() throws Exception;
	
}
