<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>oceanviewresort - Login</title>
        <link rel="stylesheet" href="css/style.css" type="text/css" />

    </head>
    <body>
        <div class="login-form">
            <h2>Login</h2>

            <form action="login" method="post">
                Username: <input type="text" name="username" required><br><br>
                Password: <input type="password" name="password" required><br><br>
                <input type="submit" value="Login" class="btn btn-primary">
            </form>
        </div>

    </body>
</html>
