<%--
  Created by IntelliJ IDEA.
  User: amadeus
  Date: 12/6/2019
  Time: 9:10 PM
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
<div class="container">
    <div class="row">
        <div class="col-sm-4"></div>
        <div class="col-sm-4">
            <div class="p-3 mb-2 bg-danger text-white hide" style="display:none;" id="error_notifier"></div>
        </div>
        <div class="col-sm-4"></div>
    </div>
    <div class="row">
        <div class="col-sm-2"></div>
        <div class="col-sm-8 mt-5">
            <h2>Atm</h2>
            <ul class="nav nav-tabs">
                <li class="active"><a data-toggle="tab" href="#profile">My profile</a></li>
                <li><a data-toggle="tab" href="#transactions">Transactions</a></li>
                <li><a data-toggle="tab" href="#atm">Atm</a></li>
            </ul>
            <div class="tab-content">
                <div id="profile" class="tab-pane fade">
                    <h3>My profile</h3>
                    <script type="text/javascript">
                        $(document).ready(function () {
                            let errorNotifier = document.getElementById("error_notifier");
                            errorNotifier.innerHTML = null;
                            $.ajax({
                                url: config["contextPath"] + "client",
                                method: 'GET',
                                success: function (data, textStatus, jqXHR) {
                                    let profile = document.getElementById("profile");
                                    profile.innerHTML = "<p>id: " + data['id'] + "</p>";
                                    profile.innerHTML += "<p>name: " + data['username'] + "</p>";
                                    profile.innerHTML += "<p>email: " + data['email'] + "</p>";
                                    profile.innerHTML += "<p>phone: " + data['phone'] + "</p>";
                                    profile.innerHTML += "<p>description: " + data['description'] + "</p>";
                                    profile.innerHTML += "<p>createdAt: " + data['createdAt'] + "</p>";
                                    profile.innerHTML += "<p>banned: " + data['banned'] + "</p>";
                                    profile.innerHTML += "<p>serial: " + data['serial'] + "</p>";
                                    profile.innerHTML += "<p>code: " + data['code'] + "</p>";
                                    profile.innerHTML += "<p>address: " + data['address'] + "</p>";

                                    profile.innerHTML += "<h3>Cards:</h3>";
                                },
                                error: function (jqXHR, textStatus, errorThrown) {
                                    errorNotifier.style.display = 'block';
                                    errorNotifier.innerHTML = jqXHR.responseJSON.message
                                }
                            })
                        })
                    </script>
                </div>
                <div id="transactions" class="tab-pane fade">
                    <h3>Transactions</h3>
                </div>
                <div id="atm" class="tab-pane fade">
                    <h3>Atm</h3>
                </div>
            </div>
        </div>
        <div class="col-sm-2"></div>
    </div>
</div>
</body>
</html>
