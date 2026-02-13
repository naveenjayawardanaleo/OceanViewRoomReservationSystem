package com.mycompany.oceanviewresort;

import com.mycompany.oceanviewresort.model.Reservation;
import com.mycompany.oceanviewresort.util.DBConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReservationDAOTest {
    @Test
    void addReservationDefaultsStatusToPending() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setName("Alex");
        reservation.setAddress("123 Beach Road");
        reservation.setTelephone("555-1000");
        reservation.setNicOrPassport("NIC123");
        reservation.setRoomType(2);
        reservation.setCheckIn(Date.valueOf("2026-02-01"));
        reservation.setCheckOut(Date.valueOf("2026-02-02"));
        reservation.setStatus(null);

        Connection con = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        try (MockedStatic<DBConnection> db = Mockito.mockStatic(DBConnection.class)) {
            db.when(DBConnection::getConnection).thenReturn(con);

            ReservationDAO dao = new ReservationDAO();

            boolean result = dao.addReservation(reservation);

            assertTrue(result);
            verify(ps).setString(1, "Alex");
            verify(ps).setString(2, "123 Beach Road");
            verify(ps).setString(3, "555-1000");
            verify(ps).setString(4, "NIC123");
            verify(ps).setInt(5, 2);
            verify(ps).setDate(6, reservation.getCheckIn());
            verify(ps).setDate(7, reservation.getCheckOut());
            verify(ps).setString(8, "Pending");
            verify(ps).close();
        }
    }

    @Test
    void addReservationUsesProvidedStatus() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setName("Sam");
        reservation.setAddress("45 Ocean Ave");
        reservation.setTelephone("555-2000");
        reservation.setNicOrPassport("PASS456");
        reservation.setRoomType(1);
        reservation.setCheckIn(Date.valueOf("2026-02-05"));
        reservation.setCheckOut(Date.valueOf("2026-02-07"));
        reservation.setStatus("Completed");

        Connection con = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        try (MockedStatic<DBConnection> db = Mockito.mockStatic(DBConnection.class)) {
            db.when(DBConnection::getConnection).thenReturn(con);

            ReservationDAO dao = new ReservationDAO();

            boolean result = dao.addReservation(reservation);

            assertTrue(result);
            verify(ps).setString(8, "Completed");
            verify(ps).close();
        }
    }

    @Test
    void getReservationByIdReturnsReservation() throws Exception {
        Connection con = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("id")).thenReturn(5);
        when(rs.getString("name")).thenReturn("Ava");
        when(rs.getString("address")).thenReturn("Coast Road");
        when(rs.getString("telephone")).thenReturn("555-9000");
        when(rs.getString("nic_passport")).thenReturn("NIC900");
        when(rs.getInt("roomType")).thenReturn(3);
        when(rs.getDate("checkIn")).thenReturn(Date.valueOf("2026-02-10"));
        when(rs.getDate("checkOut")).thenReturn(Date.valueOf("2026-02-12"));
        when(rs.getString("status")).thenReturn("Completed");

        try (MockedStatic<DBConnection> db = Mockito.mockStatic(DBConnection.class)) {
            db.when(DBConnection::getConnection).thenReturn(con);

            ReservationDAO dao = new ReservationDAO();

            Reservation reservation = dao.getReservationById(5);

            assertNotNull(reservation);
            assertEquals(5, reservation.getId());
            assertEquals("Ava", reservation.getName());
            assertEquals("Completed", reservation.getStatus());
            verify(ps).setInt(1, 5);
        }
    }

    @Test
    void updateReservationStatusUsesProvidedValue() throws Exception {
        Connection con = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(con.prepareStatement(anyString())).thenReturn(ps);

        try (MockedStatic<DBConnection> db = Mockito.mockStatic(DBConnection.class)) {
            db.when(DBConnection::getConnection).thenReturn(con);

            ReservationDAO dao = new ReservationDAO();

            dao.updateReservationStatus(11, "Completed");

            verify(ps).setString(1, "Completed");
            verify(ps).setInt(2, 11);
            verify(ps).executeUpdate();
        }
    }

    @Test
    void getCompletedReservationsReturnsRows() throws Exception {
        Connection con = mock(Connection.class);
        Statement st = mock(Statement.class);
        ResultSet rs = mock(ResultSet.class);

        when(con.createStatement()).thenReturn(st);
        when(st.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("id")).thenReturn(1);
        when(rs.getString("name")).thenReturn("Noah");
        when(rs.getString("address")).thenReturn("Palm Street");
        when(rs.getString("telephone")).thenReturn("555-0001");
        when(rs.getString("nic_passport")).thenReturn("NIC001");
        when(rs.getInt("roomType")).thenReturn(1);
        when(rs.getDate("checkIn")).thenReturn(Date.valueOf("2026-02-01"));
        when(rs.getDate("checkOut")).thenReturn(Date.valueOf("2026-02-03"));
        when(rs.getString("status")).thenReturn("Completed");

        try (MockedStatic<DBConnection> db = Mockito.mockStatic(DBConnection.class)) {
            db.when(DBConnection::getConnection).thenReturn(con);

            ReservationDAO dao = new ReservationDAO();

            List<Reservation> reservations = dao.getCompletedReservations();

            assertEquals(1, reservations.size());
            assertEquals("Noah", reservations.get(0).getName());
        }
    }
}
