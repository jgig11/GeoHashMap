package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.ucar.common.Constant;
import com.ucar.util.GeoHashUtil;
import com.ucar.vo.Point;
@WebServlet("/scope")
public class ScopeServlet extends HttpServlet  {
	private static final long serialVersionUID = 1L;
	   public ScopeServlet() {
	        super();
	    }
 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String arrCode=request.getParameter("arr");
		//lng:$('#lngX').val(),lat:$('#latY').val()
		String lng=request.getParameter("lng");
		String lat=request.getParameter("lat");
		
		Integer DISTANCE =Integer.parseInt(arrCode);
		if(DISTANCE==null||DISTANCE<0){
			DISTANCE=4000;
		}
		Point point = new Point(Double.parseDouble(lng),Double.parseDouble(lat));
		List<String> listRes = GeoHashUtil.getInstance().getGeoHashs(point, DISTANCE);
		
	
		
		StringUtils MapUtil=new StringUtils();
		Map<String,Object> returnMap=new HashMap<String,Object>();
	
		
		returnMap=MapUtil.getReturnStr(listRes);
		Map<String,Object > map=new HashMap<String,Object>();
		
		map.put("list", returnMap.get("list"));
		String center="["+lng+","+lat+"]";
	
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
