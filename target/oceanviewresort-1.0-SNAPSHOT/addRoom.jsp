<%--
Document : addROom
Created on : Jan 20, 2026, 7:52:57 PM
Author : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="com.mycompany.oceanviewresort.model.Rooms"%>

<%
    String user = (String) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Create Room | Ocean View</title>
        <link rel="stylesheet"
              href="<%= request.getContextPath()%>/css/style.css"
              type="text/css" />
    </head>
    <body>
        <div class="main-header">
            <h1 style="text-align: center;">Ocean View Resort - Create a New
                Room</h1>
        </div>




        <div class="page-add-room">

            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null && !errorMessage.isEmpty()) {
            %>
            <div style="background-color: #ffdddd; border: 1px solid red; color: red; padding: 10px; margin-bottom: 15px; border-radius: 5px;">
                <strong>Validation Error:</strong> <%= errorMessage %>
            </div>
            <%
                }
            %>

            <form action="addRoom" method="post" id="addRoomForm">

                <label for="room-name">Room Name</label><br>
                <input id="room-name" type="text" name="name" 
                       pattern="[a-zA-Z0-9\s-]+"
                       minlength="3" maxlength="100"
                       title="Only letters, numbers, spaces, and hyphens allowed (3-100 characters)"
                       required><br><br>

                <label for="room-type">Room Type</label><br>
                <select id="room-type" name="typeofroom" required>
                    <option value="1">Standard</option>
                    <option value="2">Deluxe</option>
                    <option value="3">Superior</option>
                </select><br><br>

                <label for="room-desc">Short Description</label><br>
                <textarea id="room-desc" name="shortDescription" rows="4"
                          minlength="10" maxlength="500"
                          title="Enter at least 10 characters"
                          required></textarea><br><br>

                <label for="room-capacity">Capacity</label><br>
                <input id="room-capacity" type="number" name="capacity" 
                       min="1" max="10"
                       title="Capacity must be between 1 and 10 people"
                       required><br>
                <small>How many people can stay in the room? (1-10)</small><br><br>

                <label for="room-price">Price per Night (LKR)</label><br>
                <input id="room-price" type="number" name="pricePerNight"
                       min="0.01" step="0.01" max="1000000"
                       title="Enter a valid price (greater than 0)"
                       required><br><br>

                <h3>Amenities</h3>

                <div class="checkbox-group">
                    <label class="checkbox-pill">
                        <input type="checkbox" name="wifi" value="1">
                        WiFi
                    </label>

                    <label class="checkbox-pill">
                        <input type="checkbox" name="Aircon" value="1">
                        Air Conditioning
                    </label>

                    <label class="checkbox-pill">
                        <input type="checkbox" name="Hotwater" value="1">
                        Hot Water
                    </label>
                </div>
                <br> <br>




                <button type="submit" class="btn btn-primary">Create Room</button>
                <br> <br>
                <a href="viewRoom">Go Back</a>
            </form>
        </div>

        <script>
            document.getElementById('addRoomForm').addEventListener('submit', function(e) {
                const name = document.getElementById('room-name').value.trim();
                const description = document.getElementById('room-desc').value.trim();
                const capacity = parseInt(document.getElementById('room-capacity').value);
                const price = parseFloat(document.getElementById('room-price').value);
                
                if (name.length < 3 || name.length > 100) {
                    alert('Room name must be between 3 and 100 characters.');
                    e.preventDefault();
                    return false;
                }
                
                if (!/^[a-zA-Z0-9\s-]+$/.test(name)) {
                    alert('Room name can only contain letters, numbers, spaces, and hyphens.');
                    e.preventDefault();
                    return false;
                }
                
                if (description.length < 10) {
                    alert('Short description must be at least 10 characters long.');
                    e.preventDefault();
                    return false;
                }
                
                if (description.length > 500) {
                    alert('Short description cannot exceed 500 characters.');
                    e.preventDefault();
                    return false;
                }
                
                if (isNaN(capacity) || capacity < 1 || capacity > 10) {
                    alert('Capacity must be between 1 and 10 people.');
                    e.preventDefault();
                    return false;
                }
                
                if (isNaN(price) || price <= 0) {
                    alert('Price per night must be greater than zero.');
                    e.preventDefault();
                    return false;
                }
                
                if (price > 1000000) {
                    alert('Price per night seems unreasonably high.');
                    e.preventDefault();
                    return false;
                }
                
                return confirm('Are you sure you want to create this room?');
            });
        </script>

    </body>
</html>
