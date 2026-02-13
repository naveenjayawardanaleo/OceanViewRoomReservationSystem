/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.oceanviewresort.controller;

import com.mycompany.oceanviewresort.RoomsDAO;
import com.mycompany.oceanviewresort.model.Rooms;
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
@WebServlet(name = "viewRoom", urlPatterns = {"/viewRoom"})
public class ViewRoomsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            RoomsDAO dao = new RoomsDAO();
            List<Rooms> rooms = dao.getAllRooms();
            
            
            req.setAttribute("RoomList", rooms);
            req.getRequestDispatcher("viewRooms.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().println("Error loading items");
        }
    }

}
