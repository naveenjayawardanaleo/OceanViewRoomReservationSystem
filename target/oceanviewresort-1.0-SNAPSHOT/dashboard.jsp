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
        <%
            String user = (String) session.getAttribute("user");
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }
        %>
        <div class="main-header">
            <img src="<%= request.getContextPath() %>/images/logo.png" alt="Ocean View Resort Logo" style="height: 100px; display: block; margin: 0 auto 10px;">
            <h1 style="text-align: center;">Welcome to Ocean View Resort </h1>
            <h2 style="text-align: center;"> Online room reservation System</h2>
            <p style="text-align: center; color: #fff; margin: 0;"><strong>Logged in as: <%= user %></strong></p>

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
                <a class="sidebar-btn"  href="logout" style="background-color: red;">Logout</a>

            </div>
            <div class="pos-area">
                <%
                    String errorMessage = (String) request.getAttribute("errorMessage");
                    if (errorMessage != null && !errorMessage.isEmpty()) {
                %>
                <div style="background-color: #ffdddd; border: 1px solid red; color: red; padding: 10px; margin-bottom: 15px; border-radius: 5px;">
                    <strong>Validation Error:</strong> <%= errorMessage %>
                </div>
                <%
                    }

                    String todayDate = java.time.LocalDate.now().toString();
                    String tomorrowDate = java.time.LocalDate.now().plusDays(1).toString();
                    String checkInValue = request.getParameter("checkIn");
                    if (checkInValue == null || checkInValue.trim().isEmpty()) {
                        checkInValue = todayDate;
                    }
                    String checkOutValue = request.getParameter("checkOut");
                %>
                <form action="CreateReservation" method="post" id="reservationForm">
                    <div class="posdiv">
                        <div style="margin: 0 10px;">
                            <label>Guest Name:</label><br>
                            <input type="text" name="name" id="guestName" 
                                   pattern="[a-zA-Z\s.]+" 
                                   minlength="2" maxlength="100"
                                   title="Only letters, spaces, and dots allowed"
                                   required><br><br>

                            <label>Address:</label><br>
                            <textarea name="address" id="address" 
                                      minlength="5"
                                      required style="width: 100%;"></textarea><br><br>

                            <label>Telephone:</label><br>
                            <input type="tel" name="telephone" id="telephone" 
                                   pattern="[0-9]{10,15}" 
                                   title="Enter 10-15 digit phone number"
                                   required><br><br>


                            <button type="submit" class="btn btn-primary">Save Reservation</button>

                        </div>
                        <div style="margin: 0 10px;">
                            
                            <label>NIC / Passport Number:</label><br>
                            <input type="text" name="nicOrPassport" id="nicOrPassport" 
                                   pattern="[a-zA-Z0-9]+"
                                   minlength="5" maxlength="20"
                                   title="Only letters and numbers allowed, 5-20 characters"
                                   required><br><br>

                            <label>Room Type:</label><br>
                            <select name="roomType" id="roomType" required>
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
                            <input style="display: none;" type="date" name="checkIn" id="checkInDate" readonly required value="<%= checkInValue %>" min="<%= todayDate %>"><br>

                            <label>Check-out Date:</label><br>
                            <input type="date" name="checkOut" id="checkOutDate" required value="<%= checkOutValue != null ? checkOutValue : "" %>" min="<%= tomorrowDate %>"><br><br>
                        </div>


                    </div>




                </form>

            </div>
        </div>
        <ul>

            <script>
                function formatLocalDate(date) {
                    const year = date.getFullYear();
                    const month = String(date.getMonth() + 1).padStart(2, '0');
                    const day = String(date.getDate()).padStart(2, '0');
                    return `${year}-${month}-${day}`;
                }

                const todayDate = new Date();
                const today = formatLocalDate(todayDate);
                const tomorrowDate = new Date(todayDate);
                tomorrowDate.setDate(tomorrowDate.getDate() + 1);
                const tomorrow = formatLocalDate(tomorrowDate);

                const checkInInput = document.getElementById("checkInDate");
                const checkOutInput = document.getElementById("checkOutDate");

                if (!checkInInput.value) {
                    checkInInput.value = today;
                }
                checkInInput.min = today;
                checkOutInput.min = tomorrow;
                if (!checkOutInput.value) {
                    checkOutInput.value = tomorrow;
                }
                
                document.getElementById('reservationForm').addEventListener('submit', function(e) {
                    const name = document.getElementById('guestName').value.trim();
                    const address = document.getElementById('address').value.trim();
                    const telephone = document.getElementById('telephone').value.trim();
                    const nicOrPassport = document.getElementById('nicOrPassport').value.trim();
                    const roomType = document.getElementById('roomType').value;
                    const checkIn = new Date(document.getElementById("checkInDate").value);
                    const checkOut = new Date(document.getElementById("checkOutDate").value);
                    
                    if (name.length < 2 || name.length > 100) {
                        alert('Guest name must be between 2 and 100 characters.');
                        e.preventDefault();
                        return false;
                    }
                    
                    if (!/^[a-zA-Z\s.]+$/.test(name)) {
                        alert('Guest name can only contain letters, spaces, and dots.');
                        e.preventDefault();
                        return false;
                    }
                    
                    if (address.length < 5) {
                        alert('Address must be at least 5 characters long.');
                        e.preventDefault();
                        return false;
                    }
                    
                    if (!/^[0-9]{10,15}$/.test(telephone)) {
                        alert('Telephone number must be 10-15 digits only (no spaces or special characters).');
                        e.preventDefault();
                        return false;
                    }
                    
                    if (nicOrPassport.length < 5 || nicOrPassport.length > 20) {
                        alert('NIC/Passport must be between 5 and 20 characters.');
                        e.preventDefault();
                        return false;
                    }
                    
                    if (!/^[a-zA-Z0-9]+$/.test(nicOrPassport)) {
                        alert('NIC/Passport can only contain letters and numbers (no spaces or special characters).');
                        e.preventDefault();
                        return false;
                    }
                    
                    if (!roomType || roomType === '') {
                        alert('Please select a room type.');
                        e.preventDefault();
                        return false;
                    }
                    
                    if (checkOut <= checkIn) {
                        alert('Check-out date must be after check-in date.');
                        e.preventDefault();
                        return false;
                    }
                    
                    const diffInDays = Math.floor((checkOut - checkIn) / (1000 * 60 * 60 * 24));
                    if (diffInDays > 365) {
                        alert('Reservation cannot exceed 365 days.');
                        e.preventDefault();
                        return false;
                    }
                    
                    return confirm('Are you sure you want to create this reservation?');
                });
            </script>
        </ul>
    </body>
</html>
