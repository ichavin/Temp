package com.chavin.weather;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.OutputBuffer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class weatherTest {
	
	private static String httpUrl = "http://apis.baidu.com/apistore/weatherservice/citylist";
	
	public static void main(String[] args) {
//		String httpArg = "cityname=深圳";
//		String jsonResult = request(httpUrl, httpArg);
//		JSONObject json = JSONObject.fromObject(jsonResult);
//		JSONArray retDataArr = json.getJSONArray("retData");
//		JSONObject retData = retDataArr.getJSONObject(0);
//		System.out.println("insert into area values(null,'" + retData.get("province_cn") + "','" + retData.get("district_cn") + "','" + retData.get("area_id") + "');");
		String str = getStr();
		try {
			//InputStream inputStream = new FileInputStream("F:\\province_city\\sql.txt");
			OutputStream out = new FileOutputStream("F:\\province_city\\sql.txt");
			byte[] b = str.getBytes();
			out.write(b);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("执行完毕");
	}
	
	
	public static String getStr(){
		//读取事先定义好的省市文本文件，第一行为省/直辖市，第二行为各地级市
		File provinceFile = new File("F:\\province_city\\province.txt");
		
		String sqlStr;
		StringBuilder sb = new StringBuilder();
		try {
			//定义输入流，可以使用readline()读取一行
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(provinceFile), "utf8"));
			String provinceName = "";
			
			String httpArg;
			String jsonResult;
			JSONObject retData;
			while ((provinceName = br.readLine()) != null) { //先读第一行，省的名字
				httpArg = "cityname=" + provinceName;
				jsonResult = request(httpUrl, httpArg);
				retData = JSONObject.fromObject(jsonResult).getJSONArray("retData").getJSONObject(0);
				sb.append("insert into area values(null,'" + retData.get("province_cn") + "','" + retData.get("district_cn") + "','" + retData.get("area_id") + "');");
				
				//读取第二行，该省下的所有市区
				String cityNames = br.readLine();
				//文件中的每个市区用两个空格格开，所以以两个空格分隔成市区的数组
				String[] cities = cityNames.split(" ");
				for(int j = 0; j < cities.length; j++) {
					httpArg = "cityname=" + cities[j].trim();
					jsonResult = request(httpUrl, httpArg);
					retData = JSONObject.fromObject(jsonResult).getJSONArray("retData").getJSONObject(0);
					sb.append("insert into area values(null,'" + retData.get("province_cn") + "','" + retData.get("district_cn") + "','" + retData.get("area_id") + "');");
				}
				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	
	//获取城市的区域id
	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String request(String httpUrl, String httpArg) {
		
		
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?" + httpArg;

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
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

	
	
}
