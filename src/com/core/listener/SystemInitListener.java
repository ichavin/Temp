package com.core.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.core.util.PropertiesUtil;


/**
 * 系统初始化
 */
public class SystemInitListener implements ServletContextListener {

	private static String realPath = null;
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		realPath = servletContextEvent.getServletContext().getContextPath();
		String path = SystemInitListener.class.getClassLoader().getResource("customCodeMsg.properties").getPath();
		initProperties(path,Global.CUSTOM_CODE_MSG);
	}
	
	/**
	 * 初始化properties文件
	 */
	private void initProperties(String filePath,Map<String,String> map){
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(filePath));
			PropertiesUtil prop = PropertiesUtil.getInstantce();
			prop.load(inputStream);
			if(prop != null){
				List<Object> keyList = prop.getKeyList();
				map.clear();
				for(int i = 0 ; i < keyList.size() ; i++){
					map.put((String)keyList.get(i), (String) prop.get(keyList.get(i)));
				}
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
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
