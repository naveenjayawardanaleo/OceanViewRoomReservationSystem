/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.oceanviewresort.controller;

import com.mycompany.oceanviewresort.RoomsDAO;
import com.mycompany.oceanviewresort.model.Rooms;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dell
 */
@WebServlet(name = "addRoom", urlPatterns = {"/addRoom"})
public class addRoomServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        try {
            Rooms c = new Rooms();

            // Basic fields
            c.setName(req.getParameter("name"));
            c.setShortDescription(req.getParameter("shortDescription"));

            // Numbers
            c.setCapacity(Integer.parseInt(req.getParameter("capacity")));
            c.setRoom_type(Integer.parseInt(req.getParameter("typeofroom")));
            c.setPricePerNight(Double.parseDouble(req.getParameter("pricePerNight")));

            // Checkboxes (VERY IMPORTANT)
            c.setWifi(req.getParameter("wifi") != null ? 1 : 0);
            c.setAircon(req.getParameter("Aircon") != null ? 1 : 0);
            c.setHotwater(req.getParameter("Hotwater") != null ? 1 : 0);

            // Update
            new RoomsDAO().addRoom(c);

            res.sendRedirect("viewRoom");

        } catch (Exception e) {
            res.setContentType("text/html");
            res.getWriter().println("<h2 style='color:red'>Error updating Room</h2>");
            res.getWriter().println("<pre>");
            e.printStackTrace(res.getWriter()); // 👈 shows REAL error
            res.getWriter().println("</pre>");
        }

    }

}
