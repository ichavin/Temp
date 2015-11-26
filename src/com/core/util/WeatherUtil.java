package com.core.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

//获取天气
public class WeatherUtil {

	public static String getWeather(String Name){
		String json = getWeatherByAreaId(0,Name,null);
		String areaId = (String) JSONObject.fromObject(json).getJSONObject("retData").get("cityCode");
		return getWeatherByAreaId(1,null,areaId);
	}
	
	
	//根据城市区域id获取天气
	private static String getWeatherByAreaId(int flag, String Name, String areaId){
		String httpUrl;
		if(flag == 0){
			httpUrl = "http://apis.baidu.com/apistore/weatherservice/cityinfo?cityname=" + Name;
		}else{
			httpUrl = "http://apis.baidu.com/apistore/weatherservice/cityid?cityid=" + areaId;
		}
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
	        // 填入apikey到HTTP header
	        connection.setRequestProperty("apikey",  "ae63c7f890264bb3207a1adc4f05df2d");
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return result;
	}

	public static void main(String[] args) {
		JSONObject json = JSONObject.fromObject(getWeather("深圳"));
		System.out.println(json);
	}
}