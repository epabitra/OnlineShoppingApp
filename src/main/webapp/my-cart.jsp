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
    <script
      src="https://code.jquery.com/jquery-3.6.0.js"
      integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
      crossorigin="anonymous"
    ></script>
  </head>
  <body>
    <!-- Navbar Start -->
    <jsp:include page="header.jsp"></jsp:include>
    <!-- Navbar End -->

    <!-- Container Start -->
    <div class="container1">
      <!-- Side Nav Start (left Container) -->
      <div class="left">
      	<%@ include file="side-nav.jsp" %>
      </div>
      <!-- Side Nav End -->

      <!-- Products Container Start (Right Container) -->
      <div class="right">
      	<input type="hidden" id="user_id" value="${cookie.user_id.value}">
        <table class="table table-striped">
          <thead>
            <tr class="table-primary">
                <th>NAME</th>
                <th>TYPE</th>
                <th>PRICE</th>
                <th>QUANTITY</th>
                <th>TOTAL AMOUNT</th>
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

	<script type="text/javascript">
		loadCart();
		// Below function call the backend for loading Products
		function loadCart(){
			let id = document.getElementById("user_id").value;
			let params = "id="+id+"&action=loadCart";
			
			$.ajax({
	    		url: "product",
	    		data: params,
	    		type: 'post',
	    		success: function(data, textStatus, jqXHR){
	    			$("tbody").html(data.trim());
	    		}
	    	})			
		}
		
		// The below function remove a product from the cart
		function removeProduct(event){
			let id = event.value;
			console.log("value :: "+id);
			let params = "id="+id+"&action=removeProduct";
			
			$.ajax({
	    		url: "product",
	    		data: params,
	    		type: 'post',
	    		success: function(data, textStatus, jqXHR){
	    			console.log(data.trim());
	    			loadCart();
	    		}
	    	})
		}
	</script>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
      crossorigin="anonymous"
    ></script>
  </body>
</html>
