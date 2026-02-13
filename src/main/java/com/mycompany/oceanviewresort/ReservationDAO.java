/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oceanviewresort;

import com.mycompany.oceanviewresort.model.Reservation;
import com.mycompany.oceanviewresort.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dell
 */


public class ReservationDAO {

    public boolean addReservation(Reservation r) {

        boolean status = false;

        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO reservation "
                    + "(name, address, telephone, nic_passport, roomType, checkIn, checkOut, status) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, r.getName());
            ps.setString(2, r.getAddress());
            ps.setString(3, r.getTelephone());
            ps.setString(4, r.getNicOrPassport());
            ps.setInt(5, r.getRoomType());
            ps.setDate(6, r.getCheckIn());
            ps.setDate(7, r.getCheckOut());
            ps.setString(8, r.getStatus() != null ? r.getStatus() : "Pending");

            status = ps.executeUpdate() > 0;

            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public List<Reservation> getAllReservation() throws Exception {

        List<Reservation> list = new ArrayList<>();
        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM reservation";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Reservation r = new Reservation();

            r.setId(rs.getInt("id"));
            r.setName(rs.getString("name"));
            r.setAddress(rs.getString("address"));
            r.setTelephone(rs.getString("telephone"));
            r.setNicOrPassport(rs.getString("nic_passport"));
            r.setRoomType(rs.getInt("roomType"));
            r.setCheckIn(rs.getDate("checkIn"));
            r.setCheckOut(rs.getDate("checkOut"));
            r.setStatus(rs.getString("status"));

            list.add(r);
        }

        rs.close();
        st.close();

        return list;
    }

    public void updateReservationStatus(int id, String status) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "UPDATE reservation SET status=? WHERE id=?");
        ps.setString(1, status);
        ps.setInt(2, id);
        ps.executeUpdate();
    }

    public List<Reservation> getCompletedReservations() throws Exception {
        List<Reservation> list = new ArrayList<>();
        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM reservation WHERE status='Completed'";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Reservation r = new Reservation();

            r.setId(rs.getInt("id"));
            r.setName(rs.getString("name"));
            r.setAddress(rs.getString("address"));
            r.setTelephone(rs.getString("telephone"));
            r.setNicOrPassport(rs.getString("nic_passport"));
            r.setRoomType(rs.getInt("roomType"));
            r.setCheckIn(rs.getDate("checkIn"));
            r.setCheckOut(rs.getDate("checkOut"));
            r.setStatus(rs.getString("status"));

            list.add(r);
        }

        rs.close();
        st.close();

        return list;
    }

    public List<Reservation> getPendingReservations() throws Exception {
        List<Reservation> list = new ArrayList<>();
        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM reservation WHERE status='Pending'";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Reservation r = new Reservation();

            r.setId(rs.getInt("id"));
            r.setName(rs.getString("name"));
            r.setAddress(rs.getString("address"));
            r.setTelephone(rs.getString("telephone"));
            r.setNicOrPassport(rs.getString("nic_passport"));
            r.setRoomType(rs.getInt("roomType"));
            r.setCheckIn(rs.getDate("checkIn"));
            r.setCheckOut(rs.getDate("checkOut"));
            r.setStatus(rs.getString("status"));

            list.add(r);
        }

        rs.close();
        st.close();

        return list;
    }

    public Reservation getReservationById(int id) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM reservation WHERE id=?");
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Reservation r = new Reservation();
            r.setId(rs.getInt("id"));
            r.setName(rs.getString("name"));
            r.setAddress(rs.getString("address"));
            r.setTelephone(rs.getString("telephone"));
            r.setNicOrPassport(rs.getString("nic_passport"));
            r.setRoomType(rs.getInt("roomType"));
            r.setCheckIn(rs.getDate("checkIn"));
            r.setCheckOut(rs.getDate("checkOut"));
            r.setStatus(rs.getString("status"));
            return r;
        }
        return null;
    }
}
