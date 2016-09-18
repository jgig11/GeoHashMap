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
