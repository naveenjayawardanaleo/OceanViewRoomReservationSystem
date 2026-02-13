package com.mycompany.oceanviewresort.controller;

import com.mycompany.oceanviewresort.RoomsDAO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeleteRoomServletTest {
    @Test
    void doGetDeletesRoomAndRedirects() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);

        when(req.getParameter("id")).thenReturn("12");

        try (MockedConstruction<RoomsDAO> mocked = Mockito.mockConstruction(RoomsDAO.class)) {
            new deleteRoomServlet().doGet(req, res);

            RoomsDAO dao = mocked.constructed().get(0);
            verify(dao).deleteRooms(12);
            verify(res).sendRedirect("viewRoom");
        }
    }
}
