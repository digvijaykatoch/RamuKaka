<%@page import="com.schein.dao.TicketDao"%>
<%@page import="com.schein.bean.*"%>
<%@page import="com.schein.utils.*" %>
<%@ page import ="java.util.*, java.sql.*"%>
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
</style>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link href='https://fonts.googleapis.com/css?family=Playfair Display' rel='stylesheet'>

<!-- endbuild -->
<title>Create Ticket Screen 2</title>

<%
String ponumber = (String)request.getAttribute("ponumber");
Integer controlNo = (Integer) request.getAttribute("controlNo");
String repflag = "";
Integer car =0;
//TODO Get supplier detail as well
LoginUser loginUser = (LoginUser) session.getAttribute("loginuser");
Map ticketMap = (Map)request.getAttribute("ticketmap");
Map ticketDetailsMap = (Map)request.getAttribute("ticketdetailmap");
Map ticketSubMap = (Map)request.getAttribute("ticketsubmap");
String editflag = (String)request.getAttribute("flow");
Connection con=null;
con     = AS400Utils.getAS400DBConnection();
if(request.getAttribute("repeat")!=null){
repflag = (String)request.getAttribute("repeat");
}
String firstflag = "";
if(request.getAttribute("first")!=null){
firstflag = (String)request.getAttribute("first");
}
//Getting user info
 String user = "";
 if(session.getAttribute("loginuser")!=null){
 loginUser = (LoginUser)session.getAttribute("loginuser");
 user=loginUser.getUserInfo().getUserName().toString();
 }
 else
 user=" User";
 
 List carrierlist = new ArrayList();
 if(request.getAttribute("carrierlist")!=null){
 carrierlist = (List)request.getAttribute("carrierlist");
 }
 if(request.getAttribute("carrier")!=null){
 car = (Integer)request.getAttribute("carrier");
 }
 
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
							style="vertical-align: middle; text-align: left"> <span
							style="vertical-align: middle; text-align: right">Vendor Receiving Observations</span>
					</div>
           
          <!-- Heading -->
           <!--   <h1 class="display-4 text-center mb-3">
           
            Welcome <%=user%>!
          </h1>--> 
          
          <!-- Subheading -->
          <!-- <p class="text-muted text-center mb-5">
            Vendor Receiving Observations   <a href="javascript:history.back()"><i class="fa fa-angle-left"></i> Back </a> 
          </p> -->

<form id="tkt1" class="border border-light p-5"  method='post' action='/VendorAudit/Report'>
<input type="hidden" id="report" name="report" <%if(editflag!=null && !"".equals(editflag)){ %> value="editTicket3" <%} else{ %>value="Ticket3"<%} %>>
<input type="hidden" id="option" name="option" <%if(editflag!=null && !"".equals(editflag)){ %> value="editTicket3" <%} else{ %>value="Ticket3"<%} %>>
<input type="hidden" name="controlNo" value="<%=controlNo%>">
<input type="hidden" name="ponumber" value="<%=ponumber%>">
<input type="hidden" name="ticketMap" value="<%=ticketMap%>">
<input type="hidden" name="ticketDetailsMap" value="<%=ticketDetailsMap%>">
<input type="hidden" name="save" id="save" value="">
<input type="hidden" name="editFlag" id="editFlag" value="<%=editflag%>">
<input type="hidden" name="repFlag" id="repFlag" value="<%=repflag!=null?repflag:""%>">

    <p class="h4 mb-4 text-center">Control <%=controlNo%>  <a href="/VendorAudit/Report?report=backToMain&option=backToMain" style="float: left;"><i class="fa fa-home"></i></a><a href="Login?action=logoff" style="float: right;"> <i class="fas fa-sign-out-alt"></i></a></p>
   
   	<p align="center">
    <button class="btn btn-info btn-sm" type="button" style="width: 25%; float: left;" onclick="backToSearch(<%=controlNo%>)">Previous</button>
	<button class="btn btn-info btn-sm" type="button" onclick="setSaveFlag()" style="width: 20%; float: inherit;">Save</button>   
    <button class="btn btn-info btn-sm" type="submit" style="width: 20%; float: right;">Next</button>
    </p>
   
    <!-- Iterating through ticket and ticket detail map -->
    
    <% Iterator entries = ticketMap.entrySet().iterator();
	while (entries.hasNext()) {
	Map.Entry entry = (Map.Entry) entries.next();
	String key = (String)entry.getKey();
    String value = (String)entry.getValue();
    String details = (String)ticketDetailsMap.get(key);
    String[] csv = details.split(",");
    TicketDao ticketdao = new TicketDao();
    String subissues = (String)ticketSubMap.get(key);
    if(value.toUpperCase().contains("TSM")||value.toUpperCase().contains("ANALYST")){
    	continue;
    	}	
    %>
    <label for="issue<%=key%>"><%=value%></label><br>
     <select id="<%=key%>issues" name="<%=key%>issues" class="selectpicker" multiple="multiple" data-live-search="true" style="width: 100%">
		<%for(int i=0; i<csv.length;i++){ %>	  
		  <option <%if(subissues.indexOf(csv[i])!=-1) {%> selected="selected" <%} %> value="<%=csv[i]%>" ><%=csv[i].toString().substring(5)%></option>
		 <%} %>
	</select>
	<br><br>
	<textarea id="<%=key%>comment" name="<%=key%>comment" class="form-control mb-4" placeholder="Textarea"><%=ticketdao.getCommentForIssues(con, controlNo, Integer.parseInt(key))  %></textarea>
	
	<%if(value.toUpperCase().contains("DELIVERY")) {%>
	<label for="carrier">Carrier</label><br>
	<%=WebUtils.getDropDownHtml(carrierlist,
					"id='carrier' name='carrier' data-none-selected-text ",
					car.toString())%><br><br>
	<%} %>
	
	<%} %>
	
    <p align="center">
    <button class="btn btn-info btn-sm" type="button" style="width: 25%; float: left;" onclick="backToSearch(<%=controlNo%>)">Previous</button>
	<button class="btn btn-info btn-sm" type="button" onclick="setSaveFlag()" style="width: 20%; float: inherit;">Save</button>   
    <button class="btn btn-info btn-sm" type="submit" style="width: 20%; float: right;">Next</button>
    </p>
</form>


        </div>
      </div> <!-- / .row -->
      
      <!-- <div class="row justify-content-center">
			<div class="col-12 col-md-5 col-xl-4 my-5">
				<div class="navbar">
					<a href="#">Home</a>|<a href="#">Page 1</a>|<a href="#">Page 2</a>|<a
						href="#">Page 3</a>
				</div>
			</div>
		</div> -->
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
var editFlag = document.getElementById("editFlag").value;
var repFlag = document.getElementById("repFlag").value;

document.getElementById("report").value = "saveTicket2";
saveFlag.value = "Yes";

if(editFlag.length!=0 || repFlag.length!=0)
document.getElementById("option").value = "editTicket3";
else
document.getElementById("option").value = "Ticket3";
frm.submit();
}

function backToSearch(){
var frm = document.getElementById("tkt1");
document.getElementById("report").value = "editTicket";
document.getElementById("option").value = "editTicket";
frm.submit();
}


    </script>
</body>
</html>