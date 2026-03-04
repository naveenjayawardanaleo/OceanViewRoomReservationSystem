package com.mycompany.oceanviewresort.controller;

import com.mycompany.oceanviewresort.RoomsDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/deleteRoom")
public class deleteRoomServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            new RoomsDAO().deleteRooms(id);
            res.sendRedirect("viewRoom");

        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().println("Error deleting customer");
        }
    }
}

