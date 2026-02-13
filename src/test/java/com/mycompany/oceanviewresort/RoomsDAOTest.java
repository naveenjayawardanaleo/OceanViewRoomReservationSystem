package com.mycompany.oceanviewresort;

import com.mycompany.oceanviewresort.model.Rooms;
import com.mycompany.oceanviewresort.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RoomsDAOTest {
    @Test
    void addRoomSetsGeneratedId() throws Exception {
        Rooms room = new Rooms();
        room.setName("Garden View");
        room.setShortDescription("Sea breeze");
        room.setCapacity(2);
        room.setRoom_type(1);
        room.setWifi(1);
        room.setAircon(0);
        room.setHotwater(1);
        room.setPricePerNight(120.0);

        Connection con = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet keys = mock(ResultSet.class);

        when(con.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(ps);
        when(ps.getGeneratedKeys()).thenReturn(keys);
        when(keys.next()).thenReturn(true);
        when(keys.getInt(1)).thenReturn(99);

        try (MockedStatic<DBConnection> db = Mockito.mockStatic(DBConnection.class)) {
            db.when(DBConnection::getConnection).thenReturn(con);

            RoomsDAO dao = new RoomsDAO();
            dao.addRoom(room);

            assertEquals(99, room.getId());
            verify(ps).setString(1, "Garden View");
            verify(ps).setString(2, "Sea breeze");
            verify(ps).setInt(3, 2);
            verify(ps).setInt(4, 1);
            verify(ps).setInt(5, 1);
            verify(ps).setInt(6, 0);
            verify(ps).setInt(7, 1);
            verify(ps).setDouble(8, 120.0);
            verify(ps).executeUpdate();
        }
    }

    @Test
    void getAllRoomsReturnsRows() throws Exception {
        Connection con = mock(Connection.class);
        Statement st = mock(Statement.class);
        ResultSet rs = mock(ResultSet.class);

        when(con.createStatement()).thenReturn(st);
        when(st.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("id")).thenReturn(1);
        when(rs.getString("name")).thenReturn("Ocean Suite");
        when(rs.getInt("capacity")).thenReturn(4);
        when(rs.getInt("room_type")).thenReturn(2);
        when(rs.getInt("wifi")).thenReturn(1);
        when(rs.getInt("aircon")).thenReturn(1);
        when(rs.getInt("hotwater")).thenReturn(1);
        when(rs.getString("shortDescription")).thenReturn("Ocean view");
        when(rs.getDouble("pricePerNight")).thenReturn(250.0);

        try (MockedStatic<DBConnection> db = Mockito.mockStatic(DBConnection.class)) {
            db.when(DBConnection::getConnection).thenReturn(con);

            RoomsDAO dao = new RoomsDAO();

            List<Rooms> rooms = dao.getAllRooms();

            assertEquals(1, rooms.size());
            assertEquals("Ocean Suite", rooms.get(0).getName());
        }
    }

    @Test
    void getRoomByIdReturnsRoom() throws Exception {
        Connection con = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("id")).thenReturn(3);
        when(rs.getString("name")).thenReturn("Family Room");
        when(rs.getInt("capacity")).thenReturn(5);
        when(rs.getInt("room_type")).thenReturn(3);
        when(rs.getInt("wifi")).thenReturn(1);
        when(rs.getInt("aircon")).thenReturn(0);
        when(rs.getInt("hotwater")).thenReturn(1);
        when(rs.getString("shortDescription")).thenReturn("Family stay");
        when(rs.getDouble("pricePerNight")).thenReturn(180.0);

        try (MockedStatic<DBConnection> db = Mockito.mockStatic(DBConnection.class)) {
            db.when(DBConnection::getConnection).thenReturn(con);

            RoomsDAO dao = new RoomsDAO();

            Rooms room = dao.getRoomById(3);

            assertNotNull(room);
            assertEquals("Family Room", room.getName());
            verify(ps).setInt(1, 3);
        }
    }

    @Test
    void updateRoomUsesFields() throws Exception {
        Rooms room = new Rooms();
        room.setId(10);
        room.setName("Renovated");
        room.setShortDescription("Updated");
        room.setCapacity(3);
        room.setRoom_type(2);
        room.setWifi(1);
        room.setAircon(1);
        room.setHotwater(1);
        room.setPricePerNight(200.0);

        Connection con = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(con.prepareStatement(anyString())).thenReturn(ps);

        try (MockedStatic<DBConnection> db = Mockito.mockStatic(DBConnection.class)) {
            db.when(DBConnection::getConnection).thenReturn(con);

            RoomsDAO dao = new RoomsDAO();

            dao.updateRoom(room);

            verify(ps).setString(1, "Renovated");
            verify(ps).setString(2, "Updated");
            verify(ps).setInt(3, 3);
            verify(ps).setInt(4, 2);
            verify(ps).setInt(5, 1);
            verify(ps).setInt(6, 1);
            verify(ps).setInt(7, 1);
            verify(ps).setDouble(8, 200.0);
            verify(ps).setInt(9, 10);
            verify(ps).executeUpdate();
        }
    }

    @Test
    void deleteRoomsExecutesUpdate() throws Exception {
        Connection con = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(con.prepareStatement(anyString())).thenReturn(ps);

        try (MockedStatic<DBConnection> db = Mockito.mockStatic(DBConnection.class)) {
            db.when(DBConnection::getConnection).thenReturn(con);

            RoomsDAO dao = new RoomsDAO();

            dao.deleteRooms(7);

            verify(ps).setInt(1, 7);
            verify(ps).executeUpdate();
        }
    }
}
