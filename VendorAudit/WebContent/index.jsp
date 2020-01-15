<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="en">
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <%@include file="partials/head.html" %>
    <%@ page session="false"%>
    
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
<script src='https://api.mapbox.com/mapbox-gl-js/v0.53.0/mapbox-gl.js'></script>

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
<link href='https://fonts.googleapis.com/css?family=Cinzel' rel='stylesheet'>
<!-- endbuild -->

<script type="text/javascript">
function togglePassword() {
  var x = document.getElementById("password");
  if (x.type === "password") {
    x.type = "text";
  } else {
    x.type = "password";
  }
}
</script>

  </head>
  <%@ page import="com.schein.utils.CachingManager"%>
   <%@ page import="com.schein.utils.BoUtils"%>
  <%
	boolean isExpire = false;

	System.out.println("........isExprire:" + isExpire);
	String submit = request.getParameter("submit.x");
	System.err.println(request.getParameter("action"));
	String errorCode = null;
	String errorMessage = (String) request.getAttribute("message");
	String userId = "";
	String password = "";

	//String logoname = "";
	//if (CachingManager.getCompany().equalsIgnoreCase("HSI"))
	//	logoname = "logo.gif";
	//else
	//	logoname = "HSAA-Logo.jpg";
%>
  <body class="d-flex align-items-center bg-auth border-primary">
  

    <!-- CONTENT
    ================================================== -->
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-12 col-md-5 col-xl-4 my-5" style="text-align: center; vertical-align: middle;">
          
             <!-- HSI Logo -->
             
             <div id="logo" style="text-align: center; font-family: 'Helvetica'; font-variant: small-caps; font-weight: bold;">
  <img alt="img" src="assets/img/covers/hsilogo.png"  class = "img-responsive" style="vertical-align: middle; text-align: left">
  <span style="vertical-align:middle">Vendor Receiving Observations</span>
</div>
         
          <!-- Form -->
          <form action="/VendorAudit/Login" method="post" class="border border-light p-5">
        
            <br>
          
          
             <!-- Subheading -->
        <!--   <p class="text-muted text-center mb-5">
            Vendor Receiving Observations
          </p> -->
          

            <!-- Email address -->
            <div class="form-group" style="text-align: left">

              <!-- Label -->
              <label>User Id</label>

              <!-- Input -->
              <input name="userId" type="text" class="form-control" placeholder="Enter user id" autofocus onkeyup="this.value = this.value.toUpperCase();">

            </div>

            <!-- Password -->
            <div class="form-group" style="text-align: left">

              <div class="row">
                <div class="col">
                      
                  <!-- Label -->
                  <label>Password</label>

                </div>
                <div class="col-auto">
                  
                 <!--  <!-- Help text 
                  <a href="https://ps.henryschein.com/" class="form-text small text-muted">
                    Forgot password?
                  </a> -->

                </div>
              </div> <!-- / .row -->

              <!-- Input group -->
              <div class="input-group input-group-merge">

                <!-- Input -->
                <input id="password" name="password" type="password" class="form-control form-control-appended" placeholder="Enter your password">

                <!-- Icon -->
                <div class="input-group-append">
                  <span class="input-group-text">
                    <i class="fe fe-eye" onclick="togglePassword()"> </i>
                  </span>
                </div>

              </div>
            </div>

            <!-- Submit -->
            <button class="btn btn-lg btn-block btn-primary mb-3" onclick(alert('hi'))>
              Sign in
            </button>

            <!-- Link 
            <div class="text-center">
              <small class="text-muted text-center">
                Don't have an account yet? <a href="sign-up.html">Sign up</a>.
              </small>
            </div>
            -->
            
          </form>

	<%
						if (errorMessage != null) {
					%>
					<tr>
						<td colspan='4' align='center'><div class=errmsg><%=errorMessage%></div></td>
					</tr>
					<%
						}
					%>
					<%
						if (isExpire)
							out.println("<tr align=center><td colspan=4><div class=confirmmsg>Session expired. Please log on again.</td></tr>");
					%>
        </div>
      </div> <!-- / .row -->
    </div> <!-- / .container -->
  </body>
</html>