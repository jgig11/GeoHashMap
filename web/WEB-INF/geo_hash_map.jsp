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
	  <input type="button" id="init" value="init" onclick="init()"/>
	  <input type="button" id="des" value="des" onclick="des()"/>
	  <input type="button" id="scope" value="scope" onclick="scope()"/></span>
  <div>
        lng:<input type="text" id="lngX" name="lngX" />
	    lat:<input type="text" id="latY" name="latY" />
	    <input type="button" id="convert" value="convert" onclick="convert()"/>
	    <input type="button" id="reset" value="reset" onclick="reset()"/><br>
  </div>
</div>

<script type="application/javascript" src="js/mapUtils.js"></script>
<script>

</script>
</body>
</html>
