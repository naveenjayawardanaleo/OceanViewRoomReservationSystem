<%--
Document : addROom
Created on : Jan 20, 2026, 7:52:57 PM
Author : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="com.mycompany.oceanviewresort.model.Rooms"%>

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


            <form action="addRoom" method="post">

                <label for="room-name">Room Name</label><br>
                <input id="room-name" type="text" name="name" required><br><br>

                <label for="room-type">Room Type</label><br>
                <select id="room-type" name="typeofroom">
                    <option value="1">Standard</option>
                    <option value="2">Deluxe</option>
                    <option value="3">Superior</option>
                </select><br><br>

                <label for="room-desc">Short Description</label><br>
                <textarea id="room-desc" name="shortDescription" rows="4"
                          required></textarea><br><br>

                <label for="room-capacity">Capacity</label><br>
                <input id="room-capacity" type="number" name="capacity" min="1"
                       required><br>
                <small>How many people can stay in the room?</small><br><br>

                <label for="room-price">Price per Night (LKR)</label><br>
                <input id="room-price" type="text" name="pricePerNight"
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

    </body>
</html>
