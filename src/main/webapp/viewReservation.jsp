<%-- 
    Document   : viewReservation
    Created on : Jan 24, 2026, 1:05:01 PM
    Author     : Dell
--%>
<%@page import="com.mycompany.oceanviewresort.model.Reservation"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Reservation List | Ocean Gate</title>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/style.css" type="text/css" />
    </head>
    <body>

        <div class="main-header">
            <h1 style="text-align: center;">Ocean View Resort - List of Reservations</h1>
        </div>

        <a href="dashboard.jsp"class="btn btn-primary">Create a Reservation</a>

        <h2>Ongoing Reservations</h2>

        <table border="1" cellpadding="8">
            <tr>
                <th>Reservation ID</th>
                <th>Customer Name</th>
                <th>Customer Address</th>
                <th>Telephone</th>
                <th>NIC / Passport</th>
                <th>Room Type</th>

                <th>Check In</th>
                <th>Check Out</th>
                <th>Status</th>
            </tr>

            <%
                List<Reservation> reservations
                        = (List<Reservation>) request.getAttribute("reservationList");

                if (reservations != null) {
                    for (Reservation r : reservations) {
            %>
            <tr>
                <td><%= r.getId()%></td>
                <td><%= r.getName()%></td>
                <td><%= r.getAddress()%></td>
                <td><%= r.getTelephone()%></td>
                <td><%= r.getNicOrPassport()%></td>
                <td><%= r.getRoomType()%></td>
                <td><%= r.getCheckIn()%></td>
                <td><%= r.getCheckOut()%></td>
                <td>
                    <a href="billPrint?id=<%= r.getId()%>">
                        <button>Bill Print</button>
                    </a>
                    <form action="viewReservations" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="<%= r.getId()%>">
                        <button type="submit" onclick="return confirm('Has the customer paid?')">Complete Reservation</button>
                    </form>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>

        <h2>Completed Reservations</h2>

        <table border="1" cellpadding="8">
            <tr>
                <th>Reservation ID</th>
                <th>Customer Name</th>
                <th>Customer Address</th>
                <th>Telephone</th>
                <th>NIC / Passport</th>
                <th>Room Type</th>
                <th>Check In</th>
                <th>Check Out</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>

            <%
                List<Reservation> completedReservations
                        = (List<Reservation>) request.getAttribute("completedReservationList");

                if (completedReservations != null) {
                    for (Reservation r : completedReservations) {
            %>
            <tr>
                <td><%= r.getId()%></td>
                <td><%= r.getName()%></td>
                <td><%= r.getAddress()%></td>
                <td><%= r.getTelephone()%></td>
                <td><%= r.getNicOrPassport()%></td>
                <td><%= r.getRoomType()%></td>
                <td><%= r.getCheckIn()%></td>
                <td><%= r.getCheckOut()%></td>
                <td><%= r.getStatus()%></td>
                <td>
                    <a href="billPrint?id=<%= r.getId()%>">
                        <button>Check Bill</button>
                    </a>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>

        <br>
        <a href="dashboard.jsp">Back to Dashboard</a>

    </body>
</html>
