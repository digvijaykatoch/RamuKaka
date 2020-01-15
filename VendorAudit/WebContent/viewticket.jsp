<%@page import="java.util.ArrayList"%>
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
<title>View Ticket</title>
<%
String controlNo = (String) request.getAttribute("controlNo");
String ponumber = (String) request.getAttribute("ponumber");
LoginUser loginUser = (LoginUser) session.getAttribute("loginuser");
MasterTable mbean = (MasterTable) request.getAttribute("mbean");

List conversationList = new ArrayList();
List attachmentList = new ArrayList();
String issues = "";

conversationList=(List) request.getAttribute("conversationList");
attachmentList =(List) request.getAttribute("attachmentList");
issues = (String) request.getAttribute("issues");
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
				<p class="text-muted text-center mb-5">View Ticket <a href="javascript:history.back()"><i class="fa fa-angle-left"></i> Back </a></p>
				
				<label><b>Ticket Detail</b></label>				
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
												 
												  MasterTable ticketBean = new MasterTable();
												   ticketBean =(MasterTable) mbean;
												  %>
							<tr>
								<td class="tables-first"><%=ticketBean.getControlNo()%></td>
								<td class="table-handle"><%=ticketBean.getAnalyst()%></td>
								<td class="table-handle"><%=ticketBean.getObserveDte()%></td>
								<td class="table-handle"><%=ticketBean.getStatus()%></td>
							</tr>
						</tbody>
					</table>
				</div> <!-- Main details -->
				
				<label><b>Email Details</b></label>
				<div class="table-responsive" data-toggle="lists"
					data-options='{"valueNames": ["tables-row", "tables-first", "tables-last", "tables-handle"]}'>
					<table class="table">
						<thead>
							<tr>
								<th scope="col"><a href="#" class="text-muted sort" data-sort="tables-first">Email
										Id</a></th>
								<th scope="col"><a href="#" class="text-muted sort" data-sort="tables-handle">Click Here</a>
								</th>
							</tr>
						</thead>
						<tbody class="list">
							<%
												 if(conversationList.size()>0){
												 for(int i=0; i<conversationList.size(); i++){
												  EmailTable emailBean = new EmailTable();
												  emailBean =(EmailTable) conversationList.get(i);
												  %>
							<tr>
								<td class="table-handle">Email <%=i+1%></td>
								<td class="table-handle"><a href='/VendorAudit/Report?report=viewEmails&option=viewEmails&controlNo=<%=emailBean.getControlNo()%>&emailid=<%=emailBean.getEmailId()%>'><%=emailBean.getEmailId()%></a></td>
							</tr>
							<%} 
							} else{%>
							<tr>
								<td class="table-handle" colspan="2" align="center">No Emails Available</td>
							</tr>
							<%} %>
						</tbody>
					</table>
				</div> <!-- Email List with link -->
				
				<label><b>Attachments Detail</b></label>
				<div class="table-responsive" data-toggle="lists"
					data-options='{"valueNames": ["tables-row", "tables-first", "tables-last", "tables-handle"]}'>
					<table class="table">
						<thead>
							<tr>
								<th scope="col"><a href="#" class="text-muted sort" data-sort="tables-first">Attachment
										Id</a></th>
								<th scope="col"><a href="#" class="text-muted sort" data-sort="tables-handle">Click Here</a>
								</th>
							</tr>
						</thead>
						<tbody class="list">
							<%
												 if(attachmentList.size()>0){
												 for(int i=0; i<attachmentList.size(); i++){
												  AttachmentTable attBean = new AttachmentTable();
												  attBean =(AttachmentTable) attachmentList.get(i);
												  %>
							<tr>
								<td class="table-handle">Attachment <%=i+1%></td>
								<td class="table-handle"><a href='/VendorAudit/Report?report=viewEmails&option=viewAttachment&controlNo=<%=attBean.getControlNo()%>&=lineno<%=attBean.getLineNo()%>'><%=attBean.getLineNo()%></a></td>
							</tr>
							<%} }
							 else{%>
							<tr>
								<td class="table-handle" colspan="2" align="center">No Attachments Available</td>
							</tr>
							<%} %>
						</tbody>
					</table>
				</div> <!-- Attachment List with link -->
				
				<label><b>Issues Reported</b></label>
				<div class="table-responsive" data-toggle="lists"
					data-options='{"valueNames": ["tables-row", "tables-first", "tables-last", "tables-handle"]}'>
					<table class="table">
						<thead>
							<tr>
								<th scope="col"><a href="#" class="text-muted sort" data-sort="tables-first">Issues Reported</a></th>
								<th scope="col"><a href="#" class="text-muted sort" data-sort="tables-handle">Click Here</a>
								</th>
							</tr>
						</thead>
						<tbody class="list">
							<tr>
								<td class="table-handle">Issues reported</td>
								<td class="table-handle"><%=issues%></td>
							</tr>
						</tbody>
					</table>
				</div> <!-- Issues List without link -->
				
			</div>
		</div>
		<!-- / .row -->
		
		<%-- <div class="row justify-content-center">
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