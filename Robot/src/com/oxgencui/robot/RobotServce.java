package com.oxgencui.robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 对图灵机器人进行了二次开发。通过请求question问题，来获取图林网站的资源(暂时是字符串,后期可以是图片，歌曲等其他资源)。
 * */

public class RobotServce {
	private static RobotServce instance = null;
	private static String APIKEY = null;
	
	private RobotServce() {
	}
	
	public static RobotServce getRobotInstance(String APIKEY) {
		RobotServce.APIKEY = APIKEY;
		if (instance == null) {
			return new RobotServce();
		}
		return instance;
	}
	
	public void getResouceByRobot_2(String question,Robot2Listener robot2Listener) {
		//Robot2Listener这个listner是有两种结果的，获取成功和失败
		String robotUrl = "http://www.tuling123.com/openapi/api?key="+APIKEY+ "&info="+question;
		URL url;
		HttpURLConnection httpURLConnection = null;
		InputStream inputStream = null;
		try {
			url = new URL(robotUrl);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setConnectTimeout(8000);
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.connect();
			inputStream = httpURLConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
			}
			robot2Listener.onDo(dealJsonText(stringBuffer.toString()));
			inputStream.close();
		} catch (MalformedURLException e1) {
			robot2Listener.onFail(e1);
		} catch (IOException e) {
			robot2Listener.onFail(e);
		} finally {
			httpURLConnection.disconnect();
		}
	} 

	public void getResouceByRobot_1(String question,Robot1Listener robot1Listener) {
		//Robot1Listener这个listner只有获取成功
		String robotUrl = "http://www.tuling123.com/openapi/api?key="+APIKEY+ "&info="+question;
		URL url;
		HttpURLConnection httpURLConnection = null;
		InputStream inputStream = null;
		try {
			url = new URL(robotUrl);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setConnectTimeout(8000);
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.connect();
			inputStream = httpURLConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
			}
			robot1Listener.onDo(dealJsonText(stringBuffer.toString()));
			inputStream.close();
		} catch (MalformedURLException e1) {
		} catch (IOException e) {
		} finally {
			httpURLConnection.disconnect();
		}
	} 
	
	
	private static String dealJsonText(String JsonText) {
		//{"code":100000,"text":"1 1"}
		if (JsonText.contains("text")) {
			return JsonText.substring(JsonText.lastIndexOf(':')+2, JsonText.length()-2);
		}
		return JsonText;
	}
	
	
}
