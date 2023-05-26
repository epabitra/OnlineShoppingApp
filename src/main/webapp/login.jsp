<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
      crossorigin="anonymous"
    />
    <link rel="stylesheet" href="assets/css/login.css" />
    <script
	  src="https://code.jquery.com/jquery-3.6.0.js"
	  integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
	  crossorigin="anonymous">
  	</script>
  </head>
  <body>
    <div class="container1">
      <div class="left">
        <img src="assets/images/banner.jpg" alt="loading..." />
      </div>
      <div class="right">
        <div style="text-align: center">
          <img src="assets/images/logo.jpg" alt="loading..." />
        </div>
        <div class="login_frm">
          <form onsubmit="return validate(this)" action="user" method="post" class="column">
            <h5 style="text-align: center">Login</h5>
            <div style="text-align:center;">
            	<span id="success" style="color: green; text-align:center;"></span>
            	<span id="error" style="display: block; color: red;">${requestScope.errorMsg}</span>
            	<script>
		            /**
		             * This code is used for Retrieve the param value from URL
		             * i used this for showing the Registration Successfull msg
		             * Becuase of Interviewer requirement...
		            */
	            	const myUrl2 = new URL(window.location.toLocaleString());
	            	const msg = myUrl2.searchParams.get('success');
	            	if(msg!=null)
	            	document.getElementById("success").innerHTML=msg+"<br>";
            	</script>
            </div>
            <input type="hidden" name="hb" id="hb" value="login">
            <label for="phone" class="from-label">Phone</label>
            <input
              type="text"
              class="form-control bg-transparent input"
              name="phone"
              id="phone"
              value="${param.phone}"
            />
            <label for="password" class="form-label">Password</label>
            <input
              type="password"
              class="form-control bg-transparent input"
              name="password"
              id="password"
              value="${param.password}"
            />
            <div style="text-align: right;">
              <span style="color: rgb(42, 42, 238)">forgot password?</span>
            </div>
            <input type="submit" id="submitBtn" class="btn btn-info frm-submit my-3" value="Login" />
            <p style="text-align: center;">
              Don't have an account?
              <a href="register.jsp"><span style="color: rgb(32, 32, 239)">Create One</span></a>
            </p>
          </form>
        </div>
      </div>
    </div>
    
    <script>
    	$(".input").on("click", function(){
    		$("#success").css("display", "none");
    		$("#error").css("display", "none");
    	});
    	
    	function validate(frm){
    		if(frm.phone.value == ""){
    			$("#error").html("Please fill all the fields !!!");
				$("#error").css("color", "red");
				$("#error").css("display", "block");
				return false;
    		}
    		if(frm.password.value == ""){
    			$("#error").html("Please fill all the fields !!!");
				$("#error").css("color", "red");
				$("#error").css("display", "block");
				return false;
    		}
    	}
    	
    	/*
    	$("#submitBtn").on("click", function(event){
    		event.preventDefault();
    		
    		let phone = document.getElementById("phone");
    		let password = document.getElementById("password");
    		let hb = document.getElementById("hb");
    		let params = "phone="+phone+"&password="+password+"&hb="+hb;
    		
    		$.ajax({
	    		url: "user",
	    		data: params,
	    		type: 'post',
	    		success: function(data, textStatus, jqXHR){
	    			console.log("responce :: "+data.trim());
	    			if(data.trim() == "blank"){
	    				$("#error").html("Please fill all the fields !!!");
	    				$("#error").css("color", "red");
	    				$("#error").css("display", "block");
	    			}else if(data.trim() == "mismatch"){
	    				$("#error").html("Password not matching");
	    				$("#error").css("color", "red");
	    				$("#error").css("display", "block");
	    			}else if(data.trim() == "success"){
	    				console.log("success running");
	    				window.location.replace("http://localhost:8010/OnlineShoppingApp/login.jsp?success=User Created Successfully...");
	    			}	    			   			
	    		}
	    	})
    	});
    	*/
    </script>
    
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
      crossorigin="anonymous"
    ></script>
  </body>
</html>
