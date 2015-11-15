package com.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil extends Properties{
	
	private static final long serialVersionUID = -3468908967921545725L;

	private static File file = null;
	private static PropertiesUtil propertiesUtil = null;
	private List<Object> keyList = new ArrayList<Object>();
	
	private static final Map<String,String> map = new HashMap<String, String>(); 
	private static final String str = "1"; 
	
	public List<Object> getKeyList() {
		return keyList;
	}

	private PropertiesUtil(){
		
	}

	//单例
	public static PropertiesUtil getInstantce(){
		if(propertiesUtil == null){
			propertiesUtil = new PropertiesUtil();
		}
		return propertiesUtil;
	}
	
	//********start**********重写Properties的put、remove、keys、putAll方法,实现顺序读取和顺序写入********start*******
	@Override
	public synchronized Object put(Object key, Object value) {
		if(!keyList.contains(key)){
			keyList.add(key);
		}
		return super.put(key, value);
	}
	@Override
	public synchronized Object remove(Object key) {
		if(keyList.contains(key)){
			keyList.remove(key);
		}
		return super.remove(key);
	}
	@Override
	public synchronized void putAll(Map values) {
		for(Object key : values.keySet()){
			if(!keyList.contains(key)){
				keyList.add(key);
			}
		}
		super.putAll(values);
	}
	@Override
	public synchronized Enumeration<Object> keys() {
		return Collections.enumeration(keyList);
	}
	
	//******start********查看文件是否存在，不存在返回false************start******************
	
	
	private boolean getFile(String path){
		String filePath = "";
		file = new File(filePath);
		return file.exists();
	}
	
	/**
	 * 通过key获取value
	 * 方法名：getValueByKey<br/>
	 * 时间：2015年7月13日-下午6:46:22 <br/>
	 * @param key
	 * @return String<br/>
	 */
	public String getValueByKey(String filePath, String key){
		if(!getFile(filePath))
			return null;
		String value = null;
		PropertiesUtil propertiesUtil = PropertiesUtil.getInstantce();
		try {
			InputStream inputStream = new FileInputStream(file);
			propertiesUtil.load(inputStream);
			value = propertiesUtil.getProperty(key);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	
	/**
	 * 写key-value到properties文件 相同的key会被覆盖 追加不同的key-value<br>
	 * 
	 * @param key 键<br>
	 * @param value 值<br>
	 */
	public void updateProperties(String filePath, String key, String value){
		PropertiesUtil propertiesUtil = PropertiesUtil.getInstantce();
		if(!getFile(filePath)){
			return;
		}
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(file);
			propertiesUtil.load(inputStream);
			propertiesUtil.setProperty(key, value);
			outputStream = new FileOutputStream(file);
			propertiesUtil.store(outputStream, "");
			outputStream.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(outputStream != null){
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
}
