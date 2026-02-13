package com.mycompany.oceanviewresort;

import com.mycompany.oceanviewresort.model.Rooms;
import com.mycompany.oceanviewresort.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Dell
 */
public class RoomsDAO {
    
    public void addRoom(Rooms c) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO rooms (name, shortDescription, capacity, room_type, wifi, aircon, hotwater, pricePerNight) VALUES (?,?,?,?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS
        );

        ps.setString(1, c.getName());
        ps.setString(2, c.getShortDescription());
        ps.setInt(3, c.getCapacity());
        ps.setInt(4, c.getRoom_type());
        ps.setInt(5, c.getWifi());
        ps.setInt(6, c.getAircon());
        ps.setInt(7, c.getHotwater());
        ps.setDouble(8, c.getPricePerNight());

        ps.executeUpdate();

        // Get auto-generated ID
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            c.setId(rs.getInt(1));
        }
    }

    public List<Rooms> getAllRooms() throws Exception {
        List<Rooms> list = new ArrayList<>();

        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM rooms");

        while (rs.next()) {
            Rooms c = new Rooms();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            c.setCapacity(rs.getInt("capacity"));
            c.setRoom_type(rs.getInt("room_type"));
            c.setWifi(rs.getInt("wifi"));
            c.setAircon(rs.getInt("aircon"));
            c.setHotwater(rs.getInt("hotwater"));
            c.setShortDescription(rs.getString("shortDescription"));
            c.setPricePerNight(rs.getDouble("pricePerNight"));
            list.add(c);
        }
        return list;
    }

    public Rooms getRoomById(int id) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM rooms WHERE id=?");
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Rooms c = new Rooms();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            c.setCapacity(rs.getInt("capacity"));
            c.setRoom_type(rs.getInt("room_type"));
            c.setWifi(rs.getInt("wifi"));
            c.setAircon(rs.getInt("aircon"));
            c.setHotwater(rs.getInt("hotwater"));
            c.setShortDescription(rs.getString("shortDescription"));
            c.setPricePerNight(rs.getDouble("pricePerNight"));
            return c;
        }
        return null;
    }

    public void updateRoom(Rooms c) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "UPDATE rooms SET name=?, shortDescription=?, capacity=?, room_type=?, wifi=?, aircon=?, hotwater=?,  pricePerNight=? WHERE id=?");

        ps.setString(1, c.getName());
        ps.setString(2, c.getShortDescription());
        ps.setInt(3, c.getCapacity());
        ps.setInt(4, c.getRoom_type());
        ps.setInt(5, c.getWifi());
        ps.setInt(6, c.getAircon());
        ps.setInt(7, c.getHotwater());
        ps.setDouble(8, c.getPricePerNight());

        ps.setInt(9, c.getId());

        ps.executeUpdate();
    }

    public void deleteRooms(int id) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "DELETE FROM rooms WHERE id=?");
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public Rooms getRoomByType(int type) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM rooms WHERE room_type=? LIMIT 1");
        ps.setInt(1, type);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Rooms c = new Rooms();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            c.setCapacity(rs.getInt("capacity"));
            c.setRoom_type(rs.getInt("room_type"));
            c.setWifi(rs.getInt("wifi"));
            c.setAircon(rs.getInt("aircon"));
            c.setHotwater(rs.getInt("hotwater"));
            c.setShortDescription(rs.getString("shortDescription"));
            c.setPricePerNight(rs.getDouble("pricePerNight"));
            return c;
        }
        return null;
    }
}
