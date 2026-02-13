<%-- 
    Document   : editCustomer
    Created on : Jan 20, 2026, 7:52:57 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="com.mycompany.oceanviewresort.model.Rooms"%>
<%
    Rooms c = (Rooms) request.getAttribute("room");
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Edit Customer</title>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css" type="text/css" />
    </head>
    <body>

        <div class="main-header">
            <h1 style="text-align: center;">Ocean View Resort - Help & Training</h1>
        </div>

        <h2>Edit Room</h2>

        <form action="editRoom" method="post">
            <input type="hidden" name="id" value="<%= c.getId()%>">

            Name: <input type="text" name="name" value="<%= c.getName()%>" required><br><br>

            Short Description: <textarea type="text" name="shortDescription" required><%= c.getShortDescription()%></textarea><br><br>



            Capacity <br><small>(How many People Allow) <input type="number" name="capacity" value="<%= c.getCapacity()%>" required><br><br>
                Type of Room
                <select name="typeofroom"> 
                    <option value="1" <% if (c.getRoom_type() == 1) {
                            out.print("selected");
                        } %>>Standard</option>
                    <option value="2" <% if (c.getRoom_type() == 2) {
                            out.print("selected");
                        } %>>Deluxe</option>
                    <option value="3" <% if (c.getRoom_type() == 3) {
                            out.print("selected");
                        }%>>Superior</option>
                </select><br><br>

                Wifi <input type="checkbox" name="wifi" value="1" <%= c.getWifi() == 1 ? "checked" : ""%>>
                Aircon <input type="checkbox" name="Aircon" value="1" <%= c.getAircon() == 1 ? "checked" : ""%>>
                Hotwater <input type="checkbox" name="Hotwater" value="1" <%= c.getHotwater() == 1 ? "checked" : ""%>>


                Price per Night: <input type="text" name="pricePerNight" value="<%= c.getPricePerNight()%>" required><br><br>


                <br><br>
                <input type="submit" value="Update Room">
                </form>

                <br>
                <a href="viewCustomers">Cancel</a>

                </body>
                </html>
