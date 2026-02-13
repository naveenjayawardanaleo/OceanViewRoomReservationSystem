/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.oceanviewresort.controller;

import com.mycompany.oceanviewresort.ReservationDAO;
import com.mycompany.oceanviewresort.model.Reservation;
import com.mycompany.oceanviewresort.util.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dell
 */
@WebServlet(name = "CreateReservation", urlPatterns = {"/CreateReservation"})
public class CreateReservationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            Reservation r = new Reservation();

            r.setName(request.getParameter("name"));
            r.setAddress(request.getParameter("address"));

            r.setTelephone(request.getParameter("telephone"));
            r.setNicOrPassport(request.getParameter("nicOrPassport"));
            r.setRoomType(Integer.parseInt(request.getParameter("roomType")));

            r.setCheckIn(java.sql.Date.valueOf(request.getParameter("checkIn")));
            r.setCheckOut(java.sql.Date.valueOf(request.getParameter("checkOut")));


            // Update
            new ReservationDAO().addReservation(r);

            res.sendRedirect("viewReservation.jsp");
        } catch (Exception e) {
            res.setContentType("text/html");
            res.getWriter().println("<h2 style='color:red'>Error updating Room</h2>");
            res.getWriter().println("<pre>");
            e.printStackTrace(res.getWriter());
            res.getWriter().println("</pre>");
        }

    }
}
