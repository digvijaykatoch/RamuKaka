<%@page import="java.util.Arrays"%>
<%@page import="com.schein.bean.LoginUser"%>
<%@page import="com.schein.bean.MasterTable"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="en">
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <%@include file="partials/head.html" %>
    
    <!-- Libs JS -->
<script src="assets/js/jquery/dist/jquery.min.js"></script>
<script src="assets/js/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
<script src="assets/js/@shopify/draggable/lib/es5/draggable.bundle.legacy.js"></script>
<script src="assets/js/autosize/dist/autosize.min.js"></script>
<script src="assets/js/chart.js/dist/Chart.min.js"></script>
<script src="assets/js/dropzone/dist/min/dropzone.min.js"></script>
<script src="assets/js/flatpickr/dist/flatpickr.min.js"></script>
<script src="assets/js/highlightjs/highlight.pack.min.js"></script>
<script src="assets/js/jquery-mask-plugin/dist/jquery.mask.min.js"></script>
<script src="assets/js/listjs/dist/list.min.js"></script>
<script src="assets/js/quill/dist/quill.min.js"></script>
<script src="assets/js/select2/dist/js/select2.min.js"></script>
<!-- build:js assets/libs/chart.js/Chart.extension.min.js -->
<script src="assets/libs/chart.js/Chart.extension.js"></script>
<!-- endbuild -->

<!-- Map -->
<script src='assets/js/mapbox-gl.js'></script>

<!-- Theme JS -->
<!-- build:js assets/js/theme.min.js -->
<script src="assets/js/polyfills.js"></script>
<script src="assets/js/demo.js"></script>
<script src="assets/js/charts.js"></script>
<script src="assets/js/charts-dark.js"></script>
<script src="assets/js/autosize.js"></script>
<script src="assets/js/dashkit.js"></script>
<script src="assets/js/dropdowns.js"></script>
<script src="assets/js/dropzone.js"></script>
<script src="assets/js/flatpickr.js"></script>
<script src="assets/js/highlight.js"></script>
<script src="assets/js/kanban.js"></script>
<script src="assets/js/list.js"></script>
<script src="assets/js/map.js"></script>
<script src="assets/js/navbar.js"></script>
<script src="assets/js/popover.js"></script>
<script src="assets/js/quill.js"></script>
<script src="assets/js/select2.js"></script>
<script src="assets/js/tooltip.js"></script>
<!-- endbuild -->

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
</style>

<style type="text/css">
/* Styles for wrapping the search box */

.main {
    width: 50%;
    margin: 50px auto;
}

/* Bootstrap 4 text input with search icon */

.has-search .form-control {
    padding-left: 2.375rem;
}

.has-search .form-control-feedback {
    position: absolute;
    z-index: 2;
    display: block;
    width: 2.375rem;
    height: 2.375rem;
    line-height: 2.375rem;
    text-align: center;
    pointer-events: none;
    color: #aaa;
}
</style>
<link rel="stylesheet" href="assets/css/all.css">
  </head>
  <body class="d-flex align-items-center bg-auth  border-primary">
  
  <%
if ((session == null) || (session.getAttribute("loginuser")==null)) 
 response.sendRedirect("index.jsp");
 
//if there is any message/error/info that should be displayed ,it will be saved in the request object and send back to this page 
 String message = "";
 if (request.getAttribute("message")!=null){
 if(request.getAttribute("controlNo")!=null){
 	message = (String)request.getAttribute("message");
 	message = message.trim();
 	}
 	} 
 
 LoginUser loginUser;
 String user = "";
 if(session.getAttribute("loginuser")!=null){
 loginUser = (LoginUser)session.getAttribute("loginuser");
 user=loginUser.getUserInfo().getUserName().toString();
 }
 else
 user=" User";
 
 List ticketList = new ArrayList();
 ticketList = (List)request.getAttribute("opentickets");
 
 %>
  <!-- CONTENT
    ================================================== -->
    <div class="container">    
      <div class="row justify-content-center">
        <div class="col-12 col-md-5 col-xl-4 my-5">
            <!-- HSI Logo -->
         
      <img alt="img" src="assets/img/covers/logo-global.gif"  class = "img-responsive" style="padding-left: 8%">
          
          <!-- Heading -->
          <h1 class="display-4 text-center mb-3">
           
            Welcome <%=user%>!
          </h1>
          
          <!-- Subheading -->
          <p class="text-muted text-center mb-5">
            Audit Vendor Discrepancy App
          </p>

        </div>
      </div> <!-- / .row -->
      
    </div> <!-- / .container -->
  </body>
</html>