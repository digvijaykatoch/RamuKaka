<%@page import="com.schein.bean.*"%>
<%@page import="com.schein.utils.*"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@include file="partials/head.html"%>

<!-- Libs JS -->
<script src="assets/js/jquery/dist/jquery.min.js"></script>
<script src="assets/js/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
<script
	src="assets/js/@shopify/draggable/lib/es5/draggable.bundle.legacy.js"></script>
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
<link rel="stylesheet" href="assets/css/all.css">
<link rel="stylesheet" href="assets/css/bootstrap.min.css">
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

<!-- endbuild -->
<title>Insert title here</title>
<%
List poList = (List) request.getAttribute("poList");
String ponumber = WebUtils.getRequestParameter(request, "ponumber");
LoginUser loginUser = (LoginUser) session.getAttribute("loginuser");
 %>
</head>
<body
	class="d-flex align-items-center bg-auth  border-primary">
	<!-- CONTENT
    ================================================== -->
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-12 col-md-5 col-xl-4 my-5">


				<!-- HSI Logo -->

				<img alt="img" src="assets/img/covers/logo-global.gif"
					class="img-responsive" style="padding-left: 8%">

				<!-- Heading -->
				<h1 class="display-4 text-center mb-3">Welcome User!</h1>

				<!-- Subheading -->
				<p class="text-muted text-center mb-5">Audit Vendor Discrepancy
					App <a href="javascript:history.back()"><i class="fa fa-angle-left"></i> Back </a> </p>

				<div class="table-responsive" data-toggle="lists"
					data-options='{"valueNames": ["tables-row", "tables-first", "tables-last", "tables-handle"]}'>
					<table class="table">
						<thead>
							<tr>
								<th scope="col"><a href="#" class="text-muted sort"
									data-sort="tables-first">PO Number</a></th>
								<th scope="col"><a href="#" class="text-muted sort"
									data-sort="tables-handle">Supplier</a></th>
								<th scope="col"><a href="#" class="text-muted sort"
									data-sort="tables-handle">View Tickets</a></th>
								<th scope="col"><a href="#" class="text-muted sort"
									data-sort="tables-handle">Create Ticket</a></th>
							</tr>
						</thead>
						<tbody class="list">
							<%
												  if (!poList.isEmpty()) {
												  PoBean poBean = new PoBean();
												   for (int i = 0; i < poList.size(); i++) {
												   poBean =(PoBean) poList.get(i);
												  %>
							<tr>
								<td class="tables-first"><%=poBean.getPonumber()%></td>
								<td class="table-handle"><%=poBean.getSupplier()%></td>
								<td class="table-handle" align="center"><a
									href='/VendorAudit/Report?report=viewTickets&option=viewTickets&ponumber=<%=poBean.getPonumber()%>&supplier=<%=poBean.getSupplier()%>'><i
										class="fa fa-eye"></i></a></td>
								<td class="table-handle" align="center"><a
									href='/VendorAudit/Report?report=createTickets&option=createTicket&ponumber=<%=poBean.getPonumber()%>&supplier=<%=poBean.getSupplier()%>'><i
										class="fa 
far fa-edit"></i></a></td>
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

		<%-- <div class="row justify-content-center">
			<div class="col-12 col-md-5 col-xl-4 my-5">
				<%@include file="partials/bottomnav.html" %>
			</div>
		</div> --%>
		<!-- / .row -->

	</div>
	<!-- / .container -->




	<!-- Hidden form that carries information regarding view or create ticket -->
	<!-- 	
	<form  id="ticketForm" name="ticketForm"  method='post' action='/VendorAudit/Report'>
          /VendorAudit/Report?report=NsiOpenPORpt&option=backToSearch
          Search button
           <div class="input-group">
            <input type="hidden" id="report" name="report" value="">
            <input type="hidden" name="option" id="option" value="">
    <div class="input-group-append">
      <button class="btn btn-secondary" type="button">
        <i class="fa fa-search"></i>
      </button>
    </div>
  </div>
        
          </form> -->

	<script type="text/javascript">
		function viewTicket(typ) {
		alert("inside viewticket function");
			if (typ === "V") {//View
			document.getElementById("report").value = "ViewTickets";
			document.getElementById("option").value = "view" ;
			document.getElementById("ticketForm").submit();
			} else {//Create
			document.getElementById("report").value = "CreateTickets";
			document.getElementById("option").value = "create" ;
			document.getElementById("ticketForm").submit();
			}
		}
		
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