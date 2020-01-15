<%@page import="java.math.BigDecimal"%>
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

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
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
</style>

<link href='https://fonts.googleapis.com/css?family=Playfair Display' rel='stylesheet'>

<!-- endbuild -->
<title>Create Ticket Screen 4</title>

<%
String ponumber = (String)request.getAttribute("ponumber");
Integer controlNo = (Integer) request.getAttribute("controlNo");
String supplieremail = (String)request.getAttribute("supplieremail");
String editflag = (String)request.getAttribute("flow");
String supexception = (String)request.getAttribute("supplierexception");
String followupdate = (String) request.getAttribute("followupdate");
String responsedate = (String)request.getAttribute("responsedate");
String analystnotes = (String)request.getAttribute("analystnotes");
//TODO Get supplier detail as well

BigDecimal bd1 = new BigDecimal(0);
				BigDecimal bd2 = new BigDecimal(0);

				if (followupdate != null && !"".equalsIgnoreCase(followupdate))
					bd1 = new BigDecimal(followupdate);

				if (responsedate != null && !"".equalsIgnoreCase(responsedate))
					bd2 = new BigDecimal(responsedate);

//Getting user
LoginUser loginUser;
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
            Vendor Receiving Observations <a href="javascript:history.back()"><i class="fa fa-angle-left"></i> Back </a> 
          </p> -->

<form id="tkt1" class="border border-light p-5" method='post' action='/VendorAudit/Report'>

<input type="hidden" id="report" name="report" value="submitticket">
<input type="hidden" id="option" name="option" value="submitticket">
<input type="hidden" name="controlNo" value="<%=controlNo%>">
<input type="hidden" name="ponumber" value="<%=ponumber%>">
<input type="hidden" name="save" id="save" value="">
<input type="hidden" name="editFlag" id="editFlag" value="<%=editflag%>">
<p class="h4 mb-4 text-center">Control <%=controlNo%>   <a href="/VendorAudit/Report?report=backToMain&option=backToMain" style="float: left;"><i class="fa fa-home"></i></a><a href="Login?action=logoff" style="float: right;"> <i class="fas fa-sign-out-alt"></i></a></p>
    
    <p align="center">
	<button class="btn btn-info btn-sm" type="button" style="width: 25%; float: left;" onclick="backToSearch()">Previous</button>
	<button class="btn btn-info btn-sm" type="button" onclick="setSaveFlag()" style="width: 20%; float: inherit;">Save</button>
    <button class="btn btn-info btn-sm" type="submit" style="width: 20%; float: right;">Email</button>
    </p>
    
    <label for="supplieremail">Vendor Contact</label>
    <input type="text" id="supplieremail" name="supplieremail" class="form-control mb-4" placeholder="Enter Vendor Email Here" value="<%=supplieremail%>">
	<label for "supexception">Supplier Exception</label>
	<input type="text" id="supexception" name="supexception" class="form-control mb-4" placeholder="Enter Supplier Exception" readonly="readonly" value="<%=(supexception!=null && !"".equals(supexception))?supexception:"Not Available"%>">
	<%-- <label for="followup">Follow up email sent?</label>
	<input type="checkbox" id="followup" name="followup" <%if(followup!=null && !"".equals(followup)){ %> checked="checked" <%} %>><br> --%>
	<label for="followupdate">Follow Up Date</label>
	<input  type="text" data-provide="datepicker" class="form-control datepicker" id="followupdate" name="followupdate" title="Choose Follow Up Date"  value="<%=bd1.intValue()>0?bd1:""%>">
	<%-- <label for="responded">Vendor responded to observation?</label>
	<input type="checkbox" id="responded" name="responded" <%if(responded!=null && !"".equals(responded)){ %> checked="checked" <%} %>><br> --%>
	<label for="responsedate">Vendor Response Date</label>
	<input id="responsedate" name="responsedate"  type="text"
						data-provide="datepicker" class="form-control datepicker" title="Choose Response Date" value="<%=bd2.intValue()>0?bd2:""%>">
	<label for="analystnotes">Analyst Notes</label>
	<textarea id="analystnotes" name="analystnotes" class="form-control mb-4" placeholder="Textarea"><%=analystnotes%></textarea>
	<p align="center">
	<button class="btn btn-info btn-sm" type="button" style="width: 25%; float: left;" onclick="backToSearch()">Previous</button>
	<button class="btn btn-info btn-sm" type="button" onclick="setSaveFlag()" style="width: 20%; float: inherit;">Save</button>
    <button class="btn btn-info btn-sm" type="submit" style="width: 20%; float: right;">Email</button>
    </p>
</form>


        </div>
      </div> <!-- / .row -->
      
      <%-- <div class="row justify-content-center">
			<div class="col-12 col-md-5 col-xl-4 my-5">
				<%@include file="partials/bottomnav.html" %>
			</div>
		</div> --%>
		<!-- / .row -->

    </div> <!-- / .container -->
    <script type="text/javascript">
    $(document).ready(function() {
 	$("#followupdate").datepicker();
 	$("#responsedate").datepicker();
 	
 			$('#followupdate').datepicker({
			weekStart : 1,
			daysOfWeekHighlighted : "6,0",
			autoclose : true,
			todayHighlight : true,
			 dateFormat: 'mm-dd-yy'
		});
		//$('#followupdate').datepicker("setDate", new Date());
		
		$('#responsedate').datepicker({
			weekStart : 1,
			daysOfWeekHighlighted : "6,0",
			autoclose : true,
			todayHighlight : true,
			 dateFormat: 'mm-dd-yy'
		});
		//$('#responsedate').datepicker("setDate", new Date());
                  
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
document.getElementById("report").value = "saveTicket4";
document.getElementById("option").value = "submitticket";
frm.submit();
}

function backToSearch(){
var frm = document.getElementById("tkt1");
document.getElementById("report").value = "editTicket3";
document.getElementById("option").value = "editTicket3";
frm.submit();
}
    </script>
</body>
</html>