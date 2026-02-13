/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oceanviewresort.controller;

import com.mycompany.oceanviewresort.ReservationDAO;
import com.mycompany.oceanviewresort.RoomsDAO;
import com.mycompany.oceanviewresort.model.Reservation;
import com.mycompany.oceanviewresort.model.Rooms;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Assistant
 */
@WebServlet(name = "BillPrintServlet", urlPatterns = {"/billPrint"})
public class BillPrintServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                ReservationDAO rdao = new ReservationDAO();
                RoomsDAO roomDao = new RoomsDAO();
                Reservation reservation = rdao.getReservationById(id);
                if (reservation != null) {
                    Rooms room = roomDao.getRoomByType(reservation.getRoomType());
                    if (room != null) {
                        req.setAttribute("reservation", reservation);
                        req.setAttribute("room", room);
                        // Generate invoice number
                        String invoiceNumber = "INV-" + reservation.getId();
                        req.setAttribute("invoiceNumber", invoiceNumber);
                        // Calculate total
                        long nights = (reservation.getCheckOut().getTime() - reservation.getCheckIn().getTime()) / (1000 * 60 * 60 * 24);
                        double subtotal = room.getPricePerNight() * nights;
                        double serviceCharge = subtotal * 0.10;
                        double vat = (subtotal + serviceCharge) * 0.18;
                        double total = subtotal + serviceCharge + vat;
                        req.setAttribute("nights", nights);
                        req.setAttribute("subtotal", subtotal);
                        req.setAttribute("serviceCharge", serviceCharge);
                        req.setAttribute("vat", vat);
                        req.setAttribute("total", total);
                        req.getRequestDispatcher("bill.jsp").forward(req, res);
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // If not found or error, redirect to reservations
        res.sendRedirect("viewReservations");
    }
}