<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="css/cabinet.css">

    <title>Cabinet</title>
</head>
<body>

<div class="page-wrapper chiller-theme toggled">
    <!-- sidebar-wrapper  -->
    <jsp:include page="sidebar.jsp"></jsp:include>

    <main class="page-content">
        <div class="container-fluid">
            <form id="form1">
                <fieldset>
                    <div class="input-block">
                        <label for="productName">Name:</label>
                        <input id="productName" type="text" required>
                    </div>
                    <div class="input-block">
                        <label for="productDescription">Description:</label>
                        <input id="productDescription" type="text" required>
                    </div>
                    <div class="input-block">
                        <label for="productPrice">Price:</label>
                        <input id="productPrice" type="number" required>
                    </div>
                </fieldset>
                <button type="submit" class="btn-createProduct">Create product</button>
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <b>Success!</b> You added a new book to catalog of products!
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </form>
        </div>
    </main>

</div>


<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>

<script src="js/addProduct.js"></script>

</body>
</html>