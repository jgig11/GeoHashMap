package com.servlet;

import geoHashMap.Geohash;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

/**
 * Servlet implementation class convert
 */
@WebServlet("/convert")
public class ConvertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ConvertServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String lng=request.getParameter("lng");
		String lat=request.getParameter("lat");
		Geohash e = new Geohash();
		String s ;
		if(lng==""||lng.length()<0){
			s="";
		}else{
			s = e.encode(Double.parseDouble(lat),Double.parseDouble(lng));
		}
		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		PrintWriter out = response.getWriter();
		out.write(s);//注意这里向jsp输出的流，在script中的截获方法
		out.close();
		     
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
