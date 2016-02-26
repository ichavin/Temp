package com.chavin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chavin.dao.MenuMapper;
import com.chavin.po.Menu;
import com.chavin.service.MenuService;

@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements MenuService{

	@Override
	public List<Menu> getMenu() throws Exception {
		Menu entity = new Menu();
		entity.setDeleted(0);
		List<Menu> menus = mapper.findListByEntity(entity);
		if(menus.isEmpty()) return null;
		List<Menu> menuList = new ArrayList<Menu>();
		for(Menu menu : menus){
			if(menu.getParentMenuCode().equals("0")){
				menuList.add(menu);
			}
		}
		Menu menu;
		Menu menu1;
		List<Menu> subList;
		for(int i = 0 ; i< menuList.size(); i++){
			menu = menuList.get(i);
			for(int j = 0 ; j < menus.size() ; j++){
				menu1 = menus.get(j);
				if(menu1.getParentMenuCode().equals(menu.getMenuCode())){
					subList = menuList.get(i).getSubList();
					if(subList == null || subList.isEmpty()) subList = new ArrayList<Menu>();
					subList.add(menu1);
					menuList.get(i).setSubList(subList);
				}
			}
		}
		return menuList;
	}
	
}
