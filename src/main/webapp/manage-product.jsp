<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Adding Product</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
      crossorigin="anonymous"
    />
    <style>
    	.container4{
    		display: flex;
    	}
        .dropdown-toggle{
            text-decoration: none;
        }
        select{
            width: 100%;
            height: 36px;
            border-style: solid;
            border-color: #ebe2e2;
            border-radius: 5px;
        }
        .left{
        	flex-basis: 15%;
        	background-color: red;
        	height: 100vh;
        }
        .right{
        	flex-basis: 85%;
        	height: 100vh;
        	width: 85vw;
        	background-color: #f9f6f6;
        }
        .register-form{
		    display: flex;
		    align-items: top;
		    justify-content: left;
		    padding-left: 2%;
		    height: 100%;
        }
        .form-body{
		    padding: 15px;
		    border-style: solid;
		    border-width: 1px;
		    border-color: black;
		    box-shadow: 5px 10px #ebe2e2;
		}
		.pl-2{
			padding-left: 5px;
		}
    </style>
  </head>
  <body>
  	<jsp:include page="header.jsp"></jsp:include>
    <div class="container4">
    	<div class="left">
    		<jsp:include page="side-nav.jsp"></jsp:include>
    	</div>
        <div class="registration-container right">
        	<input type="hidden" id="typeHb" value="${param.productType}"> 
            <div class="register-form">
                <form onsubmit="return false;">
                    <div class="form-body">
                        <table>
                            <tr>
                              <td style="text-align: center; padding: 10px 0px;" colspan="2">
                                <label style="font-size: 20px;" id="action"><b>${param.action}</b></label><br> 
                                <span id="msg"></span>
                              </td>
                            </tr>
                            <tr>
                               <td>
                                <label for="first-name" class="form-label">Product Name</label>
                                <input type="text" name="name" id="name" class="form-control" placeholder="First Name" value="${param.productName}">
                               </td> 
                               <td class="pl-2">
                                <label for="" class="form-label">Type</label>
                                <select class="dropdown-toggle" aria-expanded="false" onclick=loadTypes() name="productType" id="productType">
                                    <option value="0">Select</option>
                                </select>
                               </td>
                            </tr>
                            <tr>
                              <td>
                                <label for="stock" class="form-label">No Of Stucks</label>
                                <input type="number" min="0" name="stocks" id="stocks" class="form-control" placeholder="0" value="${param.stocks}">
                              </td>
                              <td class="pl-2">
                                <label for="price" class="form-label">Price</label>
                                <input type="text" name="price" id="price" class="form-control" placeholder="Price" value="${param.price}">
                              </td>
                            </tr>
                            <tr>
                              <td colspan="2">
                                <button class="btn btn-success col-12 btn-lg mt-3" id="submitBtn" value="${param.action}">${param.action}</button>
                              </td>
                            </tr>
                        </table>
                    </div>
                </form>
            </div>
        </div>
    </div>
  <script type="text/javascript">  
  	let typeFlag = 0;
  	// Loading the Product Types Dropdown
  	function loadTypes(){
  		if(typeFlag==0){
  			typeFlag = 1;
	  		let params = "action=loadTypes";
			
			$.ajax({
	    		url: "product",
	    		data: params,
	    		type: 'post',
	    		success: function(data, textStatus, jqXHR){
	    			$("#productType").html(data);
	    			// The below code set the dropdown default value
	    			// we write this here because to load the above data.
	    			// it take more then window loading completion time.
	    			// otherwise we use the below commented logic, for
	    			// execute some logic when the window loaded...
	    			// $(window). bind("load", function() { // code here }); 
	    			$("#productType").val(parseInt(document.getElementById("typeHb").value));
	    		}
	    	})
  		}
  	}
  	
  	let submitBtn = document.getElementById("submitBtn").value;
  	if(submitBtn == "Edit Product"){
  		loadTypes();
  		$("#name").attr("disabled","disabled");
  	}
  	
  	// Adding Product to the Database
  	$("#submitBtn").on("click", function(){
  		if(formValidation()){
  			return false;
  		}
  		if(submitBtn == "Add Product"){
	  		let name = document.getElementById("name").value;
	  		let productType = document.getElementById("productType").value;
	  		let stocks = document.getElementById("stocks").value;
	  		let price = document.getElementById("price").value;
	  		console.log("name="+name+"&productType="+productType+"&stocks="+stocks+"&price="+price+"&action=addProduct");
	  		
	  		let params = "name="+name+"&productType="+productType+"&stocks="+stocks+"&price="+price+"&action=addProduct";
			
			$.ajax({
	    		url: "product",
	    		data: params,
	    		type: 'post',
	    		success: function(data, textStatus, jqXHR){
	    			if(data.trim() == "success"){
	    				window.location.replace("view-product.jsp?success=Product Added Successfully");
	    			}
	    			if(data.trim() == "duplicate"){
	    				$("#msg").css("color", "red");
	    				$("#msg").html("Product Already Exist!!!");
	    				$("#name").css("border-color", "red");
	    			}
	    		}
	    	})
  		}
  		if(submitBtn == "Edit Product"){
  			let name = document.getElementById("name").value;
	  		let productType = document.getElementById("productType").value;
	  		let stocks = document.getElementById("stocks").value;
	  		let price = document.getElementById("price").value;
	  		console.log("name="+name+"&productType="+productType+"&stocks="+stocks+"&price="+price+"&action=updateProduct");
	  		
	  		let params = "name="+name+"&productType="+productType+"&stocks="+stocks+"&price="+price+"&action=updateProduct";
			
			$.ajax({
	    		url: "product",
	    		data: params,
	    		type: 'post',
	    		success: function(data, textStatus, jqXHR){
	    			if(data.trim() == "success"){
	    				window.location.replace("view-product.jsp?success=Information Updated Sucessfully");
	    			}
	    		}
	    	})
  		}
  	});
  	
  	// Form Validation Logic
  	function formValidation(){
  		let name = document.getElementById("name").value;
  		let stocks = document.getElementById("stocks").value;
  		let productType = document.getElementById("productType").value;
  		let price = document.getElementById("price").value;
  		
  		let flag = false;
  		if(name.length <1) {
  			$("#name").css("border-color", "red");
  			flag = true;
  		}
  		if(stocks.length<1) {
  			$("#stocks").css("border-color", "red");
  			flag = true;
  		}
  		if(productType == 0){
  			$("#productType").css("border-color", "red");
  		}
  		if(price.length<1) {
  			$("#price").css("border-color", "red");
  			flag = true;
  		}
  		if(flag){
	  		$("#msg").css("color", "red");
			$("#msg").html("Please Fill all the boxes!!!");
  		}
		return flag;
  	}
  	
  	// Removing the border color when someone click on the input box
  	$(".form-control").on("click", function(event){
  		$("#msg").html("");
  		$(this).css("border-color","#ebe2e2");
  	});
  	$("select").on("click", function(event){
  		$("#msg").html("");
  		$(this).css("border-color","#ebe2e2");
  	});
  	/**
  	  * I Write the JSON code in the view-product.jsp and
  	  * I redirected from view-product.jsp to this page...
  	  *
  	  */
  	
  </script>
  <script
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
    crossorigin="anonymous"
  ></script>
</body>
</html>
