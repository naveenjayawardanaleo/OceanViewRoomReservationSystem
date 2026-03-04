package com.mycompany.oceanviewresort.controller;

import com.mycompany.oceanviewresort.RoomsDAO;
import com.mycompany.oceanviewresort.model.Rooms;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(name = "addRoom", urlPatterns = {"/addRoom"})
public class addRoomServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        try {
            String name = req.getParameter("name");
            String shortDescription = req.getParameter("shortDescription");
            String capacityStr = req.getParameter("capacity");
            String roomTypeStr = req.getParameter("typeofroom");
            String priceStr = req.getParameter("pricePerNight");
            
            StringBuilder errors = new StringBuilder();
            
            if (name == null || name.trim().isEmpty()) {
                errors.append("Room name is required. ");
            } else if (name.trim().length() < 3 || name.trim().length() > 100) {
                errors.append("Room name must be between 3 and 100 characters. ");
            } else if (!name.matches("^[a-zA-Z0-9\\s-]+$")) {
                errors.append("Room name can only contain letters, numbers, spaces, and hyphens. ");
            }
            
            if (shortDescription == null || shortDescription.trim().isEmpty()) {
                errors.append("Short description is required. ");
            } else if (shortDescription.trim().length() < 10) {
                errors.append("Short description must be at least 10 characters long. ");
            } else if (shortDescription.trim().length() > 500) {
                errors.append("Short description cannot exceed 500 characters. ");
            }
            
            int capacity = 0;
            if (capacityStr == null || capacityStr.trim().isEmpty()) {
                errors.append("Capacity is required. ");
            } else {
                try {
                    capacity = Integer.parseInt(capacityStr);
                    if (capacity < 1 || capacity > 10) {
                        errors.append("Capacity must be between 1 and 10 people. ");
                    }
                } catch (NumberFormatException e) {
                    errors.append("Capacity must be a valid number. ");
                }
            }
            
            int roomType = 0;
            if (roomTypeStr == null || roomTypeStr.trim().isEmpty()) {
                errors.append("Room type is required. ");
            } else {
                try {
                    roomType = Integer.parseInt(roomTypeStr);
                    if (roomType < 1 || roomType > 3) {
                        errors.append("Room type must be Standard (1), Deluxe (2), or Superior (3). ");
                    }
                } catch (NumberFormatException e) {
                    errors.append("Room type must be a valid number. ");
                }
            }
            
            double pricePerNight = 0;
            if (priceStr == null || priceStr.trim().isEmpty()) {
                errors.append("Price per night is required. ");
            } else {
                try {
                    pricePerNight = Double.parseDouble(priceStr);
                    if (pricePerNight <= 0) {
                        errors.append("Price per night must be greater than zero. ");
                    } else if (pricePerNight > 1000000) {
                        errors.append("Price per night seems unreasonably high. ");
                    }
                } catch (NumberFormatException e) {
                    errors.append("Price per night must be a valid number. ");
                }
            }
            
            if (errors.length() > 0) {
                req.setAttribute("errorMessage", errors.toString());
                req.getRequestDispatcher("addRoom.jsp").forward(req, res);
                return;
            }
            
            Rooms c = new Rooms();
            c.setName(name.trim());
            c.setShortDescription(shortDescription.trim());
            c.setCapacity(capacity);
            c.setRoom_type(roomType);
            c.setPricePerNight(pricePerNight);
            c.setWifi(req.getParameter("wifi") != null ? 1 : 0);
            c.setAircon(req.getParameter("Aircon") != null ? 1 : 0);
            c.setHotwater(req.getParameter("Hotwater") != null ? 1 : 0);

            new RoomsDAO().addRoom(c);

            res.sendRedirect("viewRoom");

        } catch (Exception e) {
            res.setContentType("text/html");
            res.getWriter().println("<h2 style='color:red'>Error creating room</h2>");
            res.getWriter().println("<pre>");
            e.printStackTrace(res.getWriter());
            res.getWriter().println("</pre>");
        }

    }

}
