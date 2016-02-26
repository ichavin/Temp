package com.chavin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chavin.po.Unit;
import com.chavin.service.UnitService;
import com.chavin.vo.Select;


@RequestMapping(value="/unit")
@Controller
public class UnitAction {
	
	@Resource
	private UnitService unitService;
	
	
	@RequestMapping(value = "/getUnitSelectList")
	@ResponseBody
	public List<Select> getUnitSelectList(){
		List<Select> list = new ArrayList<Select>();
		Unit entity = new Unit();
		entity.setDeleted(0);
		try {
			List<Unit> units = unitService.findListByEntity(entity);
			for(int i = 0 ; i < units.size() ; i++){
				Select select = new Select();
				select.setValue(units.get(i).getId() + "");
				select.setText(units.get(i).getName());
				list.add(select);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return list; 
		}
		return list;
	}
	
	
}
