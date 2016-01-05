package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import geoHashMap.Geohash;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String str =(String)request.getParameter("code");
		double[] latlon = new Geohash().decode("wwgqyc");
//        System.out.println("[["+latlon[1]+","+latlon[0]+"],["+latlon[3]+","+latlon[0]+"],["+latlon[3]+","+latlon[2]+"],["+latlon[1]+","+latlon[2]+"]]");
//        Geohash e = new Geohash();
//        String s = e.encode(39.16269,117.373774 );
//        System.out.println(s);
//        latlon = e.decode(s);
//        DecimalFormat df = new DecimalFormat("0.00000");
//        System.out.println(df.format(latlon[0]) + ", " + df.format(latlon[1]));
//        PrintWriter ut = response.getWriter();
//        ut.println("latlon"+"[["+latlon[1]+","+latlon[0]+"],["+latlon[3]+","+latlon[0]+"],["+latlon[3]+","+latlon[2]+"],["+latlon[1]+","+latlon[2]+"]]");
//        ut.println("Password:"+password);
//		response.getWriter().append("latlon: ").append("[["+latlon[1]+","+latlon[0]+"],["+latlon[3]+","+latlon[0]+"],["+latlon[3]+","+latlon[2]+"],["+latlon[1]+","+latlon[2]+"]]");
//		response.sendRedirect(request.getContextPath() +"/geo_hash_map.jsp");
        response.setContentType("text/html; charset=UTF-8");
        ServletContext sc = getServletContext();
        RequestDispatcher rd = null;
        
        rd = sc.getRequestDispatcher("/WEB-INF/geo_hash_map.jsp");
        rd.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
