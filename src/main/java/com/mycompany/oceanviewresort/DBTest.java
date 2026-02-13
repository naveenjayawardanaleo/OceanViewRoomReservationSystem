package com.mycompany.oceanviewresort;

import com.mycompany.oceanviewresort.util.DBConnection;
import java.sql.Connection;

public class DBTest {
    public static void main(String[] args) {
        try {
            Connection con = DBConnection.getConnection();
            if (con != null) {
                System.out.println("✅ Database connected successfully");
            } else {
                System.out.println("❌ Database connection failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
