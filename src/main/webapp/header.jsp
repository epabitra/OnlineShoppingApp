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
  </head>
  <body>
    <div class="container2" style="display: flex">
      <div class="header-left px-3 py-3" style="flex-basis: 50%">
        <a href="login.jsp"><img
          src="assets/images/logo.jpg"
          alt="loading..."
          style="width: 40px; height: 40px"
        /></a>
      </div>
      <div class="header-right p-3" style="text-align: right; flex-basis: 50%">
        <a><button class="btn btn-primary" onclick="logout()">Logout</button></a>
      </div>
    </div>
    <script>
    	function logout(){
		    let params = "";
			$.ajax({
				url: "logout",
				data: params,
				type: 'get',
				success: function(data, textStatus, jqXHR){
					window.location.replace("login.jsp");
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
