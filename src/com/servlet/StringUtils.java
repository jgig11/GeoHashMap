package com.servlet;

import geoHashMap.Geohash;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;



public class StringUtils {
	  final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
          '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p',
          'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	  

	  
	  final static HashMap<Character, Integer> lookup = new HashMap<Character, Integer>();
	    static {
	        int i = 0;
	        for (char c : digits) {
	            lookup.put(c, i++);
	        }
	    }
	  static String _startCode1="wwgq";
	  String _buffCode="";
	  String[] _reutrnCode =new String[32];
	  String[] _reutrnCode1 =new String[1024];
	  int i=0;
	  int j=0;
	  int k=0;
	  public String[] getCodeStr(String _startCode){
		  for (char c : digits) {
			  _reutrnCode[i]=_startCode+c;
				  for (char d : digits) {
					  _reutrnCode1[j]=_reutrnCode[i]+d;
					  j++;
				  }
			  i++;
	        }
		  return _reutrnCode1;
	  }
	  
	   String[] returnstr =new String[1024];
	  public  Map<String,Object> getReturnStr(String _startCode){
		  if(_startCode==""||_startCode.length()<0){
			  _startCode=_startCode1;
		  }
		  Map<String,Object> map=new HashMap<String,Object>();
		  StringUtils s=new StringUtils();
		  String[] arr=s.getCodeStr(_startCode);
		  String centerLat="";
		  String centercode=_startCode+"7z";
		  List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		  for (String s1:arr) {
			  Map<String,String > map1=new HashMap<String,String >();
			  double[] latlon = new Geohash().decode(s1);
			  DecimalFormat df = new DecimalFormat("0.00000");
			  returnstr[k]="[["+Double.parseDouble(df.format(latlon[3]))+","+Double.parseDouble(df.format(latlon[0]))+"],["+Double.parseDouble(df.format(latlon[1]))+","+Double.parseDouble(df.format(latlon[0]))+"],["+Double.parseDouble(df.format(latlon[1]))+","+Double.parseDouble(df.format(latlon[2]))+"],["+Double.parseDouble(df.format(latlon[3]))+","+Double.parseDouble(df.format(latlon[2]))+"]]";
			  
			  double latmin = latlon[0];
		      double latmax = latlon[2];
		      double lonmin = latlon[1];
		      double lonmax = latlon[3];
			  double latmid = (latmin+latmax)/2;
		      double lonmid = (lonmin+lonmax)/2;
		      if(s1.equals(centercode)){
		    	  centerLat="["+lonmid+","+latmid+"]"; 
		      }
			  map1.put("latlon", returnstr[k]);
			  map1.put("marker", s1);
			  map1.put("mid", "["+lonmid+","+latmid+"]");
			  list.add(map1);
			  k++;
			  
		  }
		  map.put("center",centerLat); 
		  map.put("list", list);
		  return map;
	  }
	  public  Map<String,Object> getReturnStr(List<String> listRes){
		  Map<String,Object> map=new HashMap<String,Object>();
		  List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		  String centerLat="";
		  for (String s1:listRes) {
			  Map<String,String > map1=new HashMap<String,String >();
			  double[] latlon = new Geohash().decode(s1);
			  DecimalFormat df = new DecimalFormat("0.00000");
			  returnstr[k]="[["+Double.parseDouble(df.format(latlon[3]))+","+Double.parseDouble(df.format(latlon[0]))+"],["+Double.parseDouble(df.format(latlon[1]))+","+Double.parseDouble(df.format(latlon[0]))+"],["+Double.parseDouble(df.format(latlon[1]))+","+Double.parseDouble(df.format(latlon[2]))+"],["+Double.parseDouble(df.format(latlon[3]))+","+Double.parseDouble(df.format(latlon[2]))+"]]";
			  
			  double latmin = latlon[0];
		      double latmax = latlon[2];
		      double lonmin = latlon[1];
		      double lonmax = latlon[3];
			  double latmid = (latmin+latmax)/2;
		      double lonmid = (lonmin+lonmax)/2;
			  map1.put("latlon", returnstr[k]);
			  map1.put("marker", s1);
			  map1.put("mid", "["+lonmid+","+latmid+"]");
			  list.add(map1);
			  k++;
			  
		  }
		  map.put("center",centerLat); 
		  map.put("list", list);
		  return map;
	  }
	  
	  public static void main(String[] args) {
//		[[117.38892,39.15527],[117.37793,39.15527],[117.37793,39.16077 ],[117.38892,39.16077 ]]
//		getReturnStr().get(0);
		
	}
 
	  
}
