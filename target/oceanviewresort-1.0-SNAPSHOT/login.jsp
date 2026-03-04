<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String user = (String) session.getAttribute("user");
    if (user != null) {
        response.sendRedirect("dashboard.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>oceanviewresort - Login</title>
        <link rel="stylesheet" href="css/style.css" type="text/css" />

    </head>
    <body style="background-color: whitesmoke;">
        <div class="login-form">
            <h2>Login</h2>

            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null) {
            %>
                <div >
                    <strong>Error: </strong><%= errorMessage %>
                </div>
            <% } %>

            <form action="login" method="post">
                Username: <input type="text" name="username" required><br><br>
                Password: <input type="password" name="password" required><br><br>
                <input type="submit" value="Login" class="btn btn-primary">
            </form>
        </div>

    </body>
</html>
