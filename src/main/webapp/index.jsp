<%--
  Created by IntelliJ IDEA.
  User: amadeus
  Date: 12/6/2019
  Time: 9:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="css/bootstrap-grid.min.css">
    <link rel="stylesheet" href="css/bootstrap-reboot.min.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="js/additional-methods.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript" src="js/configuration.js"></script>
    <title>Hin Bank & FallittKasse.</title>
</head>
<body>
<script type="text/javascript">
    $(document).ready(function () {
        $("#sign_in_form").validate({
            rules: {
                login: "required",
                password: "required"
            },
            messages: {
                login: "Please enter login",
                password: "Please enter password"
            }
        })
    });
</script>
<div class="container">
    <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-6 mt-5">
            <div class="p-3 mb-2 bg-success text-white" style="display: none;" id="success"></div>
            <div class="p-3 mb-2 bg-danger text-white hide" style="display: none;" id="error"></div>
            <form id="sign_in_form">
                <div class="form-group">
                    <input class="form-control" required placeholder="Enter phone or email" id="login" name="login"/>
                </div>
                <div class="form-group">
                    <input class="form-control" required placeholder="Enter password" type="password" id="password" name="password"/>
                </div>
                <div class="form-group">
                    <button class="form-control btn btn-success" type="button" id="sign_in_but">Sign in</button>
                </div>
                <div class="form-group">
                    <a class="form-control btn btn-primary" id="sign_up" href="/signup">Sign up</a>
                </div>
            </form>
        </div>
        <div class="col-sm-3"></div>
    </div>
</div>
<script type="text/javascript">
    $("#sign_in_but").click(function () {
        let isValid = $("#sign_in_form").valid();

        if (isValid) {
            let successDiv = document.getElementById("success");
            let errorDiv = document.getElementById("error");
            successDiv.style.display = 'none';
            errorDiv.style.display = 'none';
            successDiv.innerHTML = null;
            errorDiv.innerHTML = null;
            $.ajax({
                url: config["contextPath"] + "signin",
                method: 'POST',
                data: form_to_json($("#sign_in_form")),
                success: function (data, textStatus, jqXHR) {
                    successDiv.innerHTML = textStatus;
                    successDiv.style.display = 'block';
                    window.location.href = config['host'] + config['contextPath'] + "atm";
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    errorDiv.innerHTML = textStatus + "<br>" + errorThrown;
                    errorDiv.style.display = 'block';
                }
            })
        }
    })
</script>
</body>
</html>
