package com.huiw.core.org.Controller.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONArray;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 测试类
 * 
 * @author YC
 *
 */
@Controller
public class HelloWorldHandlers {

	/**
	 * 1. 使用RequestMapping注解来映射请求的URL 2. 返回值会通过视图解析器解析为实际的物理视图,
	 * 对于InternalResourceViewResolver视图解析器，会做如下解析 通过prefix+returnVal+suffix
	 * 这样的方式得到实际的物理视图，然后会转发操作 "/WEB-INF/views/success.jsp"
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/helloworld.do")
	public void hello(HttpServletRequest req, HttpServletResponse response) throws Exception {
		StringBuilder builder = new StringBuilder();
		String aux = "";
		JSONObject json = null;
		JSONArray array=null;
		JSONObject arraytojson=null;
		while ((aux = req.getReader().readLine()) != null) {
			builder.append(aux);
		}
		String text = builder.toString();
		try {
			json = JSONObject.fromObject(text);
			array=(JSONArray) json.get("commits");
			arraytojson=(JSONObject) array.get(0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(arraytojson);
		String content="提交时间："+
		json.getJSONObject("user").get("time")+" \n提交版本："+
				json.get("after")+" \n作者："+
		json.get("user_name")+"\n提交备注："+
		arraytojson.get("message")+"\n修改内容："+
		arraytojson.get("url");
		String token="ad3a0fd1e7d91fe19a289f1c4dd98d9648e7f5ded39a5b6d32fb79afd6c0eafd";
		content = "{\"msgtype\": \"text\",\"text\": {\"content\": \""+content+"\"},\"at\": {\"isAtAll\": true}}";  
		httpsRequest("https://oapi.dingtalk.com/robot/send?access_token="+token, "POST", content);
		System.out.println("OK");
	}
	
	
	/**

	 * 发送https请求

	 */
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) throws Exception {
		HttpsURLConnection conn = null;
		BufferedReader bufferedReader = null;
		try {
			URL url = new URL(requestUrl);
			conn = (HttpsURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("content-type", "application/json");
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(outputStr.getBytes("utf-8"));
				outputStream.close();
			}
			bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			return buffer.toString();
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
				}
			}
		}
	}

}