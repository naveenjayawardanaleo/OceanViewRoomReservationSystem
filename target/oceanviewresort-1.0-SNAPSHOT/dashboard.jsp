<%-- 
    Document   : dashboard
    Created on : Jan 19, 2026, 9:00:38?PM
    Author     : Dell
--%>

<%@page import="com.mycompany.oceanviewresort.RoomsDAO"%>
<%@page import="com.mycompany.oceanviewresort.model.Rooms"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Comparator"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Dashboard</title>

        <link rel="stylesheet" href="css/style.css" type="text/css" />
    </head>
    <body>
        <div class="main-header">
            <img src="<%= request.getContextPath() %>/images/logo.png" alt="Ocean View Resort Logo" style="height: 100px; display: block; margin: 0 auto 10px;">
            <h1 style="text-align: center;">Welcome to Ocean View Resort </h1>
            <h2 style="text-align: center;"> Online room reservation System</h2>

        </div>

        <%
            RoomsDAO roomsDao = new RoomsDAO();
            List<Rooms> rooms = null;
            try {
                rooms = roomsDao.getAllRooms();
                if (rooms != null) {
                    Collections.sort(rooms, Comparator.comparingDouble(Rooms::getPricePerNight));
                }
            } catch (Exception e) {
                // Handle error
            }
        %>

        <div class="container">
            <div class="sidebar">
                <a  class="sidebar-btn" href="viewReservations">Reservations</a>
                <a  class="sidebar-btn" href="viewRoom">Rooms</a>
                <a class="sidebar-btn"  href="help.jsp">Help</a> 
                <a class="sidebar-btn"  href="login.jsp" style="background-color: red;">Logout</a>

            </div>
            <div class="pos-area">
                <form action="CreateReservation" method="post">
                    <div class="posdiv">
                        <div style="margin: 0 10px;">
                            <label>Guest Name:</label><br>
                            <input type="text" name="name" required><br><br>

                            <label>Address:</label><br>
                            <textarea name="address" required style="width: 100%;"></textarea><br><br>

                            <label>Telephone:</label><br>
                            <input type="text" name="telephone" required><br><br>


                            <button type="submit" class="btn btn-primary">Save Reservation</button>

                        </div>
                        <div style="margin: 0 10px;">
                            
                            <label>NIC / Passport Number:</label><br>
                            <input type="text" name="nicOrPassport" required><br><br>

                            <label>Room Type:</label><br>
                            <select name="roomType" required>
                                <option value="">Select Room Type</option>
                                <%
                                    if (rooms != null) {
                                        for (Rooms room : rooms) {
                                %>
                                <option value="<%= room.getRoom_type() %>"><%= room.getName() %> (Capacity: <%= room.getCapacity() %>) - LKR <%= room.getPricePerNight() %> per night</option>
                                <%
                                        }
                                    }
                                %>
                            </select>

                            <label style="display: none;">Check-in Date:</label><br>
                            <input style="display: none;" type="date" name="checkIn"id="checkInDate" readonly required><br>

                            <label>Check-out Date:</label><br>
                            <input type="date" name="checkOut" id="checkOutDate" required><br><br>
                        </div>


                    </div>




                </form>

            </div>
        </div>
        <ul>

            <script>
                const today = new Date().toISOString().split('T')[0];
                const tomorrow = new Date();
                tomorrow.setDate(tomorrow.getDate() + 1);
                const tomorrowStr = tomorrow.toISOString().split('T')[0];
                
                document.getElementById("checkInDate").value = today;
                document.getElementById("checkInDate").min = today;
                document.getElementById("checkOutDate").min = tomorrowStr;
                
                // Form validation
                document.querySelector('form').addEventListener('submit', function(e) {
                    const checkIn = new Date(document.getElementById("checkInDate").value);
                    const checkOut = new Date(document.getElementById("checkOutDate").value);
                    
                    if (checkOut <= checkIn) {
                        alert('Check-out date must be after check-in date.');
                        e.preventDefault();
                        return false;
                    }
                    
                    return confirm('Are you sure you want to create this reservation?');
                });
            </script>
        </ul>
    </body>
</html>
