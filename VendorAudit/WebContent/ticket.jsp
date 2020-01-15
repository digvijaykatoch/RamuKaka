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
<title>Insert title here</title>
<%
List ticketList = (List) request.getAttribute("ticketList");
String ponumber = (String) request.getAttribute("ponumber");
LoginUser loginUser = (LoginUser) session.getAttribute("loginuser");
 %>
</head>
<body  class="d-flex align-items-center bg-auth  border-primary">
	<!-- CONTENT
    ================================================== -->
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-12 col-md-5 col-xl-4 my-5">
				<!-- HSI Logo -->
				<img alt="img" src="assets/img/covers/logo-global.gif" class="img-responsive"
					style="padding-left: 8%">
				<!-- Heading -->
				<h1 class="display-4 text-center mb-3">Welcome User!</h1>
				<!-- Subheading -->
				<p class="text-muted text-center mb-5">Audit Vendor Discrepancy App <a href="javascript:history.back()"><i class="fa fa-angle-left"></i> Back </a></p>
				<div class="table-responsive" data-toggle="lists"
					data-options='{"valueNames": ["tables-row", "tables-first", "tables-last", "tables-handle"]}'>
					<table class="table">
						<thead>
							<tr>
								<th scope="col"><a href="#" class="text-muted sort" data-sort="tables-first">Control
										Number</a></th>
								<th scope="col"><a href="#" class="text-muted sort" data-sort="tables-handle">Analyst</a>
								</th>
								<th scope="col"><a href="#" class="text-muted sort" data-sort="tables-handle">Observe
										Date</a></th>
								<th scope="col"><a href="#" class="text-muted sort" data-sort="tables-handle">Status</a>
								</th>
							</tr>
						</thead>
						<tbody class="list">
							<%
												  if (!ticketList.isEmpty()) {
												  MasterTable ticketBean = new MasterTable();
												   for (int i = 0; i < ticketList.size(); i++) {
												   ticketBean =(MasterTable) ticketList.get(i);
												  %>
							<tr>
								<td class="tables-first"><a href='/VendorAudit/Report?report=viewTicket&option=viewTicket&controlNo=<%=ticketBean.getControlNo()%>'><%=ticketBean.getControlNo()%></a></td>
								<td class="table-handle"><%=ticketBean.getAnalyst()%></td>
								<td class="table-handle"><%=ticketBean.getObserveDte()%></td>
								<td class="table-handle"><%=ticketBean.getStatus()%></td>
							</tr>
							<%
												  }
												  }
												%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!-- / .row -->
		
	<%-- 	<div class="row justify-content-center">
			<div class="col-12 col-md-5 col-xl-4 my-5">
				<%@include file="partials/bottomnav.html" %>
			</div>
		</div> --%>
		<!-- / .row -->

	</div>
	<!-- / .container -->
	<script type="text/javascript">
	function myFunction() {
  var x = document.getElementById("myNavbar");
  if (x.className === "navbar") {
    x.className += " responsive";
  } else {
    x.className = "navbar";
  }
}
	</script>
</body>
</html>