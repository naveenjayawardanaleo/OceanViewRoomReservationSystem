<%--
    Document   : help
    Created on : Feb 3, 2026
    Author     : Dell
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Help & Training | Ocean View Resort</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css" type="text/css" />
    <style>
        .help-content { max-width: 800px; margin: 20px auto; padding: 20px; background-color: white; border: 1px solid #ccc; }
        .help-section { margin-bottom: 30px; }
        .help-section h3 { color: #004080; border-bottom: 1px solid #004080; padding-bottom: 5px; }
        .help-section ul { padding-left: 20px; }
        .help-section li { margin-bottom: 10px; }
    </style>
</head>
<body>
    <div class="main-header">
        <h1 style="text-align: center;">Ocean View Resort - Help & Training</h1>
    </div>

    <div class="help-content">
        <div class="help-section">
            <h3>Welcome to Ocean View Resort Management System</h3>
            <p>This system helps you manage reservations, rooms, and billing efficiently. Follow the guidelines below to ensure smooth operations and excellent customer service.</p>
        </div>

        <div class="help-section">
            <h3>Staff Training: How to Use the System</h3>
            <ul>
                <li><strong>Dashboard:</strong> The main page shows an overview. Use the sidebar to navigate to different sections.</li>
                <li><strong>Reservations:</strong> View ongoing and completed reservations. Use "Bill Print" to generate invoices and "Complete Reservation" to finalize paid bookings (confirm payment first).</li>
                <li><strong>Rooms:</strong> Manage room inventory, add new rooms, edit details, or remove rooms as needed.</li>
                <li><strong>Creating Reservations:</strong> Enter guest details, select room type, and dates. The system calculates costs automatically.</li>
                <li><strong>Billing:</strong> Print bills with all charges (room rate, service charge 10%, VAT 18%). Completed reservations show "PAID" watermark.</li>
            </ul>
        </div>

        <div class="help-section">
            <h3>Ensuring Customer Comfort and Happiness</h3>
            <ul>
                <li><strong>Greeting:</strong> Always greet guests warmly and confirm their reservation details upon arrival.</li>
                <li><strong>Room Assignment:</strong> Ensure rooms match the booked type and amenities. Check WiFi, AC, and hot water availability.</li>
                <li><strong>Billing Transparency:</strong> Explain all charges clearly. Show the bill breakdown and confirm payment before completion.</li>
                <li><strong>Problem Solving:</strong> Address any issues promptly. If a room is unavailable, offer alternatives or compensation.</li>
                <li><strong>Feedback:</strong> Ask for feedback at checkout and use it to improve services.</li>
                <li><strong>Safety & Cleanliness:</strong> Maintain high standards of cleanliness and safety for guest comfort.</li>
                <li><strong>Personal Touch:</strong> Remember guest names, offer local recommendations, and provide a memorable experience.</li>
            </ul>
        </div>

        <div class="help-section">
            <h3>System Tips</h3>
            <ul>
                <li>Always confirm payment before marking reservations as completed.</li>
                <li>Use the "Bill Print" feature to provide physical copies to guests.</li>
                <li>Regularly check for completed reservations to maintain accurate records.</li>
                <li>Report any system issues to the IT team immediately.</li>
            </ul>
        </div>

        <div style="text-align: center; margin-top: 30px;">
            <a href="dashboard.jsp"><button>Back to Dashboard</button></a>
        </div>
    </div>
</body>
</html>