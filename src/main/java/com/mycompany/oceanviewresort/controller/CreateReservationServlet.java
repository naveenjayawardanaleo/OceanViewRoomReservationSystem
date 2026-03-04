package com.mycompany.oceanviewresort.controller;

import com.mycompany.oceanviewresort.ReservationDAO;
import com.mycompany.oceanviewresort.model.Reservation;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(name = "CreateReservation", urlPatterns = {"/CreateReservation"})
public class CreateReservationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String telephone = request.getParameter("telephone");
            String nicOrPassport = request.getParameter("nicOrPassport");
            String roomTypeStr = request.getParameter("roomType");
            String checkInStr = request.getParameter("checkIn");
            String checkOutStr = request.getParameter("checkOut");
            
            StringBuilder errors = new StringBuilder();
            
            if (name == null || name.trim().isEmpty()) {
                errors.append("Guest name is required. ");
            } else if (name.trim().length() < 2 || name.trim().length() > 100) {
                errors.append("Guest name must be between 2 and 100 characters. ");
            } else if (!name.matches("^[a-zA-Z\\s.]+$")) {
                errors.append("Guest name can only contain letters, spaces, and dots. ");
            }
            
            if (address == null || address.trim().isEmpty()) {
                errors.append("Address is required. ");
            } else if (address.trim().length() < 5) {
                errors.append("Address must be at least 5 characters long. ");
            }
            
            if (telephone == null || telephone.trim().isEmpty()) {
                errors.append("Telephone number is required. ");
            } else if (!telephone.matches("^[0-9]{10,15}$")) {
                errors.append("Telephone number must be 10-15 digits only. ");
            }
            
            if (nicOrPassport == null || nicOrPassport.trim().isEmpty()) {
                errors.append("NIC or Passport number is required. ");
            } else if (nicOrPassport.trim().length() < 5 || nicOrPassport.trim().length() > 20) {
                errors.append("NIC/Passport must be between 5 and 20 characters. ");
            } else if (!nicOrPassport.matches("^[a-zA-Z0-9]+$")) {
                errors.append("NIC/Passport can only contain letters and numbers. ");
            }
            
            int roomType = 0;
            if (roomTypeStr == null || roomTypeStr.trim().isEmpty()) {
                errors.append("Room type must be selected. ");
            } else {
                try {
                    roomType = Integer.parseInt(roomTypeStr);
                    if (roomType <= 0) {
                        errors.append("Invalid room type selected. ");
                    }
                } catch (NumberFormatException e) {
                    errors.append("Room type must be a valid number. ");
                }
            }
            
            Date checkIn = null;
            Date checkOut = null;
            LocalDate checkInLocal = null;
            LocalDate checkOutLocal = null;
            
            if (checkInStr == null || checkInStr.trim().isEmpty()) {
                checkInLocal = LocalDate.now();
                checkIn = Date.valueOf(checkInLocal);
            } else {
                try {
                    checkInLocal = LocalDate.parse(checkInStr);
                    LocalDate today = LocalDate.now();
                    if (checkInLocal.isBefore(today)) {
                        errors.append("Check-in date cannot be in the past. ");
                    }
                    checkIn = Date.valueOf(checkInLocal);
                } catch (RuntimeException e) {
                    errors.append("Invalid check-in date format. ");
                }
            }
            
            if (checkOutStr == null || checkOutStr.trim().isEmpty()) {
                errors.append("Check-out date is required. ");
            } else {
                try {
                    checkOutLocal = LocalDate.parse(checkOutStr);
                    checkOut = Date.valueOf(checkOutLocal);
                } catch (RuntimeException e) {
                    errors.append("Invalid check-out date format. ");
                }
            }
            
            if (checkInLocal != null && checkOutLocal != null) {
                if (!checkOutLocal.isAfter(checkInLocal)) {
                    errors.append("Check-out date must be after check-in date. ");
                }
                
                long diffInDays = ChronoUnit.DAYS.between(checkInLocal, checkOutLocal);
                if (diffInDays > 365) {
                    errors.append("Reservation cannot exceed 365 days. ");
                }
            }
            
            if (errors.length() > 0) {
                request.setAttribute("errorMessage", errors.toString());
                request.getRequestDispatcher("dashboard.jsp").forward(request, res);
                return;
            }
            
            Reservation r = new Reservation();
            r.setName(name.trim());
            r.setAddress(address.trim());
            r.setTelephone(telephone.trim());
            r.setNicOrPassport(nicOrPassport.trim());
            r.setRoomType(roomType);
            r.setCheckIn(checkIn);
            r.setCheckOut(checkOut);
            
            new ReservationDAO().addReservation(r);

            res.sendRedirect("viewReservation.jsp");
        } catch (Exception e) {
            res.setContentType("text/html");
            res.getWriter().println("<h2 style='color:red'>Error creating reservation</h2>");
            res.getWriter().println("<pre>");
            e.printStackTrace(res.getWriter());
            res.getWriter().println("</pre>");
        }

    }
}
