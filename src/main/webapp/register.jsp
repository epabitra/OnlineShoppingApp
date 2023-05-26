<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>User Registration</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
      crossorigin="anonymous"
    />
    <link rel="stylesheet" href="assets/css/register.css">
    <!-- Bootstrap Icons Linking -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <script
	  src="https://code.jquery.com/jquery-3.6.0.js"
	  integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
	  crossorigin="anonymous">
  	</script>
  </head>
  <body>
    <div class="container1">
        <div class="registration-container">
            <div class="register-form">
                <form onsubmit="return false;" method="post">
                    <div class="form-body">
                        <table>
                            <tr>
                              <td style="text-align: center; padding: 10px 0px;" colspan="2">
                                <label style="font-size: 20px;"><b>Register User</b></label><br>
                                <span id="error" style="display: none;"></span>
                              </td>
                            </tr>
                            <tr>
                               <td>
                               	<input type="hidden" name="hb" id="hb" value="register">
                                <label for="firstName" class="form-label">First Name</label>
                                <input type="text" name="firstName" id="firstName" class="form-control" placeholder="First Name" value="${param.firstName}">
                               </td> 
                               <td class="pl-2">
                                <label for="lastName" class="form-label">Last Name</label>
                                <input type="text" name="lastName" id="lastName" class="form-control" placeholder="Last Name" value="${param.lastName}">
                               </td>
                            </tr>
                            <tr>
                               <td colspan="2">
                                <label for="email" class="form-label">Email</label>
                                <div class="email-container" style="display: flex;">
                                  <input type="email" name="email" id="email" class="form-control" placeholder="Email" value="${param.email}">
                                  <button class="btn btn-warning col-3" id="email-verify-btn" style="display: none;">Verify</button>
                                </div>
                               </td>
                            </tr>
                            <tr>
                               <td colspan="2">
                                <div class="email-varify mt-3" style="display: flex;">
                                  <input type="text" name="emailOtp" id="emailOtp" class="form-control email-vefify-section" placeholder="Please check your email for OTP">
                                  <button class="btn btn-success col-3 email-vefify-section" id="emailOptVefifyBtn">Verify</button>
                                </div>
                               </td>
                            </tr>
                            <tr>
                              <td colspan="2">
                                <label for="phone" class="form-label">Phone</label>
                                <div class="phone-container" style="display: flex;">
                                  <input type="text" name="phone" id="phone" class="form-control" placeholder="Phone" value="${param.phone}">
                                  <button class="btn btn-warning col-3" id="phoneVerifyBtn">Verify</button>
                                </div>
                               </td>
                            </tr>
                            <tr>
                              <td colspan="2">
                               <div class="phone-varify mt-3" style="display: flex;">
                                 <input type="text" name="phoneOtp" id="phoneOtp" class="form-control phone-verify-section" placeholder="Please check your Phone for OTP">
                                 <button class="btn btn-warning col-3 phone-verify-section" id="phoneOtpVerifyBtn">Verify</button>
                               </div>
                              </td>
                           </tr>
                            <tr>
                              <td>
                                <label for="password" class="form-label">Password</label>
                                <input type="password" name="password" id="password" class="form-control" placeholder="Password" value="${param.password.trim()}">
                              </td>
                              <td class="pl-2">
                                <label for="rePassword" class="form-label">Re-enter Password</label>
                                <input type="password" name="rePassword" id="rePassword" class="form-control" placeholder="Re-enter Password" value="${param.rePassword.trim()}">
                              </td>
                            </tr>
                            <tr>
                              <td colspan="2">
                                <button class="btn btn-success col-12 btn-lg mt-3" id="frmSubmitBtn" disabled>Register User</button>
                              </td>
                            </tr>
                            <tr>
                              <td colspan="2" style="text-align: center;">
                             	<label class="form-label">Already have an account <a href="login.jsp">Login</a></label>
                              </td>
                            </tr>
                        </table>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script>
		/** 
		 * This is Ajax code, The purpose is to Validate the Phone
		*/	
		
		$(".form-control").on("click", function(){$("#error").css("display","none");});
		
		// Form Submission Calling
		$("#frmSubmitBtn").on('click', function(event){
			event.preventDefault();
			$("#error").css("display", "none");
			let firstName = document.getElementById("firstName").value;
			let lastName = document.getElementById("lastName").value;
			let email = document.getElementById("email").value;
			let phone = document.getElementById("phone").value;
			let password = document.getElementById("password").value;
			let rePassword = document.getElementById("rePassword").value;
			let hb = document.getElementById("hb").value;
			
			let params = "firstName="+firstName+"&lastName="+lastName+"&email="+email+"&phone="+phone+"&password="+password+"&rePassword="+rePassword+"&hb="+hb;
			console.log("param :: "+params);
			
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
		})
		
		// This is for Sending OTP to Phone
		$("#phoneVerifyBtn").on('click', function(event){
			$("#error").css("display", "none");
			event.preventDefault();
			let phone = document.getElementById("phone").value;
			if(phone.length!=10){
				$("#error").html("Please Enter a valid Number");
				$("#error").css("color", "red");
				$("#error").css("display", "block");
				return false;
			}
			
			let params = "phone="+phone+"&action=sendOTP";
			console.log("param :: "+params);
			
			$.ajax({
	    		url: "phoneValidate",
	    		data: params,
	    		type: 'post',
	    		success: function(data, textStatus, jqXHR){
	    			console.log("responce :: "+data.trim());
	    			if(data.trim() == "success"){
	    				$(".phone-verify-section").css('display', 'block');
	    				$("#phoneVerifyBtn").attr("disabled", true);
	    				$("#phoneVerifyBtn").removeClass("bg-warning");
	    				$("#phoneVerifyBtn").addClass("bg-success");
	    				$("#phoneVerifyBtn").html("Verifying");
	    				$("#phone").attr("disabled", true);
	    			}else if(data.trim() == "duplicate"){
	    				$("#error").html("Phone number already registread...");
	    				$("#error").css("color", "red");
	    				$("#error").css("display", "block");
	    			}
	    		}
	    	})
		})
		
		// This is for Validating the Phone - OTP
		$("#phoneOtpVerifyBtn").on('click', function(event){
			event.preventDefault();
			let phoneOTP = document.getElementById("phoneOtp").value;
			
			let params = "otp="+phoneOTP+"&action=validateOTP";
			console.log("param :: "+params);
			
			$.ajax({
	    		url: "phoneValidate",
	    		data: params,
	    		type: 'post',
	    		success: function(data, textStatus, jqXHR){
	    			console.log("responce :: "+data.trim());
	    			if(data.trim() == "success"){
	    				$(".phone-verify-section").css('display', 'none');
	    				$("#phoneVerifyBtn").attr("disabled", true);
	    				$("#phoneVerifyBtn").removeClass("bg-success");
	    				$("#phoneVerifyBtn").addClass("bg-light");
	    				$("#phoneVerifyBtn").html("<i class='bi bi-check-circle-fill'> Verified</i>");
	    				$("#phoneVerifyBtn").css("color", "green");
	    				$("#phone").attr("disabled", true);
	    				$("#frmSubmitBtn").attr("disabled", false);
	    				
	    			}else if(data.trim() == "failed"){
	    				console.log("OTP Validation failed");
	    				$("#error").html("Invalid OTP please try again!!!");
	    				$("#error").css("color", "red");
	    				$("#error").css("display", "block");
	    			}else{
	    				console.log("Some error occured in otp validation");
	    				console.log(data.trim());
	    			}
	    		}
	    	})
		})
	</script>
  <script
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
    crossorigin="anonymous"
  ></script>
</body>
</html>
