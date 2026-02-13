/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.oceanviewresort;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mycompany.oceanviewresort.util.DBConnection;

/**
 *
 * @author Dell
 */
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
            response.sendRedirect("dashboard.jsp");
        } else {
            response.getWriter().println("Invalid login");
        }

        if (username.length() != 10) {
            System.out.println("Invalid username");
        }
    }
}
