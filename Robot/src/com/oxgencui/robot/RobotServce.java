package com.oxgencui.robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * ��ͼ������˽����˶��ο�����ͨ������question���⣬����ȡͼ����վ����Դ(��ʱ���ַ���,���ڿ�����ͼƬ��������������Դ)��
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
		//Robot2Listener���listner�������ֽ���ģ���ȡ�ɹ���ʧ��
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
		//Robot1Listener���listnerֻ�л�ȡ�ɹ�
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
