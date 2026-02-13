package com.mycompany.oceanviewresort.controller;

import com.mycompany.oceanviewresort.RoomsDAO;
import com.mycompany.oceanviewresort.model.Rooms;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AddRoomServletTest {
    @Test
    void doPostAddsRoomAndRedirects() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);

        when(req.getParameter("name")).thenReturn("New Room");
        when(req.getParameter("shortDescription")).thenReturn("Short desc");
        when(req.getParameter("capacity")).thenReturn("2");
        when(req.getParameter("typeofroom")).thenReturn("1");
        when(req.getParameter("pricePerNight")).thenReturn("110.0");
        when(req.getParameter("wifi")).thenReturn("on");
        when(req.getParameter("Aircon")).thenReturn("on");
        when(req.getParameter("Hotwater")).thenReturn(null);

        try (MockedConstruction<RoomsDAO> mocked = Mockito.mockConstruction(RoomsDAO.class)) {
            new addRoomServlet().doPost(req, res);

            RoomsDAO dao = mocked.constructed().get(0);
            ArgumentCaptor<Rooms> captor = ArgumentCaptor.forClass(Rooms.class);
            verify(dao).addRoom(captor.capture());

            Rooms room = captor.getValue();
            assertEquals("New Room", room.getName());
            assertEquals("Short desc", room.getShortDescription());
            assertEquals(2, room.getCapacity());
            assertEquals(1, room.getRoom_type());
            assertEquals(110.0, room.getPricePerNight());
            assertEquals(1, room.getWifi());
            assertEquals(1, room.getAircon());
            assertEquals(0, room.getHotwater());

            verify(res).sendRedirect("viewRoom");
        }
    }
}
