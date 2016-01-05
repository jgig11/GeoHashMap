<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>Geohash</title>
    <link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
    <script src="http://webapi.amap.com/maps?v=1.3&key=f47211eed76dbc1bdb5b22ccbbdf13d5"></script>
    <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
        <script type="text/javascript" src="jquery-1.8.2.min.js"></script>
</head>
<body>

<div id="container"></div>


 


   
  <div class="button-group">
  <span>  
  Geohash:<input type="text" id="content" name="content" />
  <input type="button" id="button" value="init" onclick="init()"/> <input type="button" id="button" value="des" onclick="des()"/><input type="button" id="button" value="scope" onclick="scope()"/></span>
  
  <div>
        lng:<input type="text" id="lngX" name="lngX" />lat:<input type="text" id="latY" name="latY" /><input type="button" id="button" value="convert" onclick="convert()"/><input type="button" id="button" value="reset" onclick="reset()"/><br>
  </div>
</div>

<script>
var local = window.location;  
var contextPath = local.pathname.split("/")[1];  
var basePath = local.protocol+"//"+local.host+"/"+contextPath;  
var center=[117.392957, 39.160411];
   function init(){
    	$.ajax({
  			type : "get",
  			url : basePath+"/init",
  			dataType : "json",
  			data : { arr : $("#content").val(),lng:$('#lngX').val(),lat:$('#latY').val()},
  			async : false,
  			success : function(data){
  				map(data);
  			}
  		});
    }
   function scope(){
	  if($('#lngX').val()==""){
		  alert("please click map get latitude-longitude first!");
	  }
   	$.ajax({
 			type : "get",
 			url : basePath+"/scope",
 			dataType : "json",
 			data : { arr : $("#content").val(),lng:$('#lngX').val(),lat:$('#latY').val()},
 			async : false,
 			success : function(data){
 				map(data);
 			}
 		});
   }
   
   function convert(){
	   
   	$.ajax({
 			type : "get",
 			url : basePath+"/convert",
 			dataType : "text",
 			data : {lng:$('#lngX').val(),lat:$('#latY').val()},
 			async : false,
 			success : function(data){
 				 $('#content').val(data);
 			}
 		});
   }
   function des(){
	   $("#content").val(null);
	   $('#lngX').val(null);
	   $('#latY').val(null);
	   var map = new AMap.Map('container', {
	       resizeEnable: true,
	       center: center,//地图中心点
	       zoom: 15
	   });
	   var lngX = $('#lngX'),latY = $('#latY');
	   map.on( 'click',  function (e) {
		   $('#lngX').val(e.lnglat.getLng());
		   $('#latY').val(e.lnglat.getLat());
	   });
   }
   function reset(){
	   $('#lngX').val(null);
	   $('#latY').val(null);
   }
   function map(data){
	   center =eval(data.center);
	   var map = new AMap.Map('container', {
	       resizeEnable: true,
	       center: center ,//地图中心点
	       zoom: 15
	   });
	   var lngX = $('#lngX'),latY = $('#latY');
	   map.on( 'click',  function (e) {
		   $('#lngX').val(e.lnglat.getLng());
		   $('#latY').val(e.lnglat.getLat());
	   });
	   var list=data.list;
	   $(list).each(function(i,obj){
		var Polygon=  new AMap.Polygon(
				  {
			    	path: eval(obj.latlon),
			    	strokeColor: "#00ffff",
			    	strokeOpacity: 1,
			    	strokeWeight: 1,
			    	fillColor: "#1791fc",
			    	fillOpacity: 0.25
	    		});
		Polygon.setMap(map);
		Polygon.on( 'click',  function (e) {
			   $('#lngX').val(e.lnglat.getLng());
			   $('#latY').val(e.lnglat.getLat());
			}); 
		  new AMap.Marker(
				  {
				  map: map,position: eval(obj.mid),
				  offset: new AMap.Pixel(-20,-10 ),
				  draggable: false,
				  content: list[i].marker
				 }
			);
	
	   });
	 
   } 
   $(document).ready(function(){
	   var map = new AMap.Map('container', {
		    resizeEnable: true,
		    center: center,//地图中心点
		    zoom: 15
		});
		var lngX = $('#lngX'),latY = $('#latY');
		map.on( 'click',  function (e) {
			   $('#lngX').val(e.lnglat.getLng());
			   $('#latY').val(e.lnglat.getLat());
		});
   });
   
</script>
</body>
</html>
