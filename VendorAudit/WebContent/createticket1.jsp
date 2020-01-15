<%@page import="com.schein.bean.*"%>
<%@page import="com.schein.utils.*"%>
<%@ page
	import="java.util.List, java.util.Map, java.util.ArrayList, com.schein.dao.TicketDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="main.jsp"%>
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

<!-- Select For Bootstrap -->
<script src='assets/js/bootstrap-select.min.js'></script>

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
<link rel="stylesheet" href="assets/css/bootstrap-select.css">
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
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#obsdate").datepicker();

		$('#goodload').change(function() {
			if ($(this).is(":checked")) {
				$('#status').val(1);
			} else {
				$('#status').val(5);
			}
		});

		$('#status').change(function() {
			if (this.value != 1) {
				$('#goodload').prop("checked", false);
			} else {
				$('#goodload').prop("checked", true);
			}
		});

	});
</script>
<link href='https://fonts.googleapis.com/css?family=Playfair Display'
	rel='stylesheet'>
<!-- endbuild -->
<title>Create Ticket Screen 1</title>

<%
	LoginUser loginUser = (LoginUser) session.getAttribute("loginuser");
	//Getting user info
	String user = "";
	if (session.getAttribute("loginuser") != null) {
		loginUser = (LoginUser) session.getAttribute("loginuser");
		user = loginUser.getUserInfo().getUserName().toString();
	} else
		user = " User";

	String ponumber = (String) request.getAttribute("ponumber");
	String supplier = (String) request.getAttribute("supplier");
	Integer controlNo = (Integer) request.getAttribute("controlNo");
	String repflag = "";
	//TODO Get supplier detail as well

	List analystList = (List) request.getAttribute("analystList");
	List statusList = (List) request.getAttribute("statusList");
	List locationList = (List) request.getAttribute("locationList");
	List stateList = (List) request.getAttribute("stateList");
	MasterTable mbean = new MasterTable();
	if (request.getAttribute("mbean") != null)
		mbean = (MasterTable) request.getAttribute("mbean");
	String tsmnotes = (String) request.getAttribute("tsmnotes");
	String editflag = (String) request.getAttribute("flow");
	if (request.getAttribute("repeat") != null) {
		repflag = (String) request.getAttribute("repeat");
	}

	String loc = "";
	String state = "";
	String stats = "";
	String xdock = "";
	String freightterms = "";
	String goodload = "";

	String firstflag = "";
	if (request.getAttribute("first") != null) {
		firstflag = (String) request.getAttribute("first");
	}
	String pronumber = (String) request.getAttribute("pronumber");
	String analyst = (String) request.getAttribute("analyst");

	if (request.getAttribute("location") != null) {
		loc = (String) request.getAttribute("location");
	}

	if ((request.getAttribute("status") != null)) {
		stats = (String) request.getAttribute("status");
	} else if (mbean.getStatus() != null) {
		stats = mbean.getStatus().toString();
	} else {
		stats = "5";
	}

	if (mbean.getShipLoc() != null && !"".equals(mbean.getShipLoc())) {
		state = mbean.getShipLoc().trim();
	}

	if (request.getAttribute("freightterms") != null) {
		freightterms = (String) request.getAttribute("freightterms");
	}

	if (request.getAttribute("xdock") != null) {
		xdock = (String) request.getAttribute("xdock");
	}
	if (request.getAttribute("goodload") != null) {
		goodload = (String) request.getAttribute("goodload");
	}

	String message = (String) request.getAttribute("message");

	if (message != null && !"".equals(message)) {
		session.setAttribute("message", message); // Set error.
		List opentickets = new ArrayList();
		TicketDao ticketdao = new TicketDao();
		opentickets = ticketdao.getOpenTickets(loginUser.getUserInfo().getUserId());
		session.setAttribute("opentickets", opentickets);
		throw new RuntimeException("Error condition!!!");
	}
%>
</head>
<body class="d-flex align-items-center bg-auth border-primary">
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-12 col-md-5 col-xl-4 my-5">
					<!-- HSI Logo -->

					<div id="logo"
						style="text-align: center; font-family: 'Helvetica'; font-variant: small-caps; font-weight: bold;">
						<img alt="img" src="assets/img/covers/hsilogo.png"
							style="vertical-align: middle; text-align: left"> <span
							style="vertical-align: middle">Vendor Receiving Observations</span>
					</div>

				<!-- Heading -->
				<!--   <h1 class="display-4 text-center mb-3">
           
            Welcome <%=user%>!
          </h1>-->
				<!-- Subheading -->
				<!-- <p class="text-muted text-center mb-5">
					Vendor Receiving Observations
					<a href="javascript:history.back()"><i class="fa fa-angle-left"></i> Back </a> 
				</p> -->
				<form id="tkt1" class="border border-light p-5" method='post'
					action='/VendorAudit/Report'>
					<input type="hidden" id="report" name="report" value="editTicket2">
					<input type="hidden" id="option" name="option" value="editTicket2">
					<input type="hidden" name="save" id="save" value=""> <input
						type="hidden" name="editFlag" id="editFlag"
						value="<%=editflag != null ? editflag : ""%>"> <input
						type="hidden" name="repFlag" id="repFlag"
						value="<%=repflag != null ? repflag : ""%>"> <input
						type="hidden" name="first" id="first" value="<%=firstflag%>">
					<input type="hidden" name="controlNo"
						value="<%=controlNo != 0 ? controlNo : ""%>">
					<p class="h4 mb-4 text-center">
						Control
						<%=controlNo%>
						<a href="/VendorAudit/Report?report=backToMain&option=backToMain"
							style="float: left;"><i class="fa fa-home"></i></a><a
							href="Login?action=logoff" style="float: right;"> <i
							class="fas fa-sign-out-alt"></i></a>
					</p>

					<p align="center">
						<button class="btn btn-info btn-sm" type="button"
							style="width: 25%; float: left;" onclick="backToSearch()">Previous</button>
						<button class="btn btn-info btn-sm" type="button"
							onclick="setSaveFlag()" style="width: 20%; float: inherit;">Save</button>
						<button class="btn btn-info btn-sm" type="submit"
							style="width: 20%; float: right;">Next</button>
					</p>

					<label for="analyst">Analyst</label> <select
						class="browser-default custom-select mb-4" id="analyst"
						name="analyst">
						<option value="" disabled="" selected="">Select</option>
						<%
							for (int i = 0; i < analystList.size(); i++) {
						%>
						<option value="<%=analystList.get(i)%>"
							<%if (analystList.get(i).toString().trim().equalsIgnoreCase(mbean.getAnalyst().toString().trim())) {%>
							selected="selected" <%}%>><%=analystList.get(i)%></option>
						<%
							}
						%>
					</select> <label for="location">Source</label>
					<%=WebUtils.getDropDownHtml((List) request.getAttribute("locationList"),
					"id='location' name='location' class='browser-default custom-select mb-4' style='pointer-events:none' ",
					loc)%>

					<label for="obsdate">Observation Date</label> <input id="obsdate"
						name="obsdate" placeholder="Selected date" type="text"
						data-provide="datepicker" class="form-control datepicker"
						<%if (mbean.getControlNo() != null) {%>
						value="<%=mbean.getObserveDte()%>" <%}%>> <br> <label
						for="status">Working Status</label>
					<%=WebUtils.getDropDownHtml((List) request.getAttribute("statusList"),
					"id='status' name='status' class='browser-default custom-select mb-4' ", stats.trim())%>

					<label for="ponumber">PO</label> <input type="text" id="ponumber"
						name="ponumber" class="form-control mb-4" placeholder="Text input"
						value="<%=ponumber%>" readonly="readonly"> <label
						for="supplier">Supplier</label> <input type="text" id="supplier"
						name="supplier" class="form-control mb-4" placeholder="Text input"
						value="<%=supplier%>" readonly="readonly"> <label
						for="state">Ship Location</label>
					<%=WebUtils.getDropDownHtml((List) request.getAttribute("stateList"),
					"id='state' name='state' class='browser-default custom-select mb-4'  data-none-selected-text",
					state)%>
					<label for="freightterms">Freight Terms</label> <input type="text"
						id="freightterms" name="freightterms" class="form-control mb-4"
						placeholder="Text input" value="<%=freightterms%>"
						readonly="readonly"> <label for="xdock">X-Dock</label> <input
						type="text" id="xdock" name="xdock" class="form-control mb-4"
						placeholder="Text input" value="<%=xdock%>" readonly="readonly">
					<labelfor"pronumber">PRO Number</label> <textarea
						maxlength="100" id="pronumber" name="pronumber"
						class="form-control mb-4" placeholder="PRO Number">
						<%
							if (mbean.getControlNo() != null) {
						%><%=mbean.getProNumber().trim()%>
						<%
							} else {
						%>  <%
  	}
  %>
					</textarea> <label for="goodload">Good Load</label> <input id="goodload"
						name="goodload" type="checkbox"
						<%if (goodload != null && !"".equals(goodload)) {
				if (goodload.equalsIgnoreCase("Y")) {%>
						checked="checked" <%}
			}%>> <br>
					<label for="tsmnotes">TSM Notes</label> <textarea id="tsmnotes"
						name="tsmnotes" class="form-control mb-4"
						placeholder="Enter TSM Notes Here"><%=tsmnotes != null ? tsmnotes : ""%></textarea>


					<p align="center">
						<button class="btn btn-info btn-sm" type="button"
							style="width: 25%; float: left;" onclick="backToSearch()">Previous</button>
						<button class="btn btn-info btn-sm" type="button"
							onclick="setSaveFlag()" style="width: 20%; float: inherit;">Save</button>
						<button class="btn btn-info btn-sm" type="submit"
							style="width: 20%; float: right;">Next</button>
					</p>
				</form>

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
		$('#obsdate').datepicker({
			weekStart : 1,
			daysOfWeekHighlighted : "6,0",
			autoclose : true,
			todayHighlight : true
		});
		$('#obsdate').datepicker("setDate", new Date());

		function myFunction() {
			var x = document.getElementById("myNavbar");
			if (x.className === "navbar") {
				x.className += " responsive";
			} else {
				x.className = "navbar";
			}
		}

		function setSaveFlag() {
			var frm = document.getElementById("tkt1");
			var saveFlag = document.getElementById("save");
			var editFlag = document.getElementById("editFlag").value;
			var repFlag = document.getElementById("repFlag").value;
			document.getElementById("report").value = "saveTicket1";

			saveFlag.value = "Yes";

			if (editFlag.length != 0 || repFlag.length != 0)
				document.getElementById("option").value = "editTicket2";
			else
				document.getElementById("option").value = "ticket2";

			frm.submit();
		}

		function backToSearch() {
			var frm = document.getElementById("tkt1");
			document.getElementById("report").value = "backToMain";
			document.getElementById("option").value = "backToMain";
			frm.submit();
		}
	</script>

</body>
</html>