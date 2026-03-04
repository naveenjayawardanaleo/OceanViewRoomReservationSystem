package com.mycompany.oceanviewresort.controller;

import com.mycompany.oceanviewresort.RoomsDAO;
import com.mycompany.oceanviewresort.model.Rooms;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/addItem")
public class AddItemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        Rooms c = new Rooms();
        c.setName(req.getParameter("name"));
        c.setShortDescription(req.getParameter("shortdescription"));
//        c.setPrice(req.getParameter("price"));
//        c.setStock(req.getIntHeader("stock"));

//        try {
//            new ItemsDAO().addItem(c);
//            res.sendRedirect("dashboard.jsp");
//        } catch (Exception e) {
//            e.printStackTrace(); // shows error in NetBeans console
//            res.getWriter().println(e.getMessage());
//        }

    }

}