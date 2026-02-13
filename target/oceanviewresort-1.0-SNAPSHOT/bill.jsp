<%--
    Document   : bill
    Created on : Feb 3, 2026
    Author     : Assistant
--%>
<%@page import="com.mycompany.oceanviewresort.model.Reservation"%>
<%@page import="com.mycompany.oceanviewresort.model.Rooms"%>
<%@ page contentType="text/html;charset=UTF-8" %>


<!DOCTYPE html>
<html>
<head>
    <title>Bill Print | Ocean View Resort</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css" type="text/css" />
</head>
<body>
   
    <div class="bill">
        <div class="header">
            <img src="images/logo.png" alt="Ocean View Resort Logo" style="height: 200px; display: block; margin: 0 auto 10px;">

        </div>

        <%
            Reservation reservation = (Reservation) request.getAttribute("reservation");
            Rooms room = (Rooms) request.getAttribute("room");
            String invoiceNumber = (String) request.getAttribute("invoiceNumber");
            Long nights = (Long) request.getAttribute("nights");
            Double subtotal = (Double) request.getAttribute("subtotal");
            Double serviceCharge = (Double) request.getAttribute("serviceCharge");
            Double vat = (Double) request.getAttribute("vat");
            Double total = (Double) request.getAttribute("total");

            if (reservation != null && room != null) {
        %>

        <div class="details">
            <h3>Invoice Details</h3>
            <table>
                <tr><td>Invoice Number:</td><td><%= invoiceNumber %></td></tr>
                <tr><td>Reservation ID:</td><td><%= reservation.getId() %></td></tr>
                <tr><td>Guest Name:</td><td><%= reservation.getName() %></td></tr>
                <tr><td>Address:</td><td><%= reservation.getAddress() %></td></tr>
                <tr><td>Telephone:</td><td><%= reservation.getTelephone() %></td></tr>
                <tr><td>Check In:</td><td><%= reservation.getCheckIn() %></td></tr>
                <tr><td>Check Out:</td><td><%= reservation.getCheckOut() %></td></tr>
                <tr><td>Room Type:</td><td><%= room.getName() %></td></tr>
                <tr><td>Room Description:</td><td><%= room.getShortDescription() %></td></tr>
                <tr><td>Capacity:</td><td><%= room.getCapacity() %></td></tr>
                <tr><td>Amenities:</td><td>
                    <%= room.getWifi() == 1 ? "&#10003; WiFi " : "" %>
                    <%= room.getAircon() == 1 ? "&#10003; Air Conditioning " : "" %>
                    <%= room.getHotwater() == 1 ? "&#10003; Hot Water" : "" %>
                </td></tr>
                <tr><td>Price per Night:</td><td>LKR <%= room.getPricePerNight() %></td></tr>
                <tr><td>Number of Nights:</td><td><%= nights %></td></tr>
            </table>
        </div>

        <div class="total">
            <table style="width: 100%; border-collapse: collapse;">
                <tr><td>Subtotal:</td><td style="text-align: right;">LKR <%= String.format("%.2f", subtotal) %></td></tr>
                <tr><td>Service Charge (10%):</td><td style="text-align: right;">LKR <%= String.format("%.2f", serviceCharge) %></td></tr>
                <tr><td>VAT (18%):</td><td style="text-align: right;">LKR <%= String.format("%.2f", vat) %></td></tr>
                <tr style="border-top: 1px solid #000; font-weight: bold;"><td>Total Amount:</td><td style="text-align: right;">LKR <%= String.format("%.2f", total) %></td></tr>
            </table>
        </div>

        <%
            } else {
        %>
        <p>Reservation not found.</p>
        <%
            }
        %>

        <% if (reservation != null && "Completed".equals(reservation.getStatus())) { %>
        <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%) rotate(-45deg); font-size: 72px; color: rgba(255, 0, 0, 0.5); font-weight: bold; z-index: 10; pointer-events: none;">
            PAID
        </div>
        <% } %>

        <div class="no-print" style="text-align: center; margin-top: 20px;">
            <button onclick="window.print()">Print Bill</button>
            <a href="viewReservations"><button>Back to Reservations</button></a>
        </div>
    </div>
</body>
</html>
