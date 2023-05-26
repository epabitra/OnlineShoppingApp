<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
      crossorigin="anonymous"
    />
    <style>
        .nav-container{
            background-color: #CFE2FF;
            height: 100vh;
        }
        .nav-container label{
            display: flex;
            justify-content: center;
            align-items: center;
            text-align: center;
            height: 40px;
            font-size: large;
            cursor: pointer;
        }
        .active{
            background-color:antiquewhite;
        }
        a{
        	text-decoration: none;
        	color: black;
        }
    </style>
    <script
      src="https://code.jquery.com/jquery-3.6.0.js"
      integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
      crossorigin="anonymous"
    ></script>
  </head>
  <body>
    <div class="nav-container">
    	<input type="hidden" id="role" value="${cookie.role.value}">
    	<input type="hidden" id="activeLink" value="${param.activeLink}">
        <a href="view-product.jsp"><label class="view-product active nav-option">View Products</label></a>
        <a href="manage-product.jsp?activeLink='add-product'&action=Add Product" id="addProductLink"><label class="add-product nav-option">Add Product</label></a>
        <a href="my-cart.jsp?activeLink='cart'" id="myCartLink"><label class="cart nav-option">My Cart</label></a>
    </div>
    <script>
    	let activeLink = document.getElementById("activeLink").value;
    	activeLink = activeLink.replace(/'/g,"");
    	if(activeLink.length > 0){
    		$(".active").removeClass("active");
    		$("."+activeLink).addClass("active");
    	}
    	
        let role = document.getElementById("role").value;
        if(role == "admin"){
        	$("#addProductLink").css("display", "block");
			$("#myCartLink").css("display", "none");        	
        }else if(role == "user"){
        	$("#myCartLink").css("display", "block");
        	$("#addProductLink").css("display", "none");
        }else{
        	window.location.replace("login.jsp");
        }
    </script>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
      crossorigin="anonymous"
    ></script>
  </body>
</html>