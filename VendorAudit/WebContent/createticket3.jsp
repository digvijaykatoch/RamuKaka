<%@page import="com.schein.bean.*"%>
<%@page import="com.schein.utils.*" %>
<%@ page import ="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <%@include file="partials/head.html" %>
<script src="assets/js/jquery/dist/jquery.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
<script src="assets/js/bootstrap-select.min.js"></script>

<link rel="stylesheet" href="assets/css/bootstrap.min.css">
<link rel="stylesheet" href="assets/css/bootstrap-select.css">
<link rel="stylesheet" href="assets/css/all.css">
<style type="text/css">
 .selectpicker
{
     width:auto !important;
     display: inline-block;
}
</style>
<style type="text/css">
.navbar {
	background-color: #f2f2f2;
	color: black;
}

/* Style the links inside the navigation bar */
.navbar a {
	float: left;
	color: black;
	text-align: center;
	text-decoration: none;
	font-size: 12px;
}

/* Change the color of links on hover */
.navbar a:hover {
	background-color: #ddd;
	color: black;
}

/* Add a color to the active/current link */
.navbar a.active {
	background-color: #4CAF50;
	color: black;
}

/*Hiding desktop element*/
@media screen and (max-width : 1920px){
  .desk{
  visibility:visible;
  }
}
@media screen and (max-width : 906px){
.desk{
  visibility:hidden;
  }
}


/*Cam for desktop Start*/


#cont{
  position: relative;

}
.son{
  position: absolute;
  top:0;
  left:0;

}




#control{
  position:absolute;

  left:0;

  z-index: 50;
  background: HoneyDew ;
  opacity:0.7;
  color:#fff;
  text-align: center;

}
#snap{
  background-color: dimgray ;

}
#retake{
  background-color: coral ;

}

#close{
  background-color: lightcoral ;

}
.hov{
  opacity:.8;
  transition: all .5s;
}
.hov:hover{
  opacity:1;

  font-weight: bolder;
}
/*#canvas{
  z-index: 1;
}
#video{
  z-index: 3;
}*/

/*Cam for desktop ends*/
</style>
<link href='https://fonts.googleapis.com/css?family=Playfair Display' rel='stylesheet'>
<!-- endbuild -->
<title>Create Ticket Screen 3</title>

<%
String ponumber = (String)request.getAttribute("ponumber");
Integer controlNo = (Integer) request.getAttribute("controlNo");
//TODO Get supplier detail as well
String supplier = (String) request.getAttribute("supplier");
LoginUser loginUser = (LoginUser) session.getAttribute("loginuser");
String editflag = (String)request.getAttribute("flow");
List fileList = (List)request.getAttribute("fileList");

//Getting user info
 String user = "";
 if(session.getAttribute("loginuser")!=null){
 loginUser = (LoginUser)session.getAttribute("loginuser");
 user=loginUser.getUserInfo().getUserName().toString();
 }
 else
 user=" User";
 %>

</head>
<body  class="d-flex align-items-center bg-auth border-primary">
<div class="container">
      <div class="row justify-content-center">
        <div class="col-12 col-md-5 col-xl-4 my-5">
        <!-- HSI Logo -->

					<div id="logo"
						style="text-align: center; font-family: 'Helvetica'; font-variant: small-caps; font-weight: bold;">
						<img alt="img" src="assets/img/covers/hsilogo.png"
							style="vertical-align: middle; text-align: left"><span style="vertical-align: middle">Vendor Receiving Observations</span>
					</div>
          
          <!-- Heading -->
           <!--   <h1 class="display-4 text-center mb-3">
           
            Welcome <%=user%>!
          </h1>--> 
          
          <!-- Subheading -->
          <!-- <p class="text-muted text-center mb-5">
            Vendor Receiving Observations  <a href="javascript:history.back()"><i class="fa fa-angle-left"></i> Back </a>  
          </p> -->
<form id="tkt1" class="border border-light p-5" method='post' <%if(editflag!=null && !"".equals(editflag)){ %> action="/VendorAudit/Report?report=editTicket4&option=editTicket4" <%} else{ %> action="/VendorAudit/Report?report=Ticket4&option=ticket4" <%} %> enctype="multipart/form-data">
        
<!-- <input type="hidden" name="report" value="Ticket4">
<input type="hidden" name="option" value="ticket4"> -->
<input type="hidden" name="editFlag" id="editFlag" value="<%=editflag%>">
<input type="hidden" id="controlNo" name="controlNo" value="<%=controlNo%>">
<input type="hidden" name="ponumber" value="<%=ponumber%>">
<input type="hidden" name="save" id="save" value="">
    <p class="h4 mb-4 text-center">Control <%=controlNo%>   <a href="/VendorAudit/Report?report=backToMain&option=backToMain" style="float: left;"><i class="fa fa-home"></i></a><a href="Login?action=logoff" style="float: right;"> <i class="fas fa-sign-out-alt"></i></a></p>
    
    <p align="center">
	<button class="btn btn-info btn-sm" style="width: 25%; float: left;" type="button" onclick="backToSearch()">Previous</button>
	<button class="btn btn-info btn-sm" style="width: 20%; float: inherit;" onclick="setSaveFlag()" type="button">Save</button>
    <button class="btn btn-info btn-sm" style="width: 20%; float: right" type="submit">Next</button>
    </p>
     
    <label for="input-2"> Attachment(s)
                            (Attach multiple files.)</label>
    <a class='btn btn-info' href='javascript:$("#input-2").click();' style="float:right">
            <i class="fas fa-upload"></i>
 
   <input id="input-2" name="input2[]" type="file" class="file"
   style='position:absolute;z-index:2;top:0;left:0;filter: alpha(opacity=0);-ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";opacity:0;background-color:#4CAF50;color:#4CAF50;'
   multiple data-show-preview="false" capture="camera" onchange="preview_images();">
            
        </a>
    <br><br>
    <label for="open" class="desk"> Or take photo </label>
    <a class='btn btn-info desk' href='javascript:$("#open").click();' style="float: right">
            <i class="fas fa-camera"></i>
 
   <input id="open" name="open" type="button" class="desk"
   style='position:absolute;z-index:2;top:0;left:0;filter: alpha(opacity=0);-ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";opacity:0;background-color:#4CAF50;color:#4CAF50;'
   >
            
        </a>
    <div id="file-upload-filename"></div>
    
 	<div class="row" id="image_preview"></div>
    
	<br>
	<p align="center">
	<button class="btn btn-info btn-sm" style="width: 25%; float: left;" type="button" onclick="backToSearch()">Previous</button>
	<button class="btn btn-info btn-sm" style="width: 20%; float: inherit;" onclick="setSaveFlag()" type="button">Save</button>
    <button class="btn btn-info btn-sm" style="width: 20%; float: right" type="submit">Next</button>
    </p>
    <%if(!fileList.isEmpty()){ %>
    <div id="prevuploads" name="prevuploads">
    <h4>Previously Uploaded
          </h4>
    <ul class="list-group">
    <%for(int i=0; i< fileList.size(); i++) {%>
    	<li class="list-group-item"><%=fileList.get(i).toString().substring(23)%></li>
    <%} %>
    </ul>
    </div>
    <%} %>
    
</form>

<!-- Section only visible on desktop -->
    <div class="desk">
          <div class="row">
            <div class="col-md- offset-1">
	 <div id="wrap">

              <div id='cont'>

                <div id="vid" class='son' >
              	<video id='video'></video>
                </div>

                <div id="capture" class='son'>
              <canvas id='canvas' width="300" height="600"></canvas>
              <canvas id='blank' style='display:none;'></canvas>
                </div>

                <div id="control">
                  <div class="container">
                  <div class="row">
                    <a id='retake' class='btn'><i class="fas fa-sync-alt"></i></a>
                    <a id='snap' class='btn'><i class="fas fa-camera"></i></a>
                    <a id='close' class='btn'><i class="fas fa-times"></i></a>
                    <a id='imgsave' class='btn'><i class="fad fa-save"></i></a>
                  </div>
                    </div>

                </div>

              </div>

              </div>

            </div>
          </div>
    </div>
    <!-- Section only visible on desktop ends -->
        </div>
      </div> <!-- / .row -->
      
    <%--   <div class="row justify-content-center">
			<div class="col-12 col-md-5 col-xl-4 my-5">
				<%@include file="partials/bottomnav.html" %>
			</div>
		</div> --%>
		<!-- / .row -->

    </div> <!-- / .container -->
    <script type="text/javascript">
    $(document).ready(function() {
 	$('.selectpicker').selectpicker();
                  
});
function myFunction() {
  var x = document.getElementById("myNavbar");
  if (x.className === "navbar") {
    x.className += " responsive";
  } else {
    x.className = "navbar";
  }
}

function setSaveFlag(){
var frm = document.getElementById("tkt1");
var saveFlag = document.getElementById("save");
var editFlag = document.getElementById("editFlag");
saveFlag.value = "Yes";
if(editFlag!=null)
frm.action = "/VendorAudit/Report?report=saveTicket3&option=editTicket4";
else
frm.action = "/VendorAudit/Report?report=saveTicket3&option=Ticket4";
frm.submit();
}

function backToSearch(){
var frm = document.getElementById("tkt1");
var controlNo = document.getElementById("controlNo").value;
frm.action = "/VendorAudit/Report?report=editTicket2&option=editTicket2&controlNo="+controlNo+"&prev=Yes";
frm.submit();
}

 $(document).ready(function() {
 $('#imgsave').on('click', function(){
 
 window.open(canvas.toDataURL('image/png'));
    var gh = canvas.toDataURL('png');

    var a  = document.createElement('a');
    a.href = gh;
    a.download = 'image.png';

    a.click()
 
 	});
 
 	$('#input-2').on('change', function() {
 	var input = document.getElementById( 'input-2' );
	var infoArea = document.getElementById( 'file-upload-filename' );
  
  // the input has an array of files in the `files` property, each one has a name that you can use. We're just using the name here.
 	 var files = input.files;
  	var fileName = '';
  
  for(var i=0; i<files.length; i++){
    fileName = fileName + "<br>"  + files[i].name;
  }
  
  // use fileName however fits your app best, i.e. add it into a div
  fileName = '<br><b>File name: </b><br>' + fileName;
  infoArea.innerHTML  = fileName;
 	
 	});
 	
 	
 	
  $('#control').hide();
  $('#video').resize(function(){
    $('#cont').height($('#video').height());
      $('#cont').width($('#video').width());
      $('#control').height($('#video').height()*0.1);
      $('#control').css('top',$('#video').height()*0.9 );
        $('#control').width($('#video').width());
        $('#control').show();
});
function opencam(){
  navigator.getUserMedia= navigator.getUserMedia ||   navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.oGetUserMedia || navigator.msGetUserMedia ;
  if(navigator.getUserMedia)
  {
    navigator.getUserMedia({video:true },  streamWebCam ,throwError) ;


  }

}

function closecam(){

  video.pause();

  try {
    video.srcObject = null;
  } catch (error) {
    video.src =null;
  }

  var track = strr.getTracks()[0];  // if only one media track
  // ...
  track.stop();

}
  var video= document.getElementById('video');
  var canvas= document.getElementById('canvas');
  var context= canvas.getContext('2d');
  var strr;
  function streamWebCam(stream){
  const  mediaSource = new MediaSource(stream);
  try {
      video.srcObject = stream;
    } catch (error) {
      video.src = URL.createObjectURL(mediaSource);
    }
    video.play();
    strr=stream;
  }
  function throwError(e){
    alert(e.name);
  }
$('#open').click(function(event) {
  opencam();
  $('#wrap').show();
   $('#control').show();
});
$('#close').click(function(event) {
  closecam();
  $('#wrap').hide();
});
  $('#snap').click(function(event) {
      canvas.width=video.clientWidth;
      canvas.height=video.clientHeight;
      context.drawImage(video,0,0);
      $('#vid').css('z-index','20');
      $('#capture').css('z-index','30');
      var dataURL = canvas.toDataURL("image/png");
      $('#imgsave').click();
      
  });
$('#retake').click(function(event) {
$('#vid').css('z-index','30');
$('#capture').css('z-index','20');
});
                  
});

function preview_images() 
{
 var total_file=document.getElementById("input-2").files.length;
 
 $('#image_preview').empty();
 $('#image_preview').append("<div class='col-md-3'><label> Preview </label></div>");
 for(var i=0;i<total_file;i++)
 {
  $('#image_preview').append("<div class='col-md-3'><img class='img-responsive' src='"+URL.createObjectURL(event.target.files[i])+"'></div><br>");
 }
}

    </script>
</body>
</html>