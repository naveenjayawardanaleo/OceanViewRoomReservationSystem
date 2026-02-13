package com.mycompany.oceanviewresort;

import com.mycompany.oceanviewresort.util.DBConnection;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoginServletTest {
    @Test
    void doPostRedirectsOnValidLogin() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);

        when(req.getParameter("username")).thenReturn("user");
        when(req.getParameter("password")).thenReturn("pass");

        Connection con = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);

        try (MockedStatic<DBConnection> db = Mockito.mockStatic(DBConnection.class)) {
            db.when(DBConnection::getConnection).thenReturn(con);

            new LoginServlet().doPost(req, res);

            verify(res).sendRedirect("dashboard.jsp");
        }
    }

    @Test
    void doPostWritesMessageOnInvalidLogin() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);

        when(req.getParameter("username")).thenReturn("user");
        when(req.getParameter("password")).thenReturn("wrong");

        Connection con = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        StringWriter buffer = new StringWriter();
        when(res.getWriter()).thenReturn(new PrintWriter(buffer));

        try (MockedStatic<DBConnection> db = Mockito.mockStatic(DBConnection.class)) {
            db.when(DBConnection::getConnection).thenReturn(con);

            new LoginServlet().doPost(req, res);

            verify(res, never()).sendRedirect(anyString());
            assertTrue(buffer.toString().contains("Invalid login"));
        }
    }
}
