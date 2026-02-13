/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.oceanviewresort.controller;

import com.mycompany.oceanviewresort.ReservationDAO;
import com.mycompany.oceanviewresort.model.Reservation;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dell
 */
@WebServlet(name = "viewReservations", urlPatterns = {"/viewReservations"})
public class ViewReservationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                ReservationDAO dao = new ReservationDAO();
                dao.updateReservationStatus(id, "Completed");
            } catch (Exception e) {
                // Handle error
            }
        }
        // Redirect back to GET
        res.sendRedirect("viewReservations");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            ReservationDAO dao = new ReservationDAO();
            List<Reservation> reservations = dao.getPendingReservations();
            List<Reservation> completedReservations = dao.getCompletedReservations();
            
            req.setAttribute("reservationList", reservations);
            req.setAttribute("completedReservationList", completedReservations);
            req.getRequestDispatcher("viewReservation.jsp").forward(req, res);

        } catch (Exception e) {
            res.setContentType("text/html");
            res.getWriter().println("<h2 style='color:red'>Error loading reservations</h2>");
            res.getWriter().println("<pre>");
            e.printStackTrace(res.getWriter());
            res.getWriter().println("</pre>");
        }
    }
}
