<%-- 
    Document   : viewRooms
    Created on : Jan 20, 2026, 7:32:54 PM
    Author     : Dell
--%>

<%@page import="com.mycompany.oceanviewresort.model.Rooms"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Room List | Ocean Gate</title>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css" type="text/css" />
    </head>
    <body>

        <div class="main-header">
            <h1 style="text-align: center;">Ocean View Resort - List of Rooms</h1>
        </div>


        <a href="addRoom.jsp"class="btn btn-primary">Create a Room</a>

        <h1></h1>


        <table border="1" cellpadding="8">
            <tr>
                <th>Room ID</th>
                <th>Room Name</th>
                <th>Short Description</th>
                <th>Capacity (Person)</th>
                <th>Room Type</th>
                <th>Wifi</th>
                <th>Aircon</th>
                <th>Hotwater</th>                  
                <th>Price Per Night</th>        
                <th>Action</th>

            </tr>

            <%
                List<Rooms> rooms = (List<Rooms>) request.getAttribute("RoomList");
                if (rooms != null) {
                    for (Rooms c : rooms) {
            %>


            <tr>
                <td><%= c.getId()%></td>
                <td><%= c.getName()%></td>
                <td><%= c.getShortDescription()%></td>
                <td><%= c.getCapacity()%></td>


                <td>
                    <% 
                        int roomtype = c.getRoom_type();
                        switch(roomtype){
                            case 1:
                                out.println("Standard");
                                break;
                            
                            case 2:
                                out.print("Deluxe");
                                break;
                            
                            case 3:
                                out.print("Superior");
                                break;
                            
                        }
                        

                    %>
                </td>

                <td>
                    <% if (c.getWifi() == 1) {
                            out.println("Available");
                        } else {
                            out.println("Not Available");
                        }

                    %>
                </td>

                <td>
                    <% if (c.getAircon() == 1) {
                            out.println("Available");
                        } else {
                            out.println("Not Available");
                        }

                    %>
                </td>

                <td>
                    <% if (c.getHotwater() == 1) {
                            out.println("Available");
                        } else {
                            out.println("Not Available");
                        }

                    %>
                </td>
                <td>LKR <%= c.getPricePerNight()%></td>


                <td>
                    <a href="editRoom?id=<%= c.getId()%>">
                        <button>Edit</button>
                    </a>

                    <a href="deleteRoom?id=<%= c.getId()%>"
                       onclick="return confirm('Are you sure you want to delete this Room?');">
                        <button>Delete</button>
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
