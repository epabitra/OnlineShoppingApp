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
    <link rel="stylesheet" href="assets/css/register.css">
    <style>
        .dropdown-toggle{
            text-decoration: none;
        }
        select{
            width: 100%;
            height: 36px;
            border-style: none;
            border-radius: 5px;
        }
    </style>
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
                <form onsubmit=false; method="post">
                    <div class="form-body">
                        <table class="table table-striped">
                            <tr>
                              <td style="text-align: center; padding: 10px 0px;" colspan="4">
                                <label style="font-size: 20px;"><b>Checkout</b></label><br>
                                <span id="msg"></span>
                                <input type="hidden" id="role" value="${cookie.role.value}">
                                <input type="hidden" id="user_id" value="${cookie.user_id.value}">
                              </td>
                            </tr>
                            <tr>
                               <td>
                                Product Name : <span id="productName"></span><br>
                                Type : <span id="productType"></span>
                               </td>
                               <td>
                                Price<br>
                                <span id="price"></span>
                               </td>
                               <td>
                                Quantity<br>
                                <input type="number" name="quantity" id="quantity" style="width: 70px;" onchange=changed() onkeyup=changed() value="1">
                               </td>
                               <td>
                                Total <br>
                                <span id="totalPrice"></span>
                               </td>
                            </tr>
                            <tr>
                              <td colspan="3">
                              	Amount : <br>
                              	GST(18%) : <br>
                              	Total Amount : 
                              </td>
                              <td>
                              	<span id="amount"></span><br>
                              	<span id="gst"></span><br>
                              	<span id="totalAmount"></span>
                              </td>
                            </tr>
                            <tr>
                              <td colspan="4">
                                <button class="btn btn-success col-12 btn-lg mt-3" id="addCartBtn">Add Product</button>
                              </td>
                            </tr>
                        </table>
                    </div>
                </form>
                <a href="view-product.jsp">Return To View Product</a>
            </div>
        </div>
    </div>
   <script>
   	if(document.getElementById("role").value == "admin"){
   		window.location.replace("login.jsp");
   	}
   
  	const myUrl2 = new URL(window.location.toLocaleString());
	const id = myUrl2.searchParams.get('id');
   	let params = "id="+id+"&action=getJSON";
   	console.log(params);
   	
   	// These properties i declared for
   	// got the value whole of the page
   	let productId;
   	let productname;
   	let productType;
   	let stocks;
   	let price;
   	
	$.ajax({
		url: "product",
		data: params,
		type: 'post',
		success: function(data, textStatus, jqXHR){
			// Getting JSON Data
			productId = data.productId;
			console.log("ProductId :: "+productId);
			productName = data.productName;
			productType = data.productType;
			stocks = data.stocks;
			price = data.price;
			
			let quantity = document.getElementById("quantity").value;
			let amount = price*quantity;
			let gst = amount/100*18;
			// For getting 2 decimal digit on Decimal values
			gst = parseFloat(gst.toFixed(2));
			let totalAmount = amount+gst;
			// The below returns String value
			totalAmount = totalAmount.toFixed(2);
			
			$("#productName").html(productName);
			$("#productType").html(productType);
			$("#stocks").html(stocks);
			$("#price").html(price);
			$("#totalPrice").html(amount);
			$("#amount").html(amount);
			$("#gst").html(gst);
			$("#totalAmount").html(totalAmount);
		}
	})
	
	// The below two empty the error message box when someone
	// click on the button or input box
	$("#quantity").on("click", function(){
		$("#msg").html("");
	})
	$("#addCartBtn").on("click", function(){
		$("#msg").html("");
	})
   
	// When the quantity value change then this update the all price values
   	function changed(){
		let quantity = document.getElementById("quantity").value;
		let amount = price*quantity;
		let gst = amount/100*18;
		// For getting 2 decimal digit on Decimal values
		gst = parseFloat(gst.toFixed(2));
		let totalAmount = amount+gst;
		// The below returns a String value
		totalAmount = totalAmount.toFixed(2);
		
		$("#productName").html(productName);
		$("#productType").html(productType);
		$("#stocks").html(stocks);
		$("#price").html(price);
		$("#totalPrice").html(amount);
		$("#amount").html(amount);
		$("#gst").html(gst);
		$("#totalAmount").html(totalAmount);
   	}
	
	// Adding a product to the cart
	$("#addCartBtn").on("click", function(event){
		event.preventDefault();
		let flag = validation();
		if(!flag){
			return false;
		}
		// The below are get from the current window
		let userId = document.getElementById("user_id").value;
		let quantity = document.getElementById("quantity").value;
		let totalPrice = document.getElementById("totalAmount").innerHTML;
		
		// Some of the values are extracted from the global variable
		// which we declared during window loading, by extracting from DB
		let params = "userId="+userId+"&productId="+productId+"&quantity="+quantity+"&stocks="+stocks+"&totalPrice="+totalPrice+"&action=addCart";
		console.log(params);
		
		$.ajax({
			url: "product",
			data: params,
			type: 'post',
			success: function(data, textStatus, jqXHR){
				if(data.trim()){
					window.location.replace("view-product.jsp?success=Product Added to Cart Successfully...");
				}
			}
		})
	});
	
	// Quantity validation (Is quantity > Stocks or not)
	function validation(){
		let quantity = document.getElementById("quantity").value;
		console.log("Quantity :: "+quantity+"Stocks :: "+stocks);
		console.log("quantity>stocks :: "+quantity > stocks);
		if(parseInt(quantity) > parseInt(stocks)){
			$("#msg").html("Current Available Stocks are only "+stocks);
			$("#msg").css("color", "red");
			return false;
		}
		return true;
	}
	
   </script>
  <script
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
    crossorigin="anonymous"
  ></script>
</body>
</html>
