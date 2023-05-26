<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>All Products List</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
      crossorigin="anonymous"
    />
    <link rel="stylesheet" href="assets/css/view-product.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <style>
		.ml-2{
			margin-left: 5px;
		}
		li{
			cursor: pointer;
		}
    </style>
    <script
      src="https://code.jquery.com/jquery-3.6.0.js"
      integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
      crossorigin="anonymous"
    ></script>
  </head>
  <body>
    <!-- Navbar Start -->
    <jsp:include page="header.jsp"/>
    <!-- Navbar End -->

    <!-- Container Start -->
    <div class="container1">
      <!-- Side Nav Start (left Container) -->
      <div class="left">
      	<jsp:include page="side-nav.jsp"/>
      </div>
      <!-- Side Nav End -->

      <!-- Products Container Start (Right Container) -->
      <div class="right">
      
      	<div class="shortContainer" style="display: flex; background-color: #107093; color: white;">
          <div class="sorting" style="flex-basis: 50%;">
            <div class="dropdown">
              <button class="btn dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false" style="color: white;">
                <i class="bi bi-sort-alpha-down" style="font-size: 18px;"></i>Sort By
              </button>
              <ul class="dropdown-menu">
              	<li><a class="dropdown-item" onclick="loadProducts()">Random</a></li>
                <li><a class="dropdown-item" onclick="loadSort('lowtohigh')">Low to High Price</a></li>
                <li><a class="dropdown-item" onclick="loadSort('hightolow')">High to Low Price</a></li>
                <li><a class="dropdown-item" onclick="loadSort('ascending')">Ascending</a></li>
                <li><a class="dropdown-item" onclick="loadSort('descending')">Descending</a></li>
              </ul>
            </div>
          </div>
          <div class="category" style="flex-basis: 50%; text-align: right;">
            <div class="dropdown">
              <button class="btn dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false" style="color: white;">
                <i class="bi bi-tag" style="font-size: 18px;"></i>Category
              </button>
              <ul class="dropdown-menu" id="typesDropdown">
                <li><a class="dropdown-item" href="#">Electronics</a></li>
                <!-- <li><a class="dropdown-item" href="#">Cloths</a></li>
                <li><a class="dropdown-item" href="#">Furnitures</a></li>
                <li><a class="dropdown-item" href="#">Paints</a></li>  -->
              </ul>
            </div>
          </div>
        </div>
        <div style="display: flex; align-items: center; color: green; justify-content: center;">
        	<span id="success"></span>
        	<span id="error" style="color: red;"></span>
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
      		<input type="hidden" id="role" value=${cookie.role.value}>
        </div>
        <table class="table table-striped">
          <thead>
            <tr class="table-primary">
                <th>NAME</th>
                <th>TYPE</th>
                <th>STOCK</th>
                <th>PRICE</th>
                <th>ACTION</th>
            </tr>
          </thead>
          <tbody>
          </tbody>
          
        </table>
      </div>
      <!-- Products Container End -->
    </div>
    <!-- Container End -->

	<script>
	
		// << Load Types Dropdown immediately when page loaded>> 
		let params = "action=loadDropdown";
			
		$.ajax({
    		url: "product",
    		data: params,
    		type: 'post',
    		success: function(data, textStatus, jqXHR){
    			$("#typesDropdown").html(data);
    		}
    	})
	    // << /End of Load Types Dropdown immediately when page loaded>>
	    
		// << Below function call the backend for loading Products
		// based on the given type and shorting order >>
		let globalSort = "random";
		let globalType = 0;
		function loadDropdown(i){
	   		globalType = i;
	   		loadProducts();
	   	}
	   	function loadSort(s){
	   		globalSort = s;
	   		loadProducts();
	   	}
		loadProducts();
		function loadProducts(){
			let params = "action=loadProducts&role="+role+"&sort="+globalSort+"&type="+globalType;
			
			$.ajax({
	    		url: "product",
	    		data: params,
	    		type: 'post',
	    		success: function(data, textStatus, jqXHR){
	    			$("tbody").html(data.trim());
	    		}
	    	})			
		}
		// << /End of Product Loading >>
		
		// Below function call the backend for Delete a product
		function deleteProduct(event){
			console.log("delete clicked");
			let flag = confirm("Are you sure you want to delete this Product !!!");
			if(!flag){
				return false;
			}
			let id = event.value;
			let params = "id="+id+"&action=deleteProduct";
			
			$.ajax({
	    		url: "product",
	    		data: params,
	    		type: 'post',
	    		success: function(data, textStatus, jqXHR){
	    			console.log(data.trim());
	    			if(data.trim()=="used"){
	    				$("#error").html("This Product Added By a Customer You don't have permision to Delete It!!!");
	    			}if(data.trim()== "success"){
	    				$("#success").html("Product deleted Successfully");
	    				loadProducts();
	    			}
	    		}
	    	})	
		};
		
		// Below function call the backend for editProduct
		function editProduct(event){
			let id = event.value;
			let params = "id="+id+"&action=getJSON";
			console.log(params);
			$.ajax({
	    		url: "product",
	    		data: params,
	    		type: 'post',
	    		success: function(data, textStatus, jqXHR){
	    			window.location.replace("manage-product.jsp?productName="+data.productName+"&stocks="+data.stocks+"&price="+data.price+"&productType="+data.productTypeId+"&action=Edit Product");
	    			//console.log(data.trim());
	    			//console.log("Product Name :: "+data.productName);
	    			//console.log("Product Type :: "+data.productType);
	    			//console.log("Total Stocks :: "+data.stocks);
	    			//console.log("Price :: "+data.price);
	    		}
	    	})	
		}
		
		// When Add Cart clicked redirecting to add-cart.jsp
		function addCart(event){
			let id = event.value;
			window.location.replace("add-cart.jsp?id="+id);
		}
		$("*").on("click", function(){
			$("#success").html("");
			$("#error").html("");
		});
		
	</script>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
      crossorigin="anonymous"
    ></script>
  </body>
</html>
