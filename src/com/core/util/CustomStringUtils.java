package com.core.util;

import org.apache.commons.lang3.StringUtils;

public class CustomStringUtils {

	//Camel 标记法:首字母是小写的,接下来的单词都以大写字母开头
	//Pascal 标记法:首字母是大写的,接下来的单词都以大写字母开头
	/**
	 * 将驼峰命名转为下划线风格命名
	 * @param camelName
	 * @return
	 */
	public static String camelNameTolineName(String camelName){
		if(StringUtils.isBlank(camelName)){
			return null;
		}
		StringBuilder newLineName = new StringBuilder();
		char[] chars = camelName.toCharArray();
		newLineName.append(chars[0]);
		for(int i = 1 ; i < chars.length ; i++){
			//如果是大写
			if(Character.isUpperCase(chars[i])){
				newLineName.append("_" + Character.toLowerCase(chars[i]));
			}else{
				newLineName.append(chars[i]);
			}
		}
		return newLineName.toString();
	}
	
	public static String lineNameToCamelName(String lineName){
		if(StringUtils.isNotBlank(lineName)){
			return null;
		}
		StringBuilder newCamelName = new StringBuilder();
		char[] chars = lineName.toCharArray();
		char line = '_';
		newCamelName.append(chars[0]);
		for(int i = 1 ; i < chars.length ; i++){
			if(chars[i] == line && i < chars.length - 1){
				chars[i + 1] = Character.toUpperCase(chars[i + 1]);
			}else{
				newCamelName.append(chars[i]);
			}
		}
		return newCamelName.toString();
	}
	
	public static void main(String[] args) {
		//System.out.println(camelNameTolineName("telNumbG"));
		System.out.println(lineNameToCamelName("user_login_"));   //如果
	}
}
