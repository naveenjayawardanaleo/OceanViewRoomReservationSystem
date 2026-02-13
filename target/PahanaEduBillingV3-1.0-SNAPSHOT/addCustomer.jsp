<%-- 
    Document   : addCustomer
    Created on : Jan 19, 2026, 9:24:55 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Customer</title>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css" type="text/css" />
    </head>
    <body>
        <div class="main-header">
            <h1 style="text-align: center;">Ocean View Resort - Help & Training</h1>
        </div>
        <h1>Add Customer</h1>

        <form action="addCustomer" method="post">
            Name: <input type="text" name="name"><br>
            Address: <input type="text" name="address"><br>
            Phone: <input type="text" name="telephone"><br>
            <input type="submit" value="Save">
        </form>
    </body>
</html>
