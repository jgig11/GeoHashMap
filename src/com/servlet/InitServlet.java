package com.servlet;

import geoHashMap.Geohash;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

/**
 * Servlet implementation class init
 */
@WebServlet("/init")
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   public InitServlet() {
	        super();
	    }
    
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String arrCode=request.getParameter("arr");
		String lng=request.getParameter("lng");
		String lat=request.getParameter("lat");
		
		StringUtils MapUtil=new StringUtils();
		Map<String,Object> returnMap=new HashMap<String,Object>();
		String start;
		if(arrCode!=null&&arrCode.length()>0){
			start=arrCode.substring(0,4);
		}else{
			start=arrCode;
		}
		returnMap=MapUtil.getReturnStr(start);
		Map<String,Object > map=new HashMap<String,Object>();
		
		
		map.put("list", returnMap.get("list"));
		String center=(String)returnMap.get("center");
		 if(lng!=null&&lng!=""){
			center="["+lng+","+lat+"]";
		}
		map.put("center", center);
		
		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		PrintWriter out = response.getWriter();
	
		out.write(JSON.toJSONString(map));//注意这里向jsp输出的流，在script中的截获方法
      
		out.close();
		     
	      
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	
	
	
}
