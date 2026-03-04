package com.mycompany.oceanviewresort;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mycompany.oceanviewresort.util.DBConnection;

@WebServlet(name = "LoginServlet", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean isValid = false;
        String sql = "SELECT 1 FROM users WHERE username = ? AND password = ?";

        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            throw new ServletException("Database connection unavailable.");
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    isValid = true;
                }
            }
        } catch (Exception e) {
            throw new ServletException("Login failed due to a server error.", e);
        }

        if (isValid) {
            request.getSession().setAttribute("user", username);
            response.sendRedirect("dashboard.jsp");
        } else {
            request.setAttribute("errorMessage", "Invalid username or password. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

        if (username.length() != 10) {
            System.out.println("Invalid username");
        }
    }
}
