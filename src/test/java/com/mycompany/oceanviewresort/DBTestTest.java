package com.mycompany.oceanviewresort;

import com.mycompany.oceanviewresort.util.DBConnection;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class DBTestTest {
    @Test
    void mainPrintsSuccessMessage() {
        Connection con = mock(Connection.class);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try (MockedStatic<DBConnection> db = Mockito.mockStatic(DBConnection.class)) {
            db.when(DBConnection::getConnection).thenReturn(con);

            DBTest.main(new String[0]);

            assertTrue(output.toString().contains("Database connected successfully"));
        } finally {
            System.setOut(originalOut);
        }
    }
}
