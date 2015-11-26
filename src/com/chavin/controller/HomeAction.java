package com.chavin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chavin.po.Menu;
import com.chavin.service.MenuService;
import com.core.util.WeatherUtil;

@RequestMapping(value = "/home")
@Controller
public class HomeAction {
	
	@Resource
	private MenuService menuService;
	
	private static List<Menu> menuList = new ArrayList<Menu>();

	@RequestMapping(value="/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");
		if(menuList.isEmpty()){
			try {
				menuList = menuService.getMenu();
			} catch (Exception e) {
				e.printStackTrace();
				menuList = new ArrayList<Menu>();
			}
		}
		modelAndView.addObject("menuList", menuList);
		return modelAndView;
	}
	
	@RequestMapping(value = "/getServerTime")
	@ResponseBody
	public String getServerTime(HttpServletRequest request, HttpServletResponse response){
		return new Date().getTime() + "";
	}
	
	@RequestMapping(value = "/getWeather")
	@ResponseBody
	public Map<String, Object> getWeather(HttpServletRequest request, HttpServletResponse response, String city_name){
		String str = WeatherUtil.getWeather(city_name);
		Map<String,Object> map = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(str).getJSONObject("retData");
		map.put("weather", json.get("weather"));
		map.put("l_tmp", json.get("l_tmp"));
		map.put("h_tmp", json.get("h_tmp"));
		map.put("temp", json.get("temp"));
		
		//定义拼音输出格式
		HanyuPinyinOutputFormat hof = new HanyuPinyinOutputFormat();
		//大写
		hof.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		//不包含声调
		hof.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		//u用V表示
		hof.setVCharType(HanyuPinyinVCharType.WITH_V);
		
		char[] characters = json.get("weather").toString().toCharArray();
		String imgName = "";
		try {
			for(int i = 0 ; i < characters.length ; i++){
				imgName += PinyinHelper.toHanyuPinyinStringArray(characters[i], hof)[0];
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		if("无".equals(characters[0])){
			imgName = "undefined";
		}
		map.put("imgName", imgName);
		return map;
	}
	
	@RequestMapping(value = "/getMenu")
	@ResponseBody
	public void getMenu(HttpServletRequest request, HttpServletResponse response){
		
	}

}
