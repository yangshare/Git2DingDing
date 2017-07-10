package com.huiw.core.org.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

/**
 * 创建时间：2015-2-13 下午3:26:03
 * 
 * @author 杨成
 * @version 2.2 描述： ajax 输出信息
 */

public class AjaxUtil {

	// ajax返回text
	public static void ajaxResponse(HttpServletResponse response, String text) {
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(text);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ajaxJSON返回
	public static void ajaxJSONResponse(HttpServletResponse response, Object object) {
		try {
			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write("callback(" + json + ")");
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}